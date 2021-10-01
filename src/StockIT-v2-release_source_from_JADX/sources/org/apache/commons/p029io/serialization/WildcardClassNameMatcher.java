package org.apache.commons.p029io.serialization;

import org.apache.commons.p029io.FilenameUtils;

/* renamed from: org.apache.commons.io.serialization.WildcardClassNameMatcher */
final class WildcardClassNameMatcher implements ClassNameMatcher {
    private final String pattern;

    public WildcardClassNameMatcher(String str) {
        this.pattern = str;
    }

    public boolean matches(String str) {
        return FilenameUtils.wildcardMatch(str, this.pattern);
    }
}
