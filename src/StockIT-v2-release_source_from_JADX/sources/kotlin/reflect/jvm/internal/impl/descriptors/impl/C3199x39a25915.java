package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;

/* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.impl.ModuleDescriptorImpl$packageFragmentProviderForWholeModuleWithDependencies$2 */
/* compiled from: ModuleDescriptorImpl.kt */
final class C3199x39a25915 extends Lambda implements Function0<CompositePackageFragmentProvider> {
    final /* synthetic */ ModuleDescriptorImpl this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3199x39a25915(ModuleDescriptorImpl moduleDescriptorImpl) {
        super(0);
        this.this$0 = moduleDescriptorImpl;
    }

    public final CompositePackageFragmentProvider invoke() {
        ModuleDependencies access$getDependencies$p = this.this$0.dependencies;
        ModuleDescriptorImpl moduleDescriptorImpl = this.this$0;
        if (access$getDependencies$p != null) {
            List<ModuleDescriptorImpl> allDependencies = access$getDependencies$p.getAllDependencies();
            boolean contains = allDependencies.contains(this.this$0);
            ModuleDescriptorImpl moduleDescriptorImpl2 = this.this$0;
            if (!_Assertions.ENABLED || contains) {
                Iterable<ModuleDescriptorImpl> iterable = allDependencies;
                ModuleDescriptorImpl moduleDescriptorImpl3 = this.this$0;
                for (ModuleDescriptorImpl moduleDescriptorImpl4 : iterable) {
                    boolean access$isInitialized = moduleDescriptorImpl4.isInitialized();
                    if (_Assertions.ENABLED && !access$isInitialized) {
                        throw new AssertionError("Dependency module " + moduleDescriptorImpl4.getId() + " was not initialized by the time contents of dependent module " + moduleDescriptorImpl3.getId() + " were queried");
                    }
                }
                Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                for (ModuleDescriptorImpl access$getPackageFragmentProviderForModuleContent$p : iterable) {
                    PackageFragmentProvider access$getPackageFragmentProviderForModuleContent$p2 = access$getPackageFragmentProviderForModuleContent$p.packageFragmentProviderForModuleContent;
                    Intrinsics.checkNotNull(access$getPackageFragmentProviderForModuleContent$p2);
                    arrayList.add(access$getPackageFragmentProviderForModuleContent$p2);
                }
                return new CompositePackageFragmentProvider((List) arrayList);
            }
            throw new AssertionError("Module " + moduleDescriptorImpl2.getId() + " is not contained in his own dependencies, this is probably a misconfiguration");
        }
        throw new AssertionError("Dependencies of module " + moduleDescriptorImpl.getId() + " were not set before querying module content");
    }
}
