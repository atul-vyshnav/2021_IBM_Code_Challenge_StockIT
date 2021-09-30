package org.unimodules.apploader;

import java.util.List;
import org.unimodules.core.interfaces.Package;

public interface AppLoaderPackagesProviderInterface<ReactPackageType> {
    List<Package> getExpoPackages();

    List<ReactPackageType> getPackages();
}
