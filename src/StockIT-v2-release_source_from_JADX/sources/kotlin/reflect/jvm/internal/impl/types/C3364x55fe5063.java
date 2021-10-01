package kotlin.reflect.jvm.internal.impl.types;

import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;

/* renamed from: kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor$makeDebugNameForIntersectionType$$inlined$sortedBy$1 */
/* compiled from: Comparisons.kt */
public final class C3364x55fe5063<T> implements Comparator<T> {
    public final int compare(T t, T t2) {
        return ComparisonsKt.compareValues(((KotlinType) t).toString(), ((KotlinType) t2).toString());
    }
}
