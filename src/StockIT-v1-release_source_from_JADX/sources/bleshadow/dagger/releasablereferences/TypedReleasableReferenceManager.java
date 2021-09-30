package bleshadow.dagger.releasablereferences;

import java.lang.annotation.Annotation;

public interface TypedReleasableReferenceManager<M extends Annotation> extends ReleasableReferenceManager {
    M metadata();
}
