package org.apache.commons.p029io.serialization;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* renamed from: org.apache.commons.io.serialization.FullClassNameMatcher */
final class FullClassNameMatcher implements ClassNameMatcher {
    private final Set<String> classesSet;

    public FullClassNameMatcher(String... strArr) {
        this.classesSet = Collections.unmodifiableSet(new HashSet(Arrays.asList(strArr)));
    }

    public boolean matches(String str) {
        return this.classesSet.contains(str);
    }
}
