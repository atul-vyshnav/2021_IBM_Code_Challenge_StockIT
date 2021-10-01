package kotlin.reflect.jvm.internal.impl.builtins;

import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;

/* compiled from: PrimitiveType.kt */
public enum PrimitiveType {
    BOOLEAN("Boolean"),
    CHAR("Char"),
    BYTE("Byte"),
    SHORT("Short"),
    INT("Int"),
    FLOAT("Float"),
    LONG("Long"),
    DOUBLE("Double");
    
    public static final Companion Companion = null;
    public static final Set<PrimitiveType> NUMBER_TYPES = null;
    private final Lazy arrayTypeFqName$delegate;
    private final Name arrayTypeName;
    private final Lazy typeFqName$delegate;
    private final Name typeName;

    private PrimitiveType(String str) {
        Name identifier = Name.identifier(str);
        Intrinsics.checkNotNullExpressionValue(identifier, "identifier(typeName)");
        this.typeName = identifier;
        Name identifier2 = Name.identifier(Intrinsics.stringPlus(str, "Array"));
        Intrinsics.checkNotNullExpressionValue(identifier2, "identifier(\"${typeName}Array\")");
        this.arrayTypeName = identifier2;
        this.typeFqName$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, new PrimitiveType$typeFqName$2(this));
        this.arrayTypeFqName$delegate = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, new PrimitiveType$arrayTypeFqName$2(this));
    }

    static {
        Companion = new Companion((DefaultConstructorMarker) null);
        NUMBER_TYPES = SetsKt.setOf(CHAR, BYTE, SHORT, INT, FLOAT, LONG, DOUBLE);
    }

    public final Name getTypeName() {
        return this.typeName;
    }

    public final Name getArrayTypeName() {
        return this.arrayTypeName;
    }

    public final FqName getTypeFqName() {
        return (FqName) this.typeFqName$delegate.getValue();
    }

    public final FqName getArrayTypeFqName() {
        return (FqName) this.arrayTypeFqName$delegate.getValue();
    }

    /* compiled from: PrimitiveType.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
