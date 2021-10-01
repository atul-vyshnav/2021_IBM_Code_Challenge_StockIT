package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

/* renamed from: kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector$intersectTypesWithoutIntersectionType$filteredEqualTypes$1 */
/* compiled from: IntersectionType.kt */
/* synthetic */ class C3370x702eebb8 extends FunctionReference implements Function2<KotlinType, KotlinType, Boolean> {
    C3370x702eebb8(TypeIntersector typeIntersector) {
        super(2, typeIntersector);
    }

    public final String getName() {
        return "isStrictSupertype";
    }

    public final String getSignature() {
        return "isStrictSupertype(Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)Z";
    }

    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(TypeIntersector.class);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return Boolean.valueOf(invoke((KotlinType) obj, (KotlinType) obj2));
    }

    public final boolean invoke(KotlinType kotlinType, KotlinType kotlinType2) {
        Intrinsics.checkNotNullParameter(kotlinType, "p0");
        Intrinsics.checkNotNullParameter(kotlinType2, "p1");
        return ((TypeIntersector) this.receiver).isStrictSupertype(kotlinType, kotlinType2);
    }
}
