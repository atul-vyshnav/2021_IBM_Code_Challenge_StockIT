package bleshadow.dagger.releasablereferences;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.ANNOTATION_TYPE})
public @interface CanReleaseReferences {
}
