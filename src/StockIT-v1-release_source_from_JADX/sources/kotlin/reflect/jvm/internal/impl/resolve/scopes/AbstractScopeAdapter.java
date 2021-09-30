package kotlin.reflect.jvm.internal.impl.resolve.scopes;

import java.util.Collection;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.name.Name;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;

/* compiled from: AbstractScopeAdapter.kt */
public abstract class AbstractScopeAdapter implements MemberScope {
    /* access modifiers changed from: protected */
    public abstract MemberScope getWorkerScope();

    public final MemberScope getActualScope() {
        if (getWorkerScope() instanceof AbstractScopeAdapter) {
            return ((AbstractScopeAdapter) getWorkerScope()).getActualScope();
        }
        return getWorkerScope();
    }

    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name, LookupLocation lookupLocation) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(lookupLocation, GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID);
        return getWorkerScope().getContributedFunctions(name, lookupLocation);
    }

    public ClassifierDescriptor getContributedClassifier(Name name, LookupLocation lookupLocation) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(lookupLocation, GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID);
        return getWorkerScope().getContributedClassifier(name, lookupLocation);
    }

    public Collection<PropertyDescriptor> getContributedVariables(Name name, LookupLocation lookupLocation) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(lookupLocation, GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID);
        return getWorkerScope().getContributedVariables(name, lookupLocation);
    }

    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter descriptorKindFilter, Function1<? super Name, Boolean> function1) {
        Intrinsics.checkNotNullParameter(descriptorKindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(function1, "nameFilter");
        return getWorkerScope().getContributedDescriptors(descriptorKindFilter, function1);
    }

    public Set<Name> getFunctionNames() {
        return getWorkerScope().getFunctionNames();
    }

    public Set<Name> getVariableNames() {
        return getWorkerScope().getVariableNames();
    }

    public Set<Name> getClassifierNames() {
        return getWorkerScope().getClassifierNames();
    }

    public void recordLookup(Name name, LookupLocation lookupLocation) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(lookupLocation, GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID);
        getWorkerScope().recordLookup(name, lookupLocation);
    }
}
