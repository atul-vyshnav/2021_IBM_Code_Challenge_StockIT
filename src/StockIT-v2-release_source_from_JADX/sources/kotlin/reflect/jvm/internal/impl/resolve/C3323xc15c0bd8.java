package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;

/* renamed from: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides$areCallableDescriptorsEquivalent$1 */
/* compiled from: DescriptorEquivalenceForOverrides.kt */
final class C3323xc15c0bd8 extends Lambda implements Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean> {
    public static final C3323xc15c0bd8 INSTANCE = new C3323xc15c0bd8();

    C3323xc15c0bd8() {
        super(2);
    }

    public final boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
        return false;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return Boolean.valueOf(invoke((DeclarationDescriptor) obj, (DeclarationDescriptor) obj2));
    }
}
