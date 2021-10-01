package kotlin.reflect.jvm.internal.impl.descriptors.annotations;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: AnnotationUseSiteTarget.kt */
public enum AnnotationUseSiteTarget {
    FIELD((String) null, 1, (String) null),
    FILE((String) null, 1, (String) null),
    PROPERTY((String) null, 1, (String) null),
    PROPERTY_GETTER("get"),
    PROPERTY_SETTER("set"),
    RECEIVER((String) null, 1, (String) null),
    CONSTRUCTOR_PARAMETER("param"),
    SETTER_PARAMETER("setparam"),
    PROPERTY_DELEGATE_FIELD("delegate");
    
    private final String renderName;

    private AnnotationUseSiteTarget(String str) {
        if (str == null) {
            String name = name();
            if (name != null) {
                str = name.toLowerCase();
                Intrinsics.checkNotNullExpressionValue(str, "(this as java.lang.String).toLowerCase()");
            } else {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }
        }
        this.renderName = str;
    }

    public final String getRenderName() {
        return this.renderName;
    }
}
