package kotlin.reflect.jvm.internal;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.KProperty;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"}, mo40253d2 = {"<no name provided>", "Ljava/lang/reflect/Type;", "invoke", "kotlin/reflect/jvm/internal/KTypeImpl$arguments$2$1$type$1"}, mo40254k = 3, mo40255mv = {1, 4, 1})
/* compiled from: KTypeImpl.kt */
final class KTypeImpl$arguments$2$$special$$inlined$mapIndexed$lambda$1 extends Lambda implements Function0<Type> {

    /* renamed from: $i */
    final /* synthetic */ int f1268$i;
    final /* synthetic */ Lazy $parameterizedTypeArguments$inlined;
    final /* synthetic */ KProperty $parameterizedTypeArguments$metadata$inlined;
    final /* synthetic */ KTypeImpl$arguments$2 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KTypeImpl$arguments$2$$special$$inlined$mapIndexed$lambda$1(int i, KTypeImpl$arguments$2 kTypeImpl$arguments$2, Lazy lazy, KProperty kProperty) {
        super(0);
        this.f1268$i = i;
        this.this$0 = kTypeImpl$arguments$2;
        this.$parameterizedTypeArguments$inlined = lazy;
        this.$parameterizedTypeArguments$metadata$inlined = kProperty;
    }

    public final Type invoke() {
        Type javaType = this.this$0.this$0.getJavaType();
        if (javaType instanceof Class) {
            Class cls = (Class) javaType;
            Class componentType = cls.isArray() ? cls.getComponentType() : Object.class;
            Intrinsics.checkNotNullExpressionValue(componentType, "if (javaType.isArray) ja…Type else Any::class.java");
            return componentType;
        } else if (javaType instanceof GenericArrayType) {
            if (this.f1268$i == 0) {
                Type genericComponentType = ((GenericArrayType) javaType).getGenericComponentType();
                Intrinsics.checkNotNullExpressionValue(genericComponentType, "javaType.genericComponentType");
                return genericComponentType;
            }
            throw new KotlinReflectionInternalError("Array type has been queried for a non-0th argument: " + this.this$0.this$0);
        } else if (javaType instanceof ParameterizedType) {
            Type type = (Type) ((List) this.$parameterizedTypeArguments$inlined.getValue()).get(this.f1268$i);
            if (type instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) type;
                Type[] lowerBounds = wildcardType.getLowerBounds();
                Intrinsics.checkNotNullExpressionValue(lowerBounds, "argument.lowerBounds");
                Type type2 = (Type) ArraysKt.firstOrNull((T[]) lowerBounds);
                if (type2 != null) {
                    type = type2;
                } else {
                    Type[] upperBounds = wildcardType.getUpperBounds();
                    Intrinsics.checkNotNullExpressionValue(upperBounds, "argument.upperBounds");
                    type = (Type) ArraysKt.first((T[]) upperBounds);
                }
            }
            Intrinsics.checkNotNullExpressionValue(type, "if (argument !is Wildcar…ument.upperBounds.first()");
            return type;
        } else {
            throw new KotlinReflectionInternalError("Non-generic type has been queried for arguments: " + this.this$0.this$0);
        }
    }
}
