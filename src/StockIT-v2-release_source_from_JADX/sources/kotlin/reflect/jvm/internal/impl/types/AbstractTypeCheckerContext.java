package kotlin.reflect.jvm.internal.impl.types;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Set;
import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContext;
import kotlin.reflect.jvm.internal.impl.utils.SmartSet;

/* compiled from: AbstractTypeChecker.kt */
public abstract class AbstractTypeCheckerContext implements TypeSystemContext {
    /* access modifiers changed from: private */
    public int argumentsDepth;
    private ArrayDeque<SimpleTypeMarker> supertypesDeque;
    private boolean supertypesLocked;
    private Set<SimpleTypeMarker> supertypesSet;

    /* compiled from: AbstractTypeChecker.kt */
    public enum LowerCapturedTypePolicy {
        CHECK_ONLY_LOWER,
        CHECK_SUBTYPE_AND_LOWER,
        SKIP_LOWER
    }

    public Boolean addSubtypeConstraint(KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "subType");
        Intrinsics.checkNotNullParameter(kotlinTypeMarker2, "superType");
        return null;
    }

    public boolean customIsSubtypeOf(KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "subType");
        Intrinsics.checkNotNullParameter(kotlinTypeMarker2, "superType");
        return true;
    }

    public abstract boolean isAllowedTypeVariable(KotlinTypeMarker kotlinTypeMarker);

    public abstract boolean isErrorTypeEqualsToAnything();

    public abstract boolean isStubTypeEqualsToAnything();

    public KotlinTypeMarker prepareType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "type");
        return kotlinTypeMarker;
    }

    public KotlinTypeMarker refineType(KotlinTypeMarker kotlinTypeMarker) {
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "type");
        return kotlinTypeMarker;
    }

    public abstract SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker simpleTypeMarker);

    public List<SimpleTypeMarker> fastCorrespondingSupertypes(SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker typeConstructorMarker) {
        return TypeSystemContext.DefaultImpls.fastCorrespondingSupertypes(this, simpleTypeMarker, typeConstructorMarker);
    }

    public TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i) {
        return TypeSystemContext.DefaultImpls.get(this, typeArgumentListMarker, i);
    }

    public TypeArgumentMarker getArgumentOrNull(SimpleTypeMarker simpleTypeMarker, int i) {
        return TypeSystemContext.DefaultImpls.getArgumentOrNull(this, simpleTypeMarker, i);
    }

    public boolean hasFlexibleNullability(KotlinTypeMarker kotlinTypeMarker) {
        return TypeSystemContext.DefaultImpls.hasFlexibleNullability(this, kotlinTypeMarker);
    }

    public boolean identicalArguments(SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
        return TypeSystemContext.DefaultImpls.identicalArguments(this, simpleTypeMarker, simpleTypeMarker2);
    }

    public boolean isClassType(SimpleTypeMarker simpleTypeMarker) {
        return TypeSystemContext.DefaultImpls.isClassType(this, simpleTypeMarker);
    }

    public boolean isDefinitelyNotNullType(KotlinTypeMarker kotlinTypeMarker) {
        return TypeSystemContext.DefaultImpls.isDefinitelyNotNullType(this, kotlinTypeMarker);
    }

    public boolean isDynamic(KotlinTypeMarker kotlinTypeMarker) {
        return TypeSystemContext.DefaultImpls.isDynamic(this, kotlinTypeMarker);
    }

    public boolean isIntegerLiteralType(SimpleTypeMarker simpleTypeMarker) {
        return TypeSystemContext.DefaultImpls.isIntegerLiteralType(this, simpleTypeMarker);
    }

    public boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker) {
        return TypeSystemContext.DefaultImpls.isMarkedNullable(this, kotlinTypeMarker);
    }

    public boolean isNothing(KotlinTypeMarker kotlinTypeMarker) {
        return TypeSystemContext.DefaultImpls.isNothing(this, kotlinTypeMarker);
    }

    public SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        return TypeSystemContext.DefaultImpls.lowerBoundIfFlexible(this, kotlinTypeMarker);
    }

    public int size(TypeArgumentListMarker typeArgumentListMarker) {
        return TypeSystemContext.DefaultImpls.size(this, typeArgumentListMarker);
    }

    public TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker) {
        return TypeSystemContext.DefaultImpls.typeConstructor(this, kotlinTypeMarker);
    }

    public SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker) {
        return TypeSystemContext.DefaultImpls.upperBoundIfFlexible(this, kotlinTypeMarker);
    }

    public LowerCapturedTypePolicy getLowerCapturedTypePolicy(SimpleTypeMarker simpleTypeMarker, CapturedTypeMarker capturedTypeMarker) {
        Intrinsics.checkNotNullParameter(simpleTypeMarker, "subType");
        Intrinsics.checkNotNullParameter(capturedTypeMarker, "superType");
        return LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER;
    }

    public static /* synthetic */ Boolean addSubtypeConstraint$default(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z, int i, Object obj) {
        if (obj == null) {
            if ((i & 4) != 0) {
                z = false;
            }
            return abstractTypeCheckerContext.addSubtypeConstraint(kotlinTypeMarker, kotlinTypeMarker2, z);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addSubtypeConstraint");
    }

    public final ArrayDeque<SimpleTypeMarker> getSupertypesDeque() {
        return this.supertypesDeque;
    }

    public final Set<SimpleTypeMarker> getSupertypesSet() {
        return this.supertypesSet;
    }

    public final void initialize() {
        boolean z = !this.supertypesLocked;
        if (!_Assertions.ENABLED || z) {
            this.supertypesLocked = true;
            if (this.supertypesDeque == null) {
                this.supertypesDeque = new ArrayDeque<>(4);
            }
            if (this.supertypesSet == null) {
                this.supertypesSet = SmartSet.Companion.create();
                return;
            }
            return;
        }
        throw new AssertionError("Assertion failed");
    }

    public final void clear() {
        ArrayDeque<SimpleTypeMarker> arrayDeque = this.supertypesDeque;
        Intrinsics.checkNotNull(arrayDeque);
        arrayDeque.clear();
        Set<SimpleTypeMarker> set = this.supertypesSet;
        Intrinsics.checkNotNull(set);
        set.clear();
        this.supertypesLocked = false;
    }

    /* compiled from: AbstractTypeChecker.kt */
    public static abstract class SupertypesPolicy {
        public /* synthetic */ SupertypesPolicy(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract SimpleTypeMarker transformType(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker);

        private SupertypesPolicy() {
        }

        /* compiled from: AbstractTypeChecker.kt */
        public static final class None extends SupertypesPolicy {
            public static final None INSTANCE = new None();

            private None() {
                super((DefaultConstructorMarker) null);
            }

            public Void transformType(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker) {
                Intrinsics.checkNotNullParameter(abstractTypeCheckerContext, "context");
                Intrinsics.checkNotNullParameter(kotlinTypeMarker, "type");
                throw new UnsupportedOperationException("Should not be called");
            }
        }

        /* compiled from: AbstractTypeChecker.kt */
        public static final class UpperIfFlexible extends SupertypesPolicy {
            public static final UpperIfFlexible INSTANCE = new UpperIfFlexible();

            private UpperIfFlexible() {
                super((DefaultConstructorMarker) null);
            }

            public SimpleTypeMarker transformType(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker) {
                Intrinsics.checkNotNullParameter(abstractTypeCheckerContext, "context");
                Intrinsics.checkNotNullParameter(kotlinTypeMarker, "type");
                return abstractTypeCheckerContext.upperBoundIfFlexible(kotlinTypeMarker);
            }
        }

        /* compiled from: AbstractTypeChecker.kt */
        public static final class LowerIfFlexible extends SupertypesPolicy {
            public static final LowerIfFlexible INSTANCE = new LowerIfFlexible();

            private LowerIfFlexible() {
                super((DefaultConstructorMarker) null);
            }

            public SimpleTypeMarker transformType(AbstractTypeCheckerContext abstractTypeCheckerContext, KotlinTypeMarker kotlinTypeMarker) {
                Intrinsics.checkNotNullParameter(abstractTypeCheckerContext, "context");
                Intrinsics.checkNotNullParameter(kotlinTypeMarker, "type");
                return abstractTypeCheckerContext.lowerBoundIfFlexible(kotlinTypeMarker);
            }
        }

        /* compiled from: AbstractTypeChecker.kt */
        public static abstract class DoCustomTransform extends SupertypesPolicy {
            public DoCustomTransform() {
                super((DefaultConstructorMarker) null);
            }
        }
    }
}
