package kotlin.reflect.jvm.internal.impl.descriptors.runtime.components;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.text.StringsKt;

/* compiled from: ReflectKotlinClassFinder.kt */
public final class ReflectKotlinClassFinderKt {
    /* access modifiers changed from: private */
    public static final String toRuntimeFqName(ClassId classId) {
        String asString = classId.getRelativeClassName().asString();
        Intrinsics.checkNotNullExpressionValue(asString, "relativeClassName.asString()");
        String replace$default = StringsKt.replace$default(asString, '.', '$', false, 4, (Object) null);
        if (classId.getPackageFqName().isRoot()) {
            return replace$default;
        }
        return classId.getPackageFqName() + '.' + replace$default;
    }
}
