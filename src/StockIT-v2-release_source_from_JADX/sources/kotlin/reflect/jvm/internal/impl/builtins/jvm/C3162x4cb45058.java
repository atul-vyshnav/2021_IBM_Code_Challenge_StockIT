package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PackageFragmentDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;

/* renamed from: kotlin.reflect.jvm.internal.impl.builtins.jvm.JvmBuiltInsCustomizer$createMockJavaIoSerializableType$mockJavaIoPackageFragment$1 */
/* compiled from: JvmBuiltInsCustomizer.kt */
public final class C3162x4cb45058 extends PackageFragmentDescriptorImpl {
    C3162x4cb45058(ModuleDescriptor moduleDescriptor, FqName fqName) {
        super(moduleDescriptor, fqName);
    }

    public MemberScope.Empty getMemberScope() {
        return MemberScope.Empty.INSTANCE;
    }
}
