package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

/* compiled from: MemberDeserializer.kt */
/* synthetic */ class MemberDeserializer$containsSuspendFunctionType$1 extends PropertyReference1 {
    public static final MemberDeserializer$containsSuspendFunctionType$1 INSTANCE = new MemberDeserializer$containsSuspendFunctionType$1();

    public String getName() {
        return "isSuspendFunctionType";
    }

    public String getSignature() {
        return "isSuspendFunctionType(Lorg/jetbrains/kotlin/types/KotlinType;)Z";
    }

    /* synthetic */ MemberDeserializer$containsSuspendFunctionType$1() {
    }

    public Object get(Object obj) {
        return Boolean.valueOf(FunctionTypesKt.isSuspendFunctionType((KotlinType) obj));
    }

    public KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(FunctionTypesKt.class, "deserialization");
    }
}
