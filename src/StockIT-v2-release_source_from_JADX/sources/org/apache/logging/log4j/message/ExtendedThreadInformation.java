package org.apache.logging.log4j.message;

import java.lang.Thread;
import java.lang.management.LockInfo;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import kotlin.text.Typography;

class ExtendedThreadInformation implements ThreadInformation {
    private final ThreadInfo threadInfo;

    public ExtendedThreadInformation(ThreadInfo threadInfo2) {
        this.threadInfo = threadInfo2;
    }

    public void printThreadInfo(StringBuilder sb) {
        sb.append(Typography.quote);
        sb.append(this.threadInfo.getThreadName());
        sb.append(Typography.quote);
        sb.append(" Id=");
        sb.append(this.threadInfo.getThreadId());
        sb.append(' ');
        formatState(sb, this.threadInfo);
        if (this.threadInfo.isSuspended()) {
            sb.append(" (suspended)");
        }
        if (this.threadInfo.isInNative()) {
            sb.append(" (in native)");
        }
        sb.append(10);
    }

    public void printStack(StringBuilder sb, StackTraceElement[] stackTraceElementArr) {
        int i = 0;
        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
            sb.append("\tat ");
            sb.append(stackTraceElement.toString());
            sb.append(10);
            if (i == 0 && this.threadInfo.getLockInfo() != null) {
                int i2 = C35051.$SwitchMap$java$lang$Thread$State[this.threadInfo.getThreadState().ordinal()];
                if (i2 == 1) {
                    sb.append("\t-  blocked on ");
                    formatLock(sb, this.threadInfo.getLockInfo());
                    sb.append(10);
                } else if (i2 == 2) {
                    sb.append("\t-  waiting on ");
                    formatLock(sb, this.threadInfo.getLockInfo());
                    sb.append(10);
                } else if (i2 == 3) {
                    sb.append("\t-  waiting on ");
                    formatLock(sb, this.threadInfo.getLockInfo());
                    sb.append(10);
                }
            }
            for (MonitorInfo monitorInfo : this.threadInfo.getLockedMonitors()) {
                if (monitorInfo.getLockedStackDepth() == i) {
                    sb.append("\t-  locked ");
                    formatLock(sb, monitorInfo);
                    sb.append(10);
                }
            }
            i++;
        }
        LockInfo[] lockedSynchronizers = this.threadInfo.getLockedSynchronizers();
        if (lockedSynchronizers.length > 0) {
            sb.append("\n\tNumber of locked synchronizers = ");
            sb.append(lockedSynchronizers.length);
            sb.append(10);
            for (LockInfo formatLock : lockedSynchronizers) {
                sb.append("\t- ");
                formatLock(sb, formatLock);
                sb.append(10);
            }
        }
    }

    /* renamed from: org.apache.logging.log4j.message.ExtendedThreadInformation$1 */
    static /* synthetic */ class C35051 {
        static final /* synthetic */ int[] $SwitchMap$java$lang$Thread$State;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                java.lang.Thread$State[] r0 = java.lang.Thread.State.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$java$lang$Thread$State = r0
                java.lang.Thread$State r1 = java.lang.Thread.State.BLOCKED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$java$lang$Thread$State     // Catch:{ NoSuchFieldError -> 0x001d }
                java.lang.Thread$State r1 = java.lang.Thread.State.WAITING     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$java$lang$Thread$State     // Catch:{ NoSuchFieldError -> 0x0028 }
                java.lang.Thread$State r1 = java.lang.Thread.State.TIMED_WAITING     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.logging.log4j.message.ExtendedThreadInformation.C35051.<clinit>():void");
        }
    }

    private void formatLock(StringBuilder sb, LockInfo lockInfo) {
        sb.append(Typography.less);
        sb.append(lockInfo.getIdentityHashCode());
        sb.append("> (a ");
        sb.append(lockInfo.getClassName());
        sb.append(')');
    }

    private void formatState(StringBuilder sb, ThreadInfo threadInfo2) {
        Thread.State threadState = threadInfo2.getThreadState();
        sb.append(threadState);
        int i = C35051.$SwitchMap$java$lang$Thread$State[threadState.ordinal()];
        if (i == 1) {
            sb.append(" (on object monitor owned by \"");
            sb.append(threadInfo2.getLockOwnerName());
            sb.append("\" Id=");
            sb.append(threadInfo2.getLockOwnerId());
            sb.append(')');
        } else if (i == 2) {
            StackTraceElement stackTraceElement = threadInfo2.getStackTrace()[0];
            String className = stackTraceElement.getClassName();
            String methodName = stackTraceElement.getMethodName();
            if (className.equals("java.lang.Object") && methodName.equals("wait")) {
                sb.append(" (on object monitor");
                if (threadInfo2.getLockOwnerName() != null) {
                    sb.append(" owned by \"");
                    sb.append(threadInfo2.getLockOwnerName());
                    sb.append("\" Id=");
                    sb.append(threadInfo2.getLockOwnerId());
                }
                sb.append(')');
            } else if (!className.equals("java.lang.Thread") || !methodName.equals("join")) {
                sb.append(" (parking for lock");
                if (threadInfo2.getLockOwnerName() != null) {
                    sb.append(" owned by \"");
                    sb.append(threadInfo2.getLockOwnerName());
                    sb.append("\" Id=");
                    sb.append(threadInfo2.getLockOwnerId());
                }
                sb.append(')');
            } else {
                sb.append(" (on completion of thread ");
                sb.append(threadInfo2.getLockOwnerId());
                sb.append(')');
            }
        } else if (i == 3) {
            StackTraceElement stackTraceElement2 = threadInfo2.getStackTrace()[0];
            String className2 = stackTraceElement2.getClassName();
            String methodName2 = stackTraceElement2.getMethodName();
            if (className2.equals("java.lang.Object") && methodName2.equals("wait")) {
                sb.append(" (on object monitor");
                if (threadInfo2.getLockOwnerName() != null) {
                    sb.append(" owned by \"");
                    sb.append(threadInfo2.getLockOwnerName());
                    sb.append("\" Id=");
                    sb.append(threadInfo2.getLockOwnerId());
                }
                sb.append(')');
            } else if (className2.equals("java.lang.Thread") && methodName2.equals("sleep")) {
                sb.append(" (sleeping)");
            } else if (!className2.equals("java.lang.Thread") || !methodName2.equals("join")) {
                sb.append(" (parking for lock");
                if (threadInfo2.getLockOwnerName() != null) {
                    sb.append(" owned by \"");
                    sb.append(threadInfo2.getLockOwnerName());
                    sb.append("\" Id=");
                    sb.append(threadInfo2.getLockOwnerId());
                }
                sb.append(')');
            } else {
                sb.append(" (on completion of thread ");
                sb.append(threadInfo2.getLockOwnerId());
                sb.append(')');
            }
        }
    }
}
