package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;

/* renamed from: kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver$mapJavaConstantToQualifierApplicabilityTypes$1 */
/* compiled from: AnnotationTypeQualifierResolver.kt */
final class C3200xefd2e52b extends Lambda implements Function2<EnumValue, AnnotationQualifierApplicabilityType, Boolean> {
    public static final C3200xefd2e52b INSTANCE = new C3200xefd2e52b();

    C3200xefd2e52b() {
        super(2);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return Boolean.valueOf(invoke((EnumValue) obj, (AnnotationQualifierApplicabilityType) obj2));
    }

    public final boolean invoke(EnumValue enumValue, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType) {
        Intrinsics.checkNotNullParameter(enumValue, "<this>");
        Intrinsics.checkNotNullParameter(annotationQualifierApplicabilityType, "it");
        return Intrinsics.areEqual((Object) enumValue.getEnumEntryName().getIdentifier(), (Object) annotationQualifierApplicabilityType.getJavaTarget());
    }
}
