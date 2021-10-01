package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Collection;
import java.util.List;
import kotlin.LazyKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentProviderOptimized;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.TypeParameterResolver;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaPackageFragment;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaPackage;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.storage.CacheWithNotNullValues;

/* compiled from: LazyJavaPackageFragmentProvider.kt */
public final class LazyJavaPackageFragmentProvider implements PackageFragmentProviderOptimized {
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final LazyJavaResolverContext f1271c;
    private final CacheWithNotNullValues<FqName, LazyJavaPackageFragment> packageFragments;

    public LazyJavaPackageFragmentProvider(JavaResolverComponents javaResolverComponents) {
        Intrinsics.checkNotNullParameter(javaResolverComponents, "components");
        LazyJavaResolverContext lazyJavaResolverContext = new LazyJavaResolverContext(javaResolverComponents, TypeParameterResolver.EMPTY.INSTANCE, LazyKt.lazyOf(null));
        this.f1271c = lazyJavaResolverContext;
        this.packageFragments = lazyJavaResolverContext.getStorageManager().createCacheWithNotNullValues();
    }

    private final LazyJavaPackageFragment getPackageFragment(FqName fqName) {
        JavaPackage findPackage = this.f1271c.getComponents().getFinder().findPackage(fqName);
        if (findPackage == null) {
            return null;
        }
        return this.packageFragments.computeIfAbsent(fqName, new LazyJavaPackageFragmentProvider$getPackageFragment$1(this, findPackage));
    }

    public List<LazyJavaPackageFragment> getPackageFragments(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return CollectionsKt.listOfNotNull(getPackageFragment(fqName));
    }

    public void collectPackageFragments(FqName fqName, Collection<PackageFragmentDescriptor> collection) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(collection, "packageFragments");
        kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(collection, getPackageFragment(fqName));
    }

    public List<FqName> getSubPackagesOf(FqName fqName, Function1<? super Name, Boolean> function1) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        Intrinsics.checkNotNullParameter(function1, "nameFilter");
        LazyJavaPackageFragment packageFragment = getPackageFragment(fqName);
        List<FqName> subPackageFqNames$descriptors_jvm = packageFragment == null ? null : packageFragment.getSubPackageFqNames$descriptors_jvm();
        return subPackageFqNames$descriptors_jvm != null ? subPackageFqNames$descriptors_jvm : CollectionsKt.emptyList();
    }
}
