package kotlin.reflect.jvm.internal.impl.descriptors.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProvider;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProviderKt;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProviderOptimized;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

/* compiled from: CompositePackageFragmentProvider.kt */
public final class CompositePackageFragmentProvider implements PackageFragmentProviderOptimized {
    private final List<PackageFragmentProvider> providers;

    public CompositePackageFragmentProvider(List<? extends PackageFragmentProvider> list) {
        Intrinsics.checkNotNullParameter(list, "providers");
        this.providers = list;
        boolean z = list.size() == CollectionsKt.toSet(this.providers).size();
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("providers.size is " + this.providers.size() + " while only " + CollectionsKt.toSet(this.providers).size() + " unique providers");
        }
    }

    public List<PackageFragmentDescriptor> getPackageFragments(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ArrayList arrayList = new ArrayList();
        for (PackageFragmentProvider collectPackageFragmentsOptimizedIfPossible : this.providers) {
            PackageFragmentProviderKt.collectPackageFragmentsOptimizedIfPossible(collectPackageFragmentsOptimizedIfPossible, fqName, arrayList);
        }
        return CollectionsKt.toList(arrayList);
    }

    public void collectPackageFragments(FqName fqName, Collection<PackageFragmentDescriptor> collection) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(collection, "packageFragments");
        for (PackageFragmentProvider collectPackageFragmentsOptimizedIfPossible : this.providers) {
            PackageFragmentProviderKt.collectPackageFragmentsOptimizedIfPossible(collectPackageFragmentsOptimizedIfPossible, fqName, collection);
        }
    }

    public Collection<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> function1) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(function1, "nameFilter");
        HashSet hashSet = new HashSet();
        for (PackageFragmentProvider subPackagesOf : this.providers) {
            hashSet.addAll(subPackagesOf.getSubPackagesOf(fqName, function1));
        }
        return hashSet;
    }
}
