package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* compiled from: NewCapturedType.kt */
public final class NewCapturedTypeConstructor implements CapturedTypeConstructor {
    private final Lazy _supertypes$delegate;
    private final NewCapturedTypeConstructor original;
    private final TypeProjection projection;
    /* access modifiers changed from: private */
    public Function0<? extends List<? extends UnwrappedType>> supertypesComputation;
    private final TypeParameterDescriptor typeParameter;

    public ClassifierDescriptor getDeclarationDescriptor() {
        return null;
    }

    public boolean isDenotable() {
        return false;
    }

    public NewCapturedTypeConstructor(TypeProjection typeProjection, Function0<? extends List<? extends UnwrappedType>> function0, NewCapturedTypeConstructor newCapturedTypeConstructor, TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(typeProjection, "projection");
        this.projection = typeProjection;
        this.supertypesComputation = function0;
        this.original = newCapturedTypeConstructor;
        this.typeParameter = typeParameterDescriptor;
        this._supertypes$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, new NewCapturedTypeConstructor$_supertypes$2(this));
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ NewCapturedTypeConstructor(TypeProjection typeProjection, Function0 function0, NewCapturedTypeConstructor newCapturedTypeConstructor, TypeParameterDescriptor typeParameterDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeProjection, (i & 2) != 0 ? null : function0, (i & 4) != 0 ? null : newCapturedTypeConstructor, (i & 8) != 0 ? null : typeParameterDescriptor);
    }

    public TypeProjection getProjection() {
        return this.projection;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ NewCapturedTypeConstructor(TypeProjection typeProjection, List list, NewCapturedTypeConstructor newCapturedTypeConstructor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeProjection, list, (i & 4) != 0 ? null : newCapturedTypeConstructor);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public NewCapturedTypeConstructor(TypeProjection typeProjection, final List<? extends UnwrappedType> list, NewCapturedTypeConstructor newCapturedTypeConstructor) {
        this(typeProjection, new Function0<List<? extends UnwrappedType>>() {
            public final List<UnwrappedType> invoke() {
                return list;
            }
        }, newCapturedTypeConstructor, (TypeParameterDescriptor) null, 8, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(typeProjection, "projection");
        Intrinsics.checkNotNullParameter(list, "supertypes");
    }

    private final List<UnwrappedType> get_supertypes() {
        return (List) this._supertypes$delegate.getValue();
    }

    public final void initializeSupertypes(List<? extends UnwrappedType> list) {
        Intrinsics.checkNotNullParameter(list, "supertypes");
        boolean z = this.supertypesComputation == null;
        if (!_Assertions.ENABLED || z) {
            this.supertypesComputation = new NewCapturedTypeConstructor$initializeSupertypes$2(list);
            return;
        }
        throw new AssertionError("Already initialized! oldValue = " + this.supertypesComputation + ", newValue = " + list);
    }

    public List<UnwrappedType> getSupertypes() {
        List<UnwrappedType> list = get_supertypes();
        return list == null ? CollectionsKt.emptyList() : list;
    }

    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    public KotlinBuiltIns getBuiltIns() {
        KotlinType type = getProjection().getType();
        Intrinsics.checkNotNullExpressionValue(type, "projection.type");
        return TypeUtilsKt.getBuiltIns(type);
    }

    public NewCapturedTypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Function0 function0;
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        TypeProjection refine = getProjection().refine(kotlinTypeRefiner);
        Intrinsics.checkNotNullExpressionValue(refine, "projection.refine(kotlinTypeRefiner)");
        if (this.supertypesComputation == null) {
            function0 = null;
        } else {
            function0 = new NewCapturedTypeConstructor$refine$1$1(this, kotlinTypeRefiner);
        }
        NewCapturedTypeConstructor newCapturedTypeConstructor = this.original;
        if (newCapturedTypeConstructor == null) {
            newCapturedTypeConstructor = this;
        }
        return new NewCapturedTypeConstructor(refine, function0, newCapturedTypeConstructor, this.typeParameter);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual((Object) getClass(), (Object) obj == null ? null : obj.getClass())) {
            return false;
        }
        if (obj != null) {
            NewCapturedTypeConstructor newCapturedTypeConstructor = (NewCapturedTypeConstructor) obj;
            NewCapturedTypeConstructor newCapturedTypeConstructor2 = this.original;
            if (newCapturedTypeConstructor2 == null) {
                newCapturedTypeConstructor2 = this;
            }
            NewCapturedTypeConstructor newCapturedTypeConstructor3 = newCapturedTypeConstructor.original;
            if (newCapturedTypeConstructor3 != null) {
                newCapturedTypeConstructor = newCapturedTypeConstructor3;
            }
            if (newCapturedTypeConstructor2 == newCapturedTypeConstructor) {
                return true;
            }
            return false;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor");
    }

    public int hashCode() {
        NewCapturedTypeConstructor newCapturedTypeConstructor = this.original;
        return newCapturedTypeConstructor == null ? super.hashCode() : newCapturedTypeConstructor.hashCode();
    }

    public String toString() {
        return "CapturedType(" + getProjection() + ')';
    }
}
