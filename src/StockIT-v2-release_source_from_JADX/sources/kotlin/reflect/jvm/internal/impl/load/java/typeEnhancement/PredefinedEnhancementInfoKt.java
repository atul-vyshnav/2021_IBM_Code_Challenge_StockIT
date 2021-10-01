package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.Map;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancementBuilder;
import kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents;

/* compiled from: predefinedEnhancementInfo.kt */
public final class PredefinedEnhancementInfoKt {
    /* access modifiers changed from: private */
    public static final JavaTypeQualifiers NOT_NULLABLE = new JavaTypeQualifiers(NullabilityQualifier.NOT_NULL, (MutabilityQualifier) null, true, false, 8, (DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final JavaTypeQualifiers NOT_PLATFORM = new JavaTypeQualifiers(NullabilityQualifier.NOT_NULL, (MutabilityQualifier) null, false, false, 8, (DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public static final JavaTypeQualifiers NULLABLE = new JavaTypeQualifiers(NullabilityQualifier.NULLABLE, (MutabilityQualifier) null, false, false, 8, (DefaultConstructorMarker) null);
    private static final Map<String, PredefinedFunctionEnhancementInfo> PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE;

    static {
        SignatureBuildingComponents signatureBuildingComponents = SignatureBuildingComponents.INSTANCE;
        String javaLang = signatureBuildingComponents.javaLang("Object");
        String javaFunction = signatureBuildingComponents.javaFunction("Predicate");
        String javaFunction2 = signatureBuildingComponents.javaFunction("Function");
        String javaFunction3 = signatureBuildingComponents.javaFunction("Consumer");
        String javaFunction4 = signatureBuildingComponents.javaFunction("BiFunction");
        String javaFunction5 = signatureBuildingComponents.javaFunction("BiConsumer");
        String javaFunction6 = signatureBuildingComponents.javaFunction("UnaryOperator");
        String javaUtil = signatureBuildingComponents.javaUtil("stream/Stream");
        String javaUtil2 = signatureBuildingComponents.javaUtil("Optional");
        SignatureEnhancementBuilder signatureEnhancementBuilder = new SignatureEnhancementBuilder();
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil("Iterator")).function("forEachRemaining", new C3218xb250426d(javaFunction3));
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaLang("Iterable")).function("spliterator", new C3224xb250462e(signatureBuildingComponents));
        SignatureEnhancementBuilder.ClassEnhancementBuilder classEnhancementBuilder = new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil("Collection"));
        classEnhancementBuilder.function("removeIf", new C3225xb25049ef(javaFunction));
        classEnhancementBuilder.function("stream", new C3226xb25049f0(javaUtil));
        classEnhancementBuilder.function("parallelStream", new C3227xb25049f1(javaUtil));
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil("List")).function("replaceAll", new C3228xb2504db0(javaFunction6));
        SignatureEnhancementBuilder.ClassEnhancementBuilder classEnhancementBuilder2 = new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaUtil("Map"));
        classEnhancementBuilder2.function("forEach", new C3229xb2505171(javaFunction5));
        classEnhancementBuilder2.function("putIfAbsent", new C3230xb2505172(javaLang));
        classEnhancementBuilder2.function("replace", new C3231xb2505173(javaLang));
        classEnhancementBuilder2.function("replace", new C3232xb2505174(javaLang));
        classEnhancementBuilder2.function("replaceAll", new C3233xb2505175(javaFunction4));
        classEnhancementBuilder2.function("compute", new C3234xb2505176(javaLang, javaFunction4));
        classEnhancementBuilder2.function("computeIfAbsent", new C3235xb2505177(javaLang, javaFunction2));
        classEnhancementBuilder2.function("computeIfPresent", new C3236xb2505178(javaLang, javaFunction4));
        classEnhancementBuilder2.function("merge", new C3237xb2505179(javaLang, javaFunction4));
        SignatureEnhancementBuilder.ClassEnhancementBuilder classEnhancementBuilder3 = new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, javaUtil2);
        classEnhancementBuilder3.function("empty", new C3238xb2505532(javaUtil2));
        classEnhancementBuilder3.function("of", new C3239xb2505533(javaLang, javaUtil2));
        classEnhancementBuilder3.function("ofNullable", new C3240xb2505534(javaLang, javaUtil2));
        classEnhancementBuilder3.function("get", new C3241xb2505535(javaLang));
        classEnhancementBuilder3.function("ifPresent", new C3242xb2505536(javaFunction3));
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaLang("ref/Reference")).function("get", new C3243xb25058f3(javaLang));
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, javaFunction).function("test", new C3244xb2505cb4(javaLang));
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaFunction("BiPredicate")).function("test", new C3245xb2506075(javaLang));
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, javaFunction3).function("accept", new C3219x97b836dd(javaLang));
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, javaFunction5).function("accept", new C3220x97b83a9e(javaLang));
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, javaFunction2).function("apply", new C3221x97b83e5f(javaLang));
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, javaFunction4).function("apply", new C3222x97b84220(javaLang));
        new SignatureEnhancementBuilder.ClassEnhancementBuilder(signatureEnhancementBuilder, signatureBuildingComponents.javaFunction("Supplier")).function("get", new C3223x97b845e1(javaLang));
        PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE = signatureEnhancementBuilder.build();
    }

    public static final Map<String, PredefinedFunctionEnhancementInfo> getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE() {
        return PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE;
    }
}
