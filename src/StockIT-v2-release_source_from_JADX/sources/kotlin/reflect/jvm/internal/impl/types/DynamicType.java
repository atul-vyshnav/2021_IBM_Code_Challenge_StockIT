package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* compiled from: dynamicTypes.kt */
public final class DynamicType extends FlexibleType implements DynamicTypeMarker {
    private final Annotations annotations;

    public boolean isMarkedNullable() {
        return false;
    }

    public DynamicType makeNullableAsSpecified(boolean z) {
        return this;
    }

    public DynamicType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    public String render(DescriptorRenderer descriptorRenderer, DescriptorRendererOptions descriptorRendererOptions) {
        Intrinsics.checkNotNullParameter(descriptorRenderer, "renderer");
        Intrinsics.checkNotNullParameter(descriptorRendererOptions, "options");
        return "dynamic";
    }

    public Annotations getAnnotations() {
        return this.annotations;
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public DynamicType(kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns r3, kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r4) {
        /*
            r2 = this;
            java.lang.String r0 = "builtIns"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            java.lang.String r0 = "annotations"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            kotlin.reflect.jvm.internal.impl.types.SimpleType r0 = r3.getNothingType()
            java.lang.String r1 = "builtIns.nothingType"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r0, r1)
            kotlin.reflect.jvm.internal.impl.types.SimpleType r3 = r3.getNullableAnyType()
            java.lang.String r1 = "builtIns.nullableAnyType"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r1)
            r2.<init>(r0, r3)
            r2.annotations = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.types.DynamicType.<init>(kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns, kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations):void");
    }

    public SimpleType getDelegate() {
        return getUpperBound();
    }

    public DynamicType replaceAnnotations(Annotations annotations2) {
        Intrinsics.checkNotNullParameter(annotations2, "newAnnotations");
        return new DynamicType(TypeUtilsKt.getBuiltIns(getDelegate()), annotations2);
    }
}
