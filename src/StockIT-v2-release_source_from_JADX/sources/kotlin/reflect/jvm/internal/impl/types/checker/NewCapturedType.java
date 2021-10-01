package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;

/* compiled from: NewCapturedType.kt */
public final class NewCapturedType extends SimpleType implements CapturedTypeMarker {
    private final Annotations annotations;
    private final CaptureStatus captureStatus;
    private final NewCapturedTypeConstructor constructor;
    private final boolean isMarkedNullable;
    private final boolean isProjectionNotNull;
    private final UnwrappedType lowerType;

    public final CaptureStatus getCaptureStatus() {
        return this.captureStatus;
    }

    public NewCapturedTypeConstructor getConstructor() {
        return this.constructor;
    }

    public final UnwrappedType getLowerType() {
        return this.lowerType;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ NewCapturedType(CaptureStatus captureStatus2, NewCapturedTypeConstructor newCapturedTypeConstructor, UnwrappedType unwrappedType, Annotations annotations2, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(captureStatus2, newCapturedTypeConstructor, unwrappedType, (i & 8) != 0 ? Annotations.Companion.getEMPTY() : annotations2, (i & 16) != 0 ? false : z, (i & 32) != 0 ? false : z2);
    }

    public Annotations getAnnotations() {
        return this.annotations;
    }

    public boolean isMarkedNullable() {
        return this.isMarkedNullable;
    }

    public final boolean isProjectionNotNull() {
        return this.isProjectionNotNull;
    }

    public NewCapturedType(CaptureStatus captureStatus2, NewCapturedTypeConstructor newCapturedTypeConstructor, UnwrappedType unwrappedType, Annotations annotations2, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(captureStatus2, "captureStatus");
        Intrinsics.checkNotNullParameter(newCapturedTypeConstructor, "constructor");
        Intrinsics.checkNotNullParameter(annotations2, "annotations");
        this.captureStatus = captureStatus2;
        this.constructor = newCapturedTypeConstructor;
        this.lowerType = unwrappedType;
        this.annotations = annotations2;
        this.isMarkedNullable = z;
        this.isProjectionNotNull = z2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public NewCapturedType(CaptureStatus captureStatus2, UnwrappedType unwrappedType, TypeProjection typeProjection, TypeParameterDescriptor typeParameterDescriptor) {
        this(captureStatus2, new NewCapturedTypeConstructor(typeProjection, (Function0) null, (NewCapturedTypeConstructor) null, typeParameterDescriptor, 6, (DefaultConstructorMarker) null), unwrappedType, (Annotations) null, false, false, 56, (DefaultConstructorMarker) null);
        Intrinsics.checkNotNullParameter(captureStatus2, "captureStatus");
        Intrinsics.checkNotNullParameter(typeProjection, "projection");
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameter");
    }

    public List<TypeProjection> getArguments() {
        return CollectionsKt.emptyList();
    }

    public MemberScope getMemberScope() {
        MemberScope createErrorScope = ErrorUtils.createErrorScope("No member resolution should be done on captured type!", true);
        Intrinsics.checkNotNullExpressionValue(createErrorScope, "createErrorScope(\"No member resolution should be done on captured type!\", true)");
        return createErrorScope;
    }

    public NewCapturedType replaceAnnotations(Annotations annotations2) {
        Intrinsics.checkNotNullParameter(annotations2, "newAnnotations");
        return new NewCapturedType(this.captureStatus, getConstructor(), this.lowerType, annotations2, isMarkedNullable(), false, 32, (DefaultConstructorMarker) null);
    }

    public NewCapturedType makeNullableAsSpecified(boolean z) {
        return new NewCapturedType(this.captureStatus, getConstructor(), this.lowerType, getAnnotations(), z, false, 32, (DefaultConstructorMarker) null);
    }

    public NewCapturedType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        CaptureStatus captureStatus2 = this.captureStatus;
        NewCapturedTypeConstructor refine = getConstructor().refine(kotlinTypeRefiner);
        UnwrappedType unwrappedType = this.lowerType;
        return new NewCapturedType(captureStatus2, refine, unwrappedType == null ? null : kotlinTypeRefiner.refineType(unwrappedType).unwrap(), getAnnotations(), isMarkedNullable(), false, 32, (DefaultConstructorMarker) null);
    }
}
