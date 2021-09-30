package bleshadow.dagger.releasablereferences;

import java.lang.annotation.Annotation;

public interface ReleasableReferenceManager {
    void releaseStrongReferences();

    void restoreStrongReferences();

    Class<? extends Annotation> scope();
}
