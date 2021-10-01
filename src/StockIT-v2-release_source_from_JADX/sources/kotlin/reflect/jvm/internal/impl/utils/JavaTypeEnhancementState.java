package kotlin.reflect.jvm.internal.impl.utils;

import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: JavaTypeEnhancementState.kt */
public final class JavaTypeEnhancementState {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final JavaTypeEnhancementState DEFAULT = new JavaTypeEnhancementState(ReportLevel.WARN, (ReportLevel) null, MapsKt.emptyMap(), false, (ReportLevel) null, 24, (DefaultConstructorMarker) null);
    public static final ReportLevel DEFAULT_REPORT_LEVEL_FOR_JSPECIFY = ReportLevel.WARN;
    public static final JavaTypeEnhancementState DISABLED_JSR_305 = new JavaTypeEnhancementState(ReportLevel.IGNORE, ReportLevel.IGNORE, MapsKt.emptyMap(), false, (ReportLevel) null, 24, (DefaultConstructorMarker) null);
    public static final JavaTypeEnhancementState STRICT = new JavaTypeEnhancementState(ReportLevel.STRICT, ReportLevel.STRICT, MapsKt.emptyMap(), false, (ReportLevel) null, 24, (DefaultConstructorMarker) null);
    private final Lazy description$delegate;
    private final boolean disabledDefaultAnnotations;
    private final boolean disabledJsr305;
    private final boolean enableCompatqualCheckerFrameworkAnnotations;
    private final ReportLevel globalJsr305Level;
    private final ReportLevel jspecifyReportLevel;
    private final ReportLevel migrationLevelForJsr305;
    private final Map<String, ReportLevel> userDefinedLevelForSpecificJsr305Annotation;

    public JavaTypeEnhancementState(ReportLevel reportLevel, ReportLevel reportLevel2, Map<String, ? extends ReportLevel> map, boolean z, ReportLevel reportLevel3) {
        Intrinsics.checkNotNullParameter(reportLevel, "globalJsr305Level");
        Intrinsics.checkNotNullParameter(map, "userDefinedLevelForSpecificJsr305Annotation");
        Intrinsics.checkNotNullParameter(reportLevel3, "jspecifyReportLevel");
        this.globalJsr305Level = reportLevel;
        this.migrationLevelForJsr305 = reportLevel2;
        this.userDefinedLevelForSpecificJsr305Annotation = map;
        this.enableCompatqualCheckerFrameworkAnnotations = z;
        this.jspecifyReportLevel = reportLevel3;
        this.description$delegate = LazyKt.lazy(new JavaTypeEnhancementState$description$2(this));
        boolean z2 = true;
        boolean z3 = this.globalJsr305Level == ReportLevel.IGNORE && this.migrationLevelForJsr305 == ReportLevel.IGNORE && this.userDefinedLevelForSpecificJsr305Annotation.isEmpty();
        this.disabledJsr305 = z3;
        if (!z3 && this.jspecifyReportLevel != ReportLevel.IGNORE) {
            z2 = false;
        }
        this.disabledDefaultAnnotations = z2;
    }

    public final ReportLevel getGlobalJsr305Level() {
        return this.globalJsr305Level;
    }

    public final ReportLevel getMigrationLevelForJsr305() {
        return this.migrationLevelForJsr305;
    }

    public final Map<String, ReportLevel> getUserDefinedLevelForSpecificJsr305Annotation() {
        return this.userDefinedLevelForSpecificJsr305Annotation;
    }

    public final boolean getEnableCompatqualCheckerFrameworkAnnotations() {
        return this.enableCompatqualCheckerFrameworkAnnotations;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ JavaTypeEnhancementState(ReportLevel reportLevel, ReportLevel reportLevel2, Map map, boolean z, ReportLevel reportLevel3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(reportLevel, reportLevel2, map, (i & 8) != 0 ? true : z, (i & 16) != 0 ? DEFAULT_REPORT_LEVEL_FOR_JSPECIFY : reportLevel3);
    }

    public final ReportLevel getJspecifyReportLevel() {
        return this.jspecifyReportLevel;
    }

    public final boolean getDisabledJsr305() {
        return this.disabledJsr305;
    }

    public final boolean getDisabledDefaultAnnotations() {
        return this.disabledDefaultAnnotations;
    }

    /* compiled from: JavaTypeEnhancementState.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
