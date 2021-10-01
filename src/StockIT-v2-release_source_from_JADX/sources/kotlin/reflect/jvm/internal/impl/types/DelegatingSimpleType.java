package kotlin.reflect.jvm.internal.impl.types;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;

/* compiled from: SpecialTypes.kt */
public abstract class DelegatingSimpleType extends SimpleType {
    /* access modifiers changed from: protected */
    public abstract SimpleType getDelegate();

    public abstract DelegatingSimpleType replaceDelegate(SimpleType simpleType);

    public Annotations getAnnotations() {
        return getDelegate().getAnnotations();
    }

    public TypeConstructor getConstructor() {
        return getDelegate().getConstructor();
    }

    public List<TypeProjection> getArguments() {
        return getDelegate().getArguments();
    }

    public boolean isMarkedNullable() {
        return getDelegate().isMarkedNullable();
    }

    public MemberScope getMemberScope() {
        return getDelegate().getMemberScope();
    }

    public SimpleType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return replaceDelegate((SimpleType) kotlinTypeRefiner.refineType(getDelegate()));
    }
}
