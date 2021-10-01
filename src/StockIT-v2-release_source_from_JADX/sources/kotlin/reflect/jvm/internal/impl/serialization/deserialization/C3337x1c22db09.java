package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.name.ClassId;

/* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeConstructor$notFoundClass$classNestingLevel$1 */
/* compiled from: TypeDeserializer.kt */
/* synthetic */ class C3337x1c22db09 extends FunctionReference implements Function1<ClassId, ClassId> {
    public static final C3337x1c22db09 INSTANCE = new C3337x1c22db09();

    C3337x1c22db09() {
        super(1);
    }

    public final String getName() {
        return "getOuterClassId";
    }

    public final String getSignature() {
        return "getOuterClassId()Lorg/jetbrains/kotlin/name/ClassId;";
    }

    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ClassId.class);
    }

    public final ClassId invoke(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "p0");
        return classId.getOuterClassId();
    }
}
