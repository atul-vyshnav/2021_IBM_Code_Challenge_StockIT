package kotlin.reflect.jvm.internal.impl.load.java.structure;

import kotlin.reflect.jvm.internal.impl.load.java.structure.ListBasedJavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.name.FqName;

/* compiled from: javaTypes.kt */
public interface JavaType extends ListBasedJavaAnnotationOwner {

    /* compiled from: javaTypes.kt */
    public static final class DefaultImpls {
        public static JavaAnnotation findAnnotation(JavaType javaType, FqName fqName) {
            return ListBasedJavaAnnotationOwner.DefaultImpls.findAnnotation(javaType, fqName);
        }
    }
}
