package kotlin.reflect.full;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KAnnotatedElement;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u001a \u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\b¢\u0006\u0002\u0010\u0004\u001a\u0019\u0010\u0005\u001a\u00020\u0006\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u00020\u0003H\b¨\u0006\u0007"}, mo40253d2 = {"findAnnotation", "T", "", "Lkotlin/reflect/KAnnotatedElement;", "(Lkotlin/reflect/KAnnotatedElement;)Ljava/lang/annotation/Annotation;", "hasAnnotation", "", "kotlin-reflection"}, mo40254k = 2, mo40255mv = {1, 4, 1})
/* compiled from: KAnnotatedElements.kt */
public final class KAnnotatedElements {
    public static final /* synthetic */ <T extends Annotation> T findAnnotation(KAnnotatedElement kAnnotatedElement) {
        T t;
        Intrinsics.checkNotNullParameter(kAnnotatedElement, "$this$findAnnotation");
        Iterator it = kAnnotatedElement.getAnnotations().iterator();
        while (true) {
            if (!it.hasNext()) {
                t = null;
                break;
            }
            t = it.next();
            Intrinsics.reifiedOperationMarker(3, "T");
            if (((Annotation) t) instanceof Annotation) {
                break;
            }
        }
        Intrinsics.reifiedOperationMarker(1, "T?");
        return (Annotation) t;
    }

    public static final /* synthetic */ <T extends Annotation> boolean hasAnnotation(KAnnotatedElement kAnnotatedElement) {
        Object obj;
        Intrinsics.checkNotNullParameter(kAnnotatedElement, "$this$hasAnnotation");
        Iterator it = kAnnotatedElement.getAnnotations().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            Intrinsics.reifiedOperationMarker(3, "T");
            if (((Annotation) obj) instanceof Annotation) {
                break;
            }
        }
        Intrinsics.reifiedOperationMarker(1, "T?");
        if (((Annotation) obj) != null) {
            return true;
        }
        return false;
    }
}
