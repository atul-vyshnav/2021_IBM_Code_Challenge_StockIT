package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;

/* renamed from: kotlin.reflect.jvm.internal.impl.load.java.ClassicBuiltinSpecialProperties$getBuiltinSpecialPropertyGetterName$descriptor$1 */
/* compiled from: ClassicBuiltinSpecialProperties.kt */
final class C3205xccd5eab2 extends Lambda implements Function1<CallableMemberDescriptor, Boolean> {
    final /* synthetic */ ClassicBuiltinSpecialProperties this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3205xccd5eab2(ClassicBuiltinSpecialProperties classicBuiltinSpecialProperties) {
        super(1);
        this.this$0 = classicBuiltinSpecialProperties;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return Boolean.valueOf(invoke((CallableMemberDescriptor) obj));
    }

    public final boolean invoke(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "it");
        return this.this$0.hasBuiltinSpecialPropertyFqName(callableMemberDescriptor);
    }
}
