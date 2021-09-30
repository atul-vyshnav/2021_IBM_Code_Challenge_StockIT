package org.apache.logging.log4j.util;

import java.lang.reflect.Method;
import java.util.Stack;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public final class ReflectionUtil {
    private static final Method GET_CALLER_CLASS;
    static final int JDK_7u25_OFFSET;
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final PrivateSecurityManager SECURITY_MANAGER;
    private static final boolean SUN_REFLECTION_SUPPORTED;

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0078  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0085 A[Catch:{ SecurityException -> 0x0096 }] */
    static {
        /*
            org.apache.logging.log4j.status.StatusLogger r0 = org.apache.logging.log4j.status.StatusLogger.getLogger()
            LOGGER = r0
            r0 = -1
            r1 = 0
            r2 = 1
            r3 = 0
            java.lang.String r4 = "sun.reflect.Reflection"
            java.lang.Class r4 = org.apache.logging.log4j.util.LoaderUtil.loadClass(r4)     // Catch:{ Exception -> 0x006c }
            java.lang.String r5 = "getCallerClass"
            java.lang.Class[] r6 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x006c }
            java.lang.Class r7 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x006c }
            r6[r3] = r7     // Catch:{ Exception -> 0x006c }
            java.lang.reflect.Method r5 = r4.getDeclaredMethod(r5, r6)     // Catch:{ Exception -> 0x006c }
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x006c }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x006c }
            r6[r3] = r7     // Catch:{ Exception -> 0x006c }
            java.lang.Object r6 = r5.invoke(r1, r6)     // Catch:{ Exception -> 0x006c }
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x006c }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x006c }
            r7[r3] = r8     // Catch:{ Exception -> 0x006c }
            java.lang.Object r7 = r5.invoke(r1, r7)     // Catch:{ Exception -> 0x006c }
            if (r6 == 0) goto L_0x0060
            if (r6 == r4) goto L_0x0039
            goto L_0x0060
        L_0x0039:
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x006c }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r2)     // Catch:{ Exception -> 0x006c }
            r6[r3] = r7     // Catch:{ Exception -> 0x006c }
            java.lang.Object r6 = r5.invoke(r1, r6)     // Catch:{ Exception -> 0x006c }
            if (r6 != r4) goto L_0x005e
            org.apache.logging.log4j.Logger r4 = LOGGER     // Catch:{ Exception -> 0x006c }
            java.lang.String r6 = "You are using Java 1.7.0_25 which has a broken implementation of Reflection.getCallerClass."
            r4.warn((java.lang.String) r6)     // Catch:{ Exception -> 0x006c }
            org.apache.logging.log4j.Logger r4 = LOGGER     // Catch:{ Exception -> 0x006c }
            java.lang.String r6 = "You should upgrade to at least Java 1.7.0_40 or later."
            r4.warn((java.lang.String) r6)     // Catch:{ Exception -> 0x006c }
            org.apache.logging.log4j.Logger r4 = LOGGER     // Catch:{ Exception -> 0x006c }
            java.lang.String r6 = "Using stack depth compensation offset of 1 due to Java 7u25."
            r4.debug((java.lang.String) r6)     // Catch:{ Exception -> 0x006c }
            r0 = 1
            goto L_0x0075
        L_0x005e:
            r0 = 0
            goto L_0x0075
        L_0x0060:
            org.apache.logging.log4j.Logger r4 = LOGGER     // Catch:{ Exception -> 0x006c }
            java.lang.String r5 = "Unexpected return value from Reflection.getCallerClass(): {}"
            java.lang.Object[] r6 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x006c }
            r6[r3] = r7     // Catch:{ Exception -> 0x006c }
            r4.warn((java.lang.String) r5, (java.lang.Object[]) r6)     // Catch:{ Exception -> 0x006c }
            goto L_0x0074
        L_0x006c:
            r4 = move-exception
            org.apache.logging.log4j.Logger r5 = LOGGER
            java.lang.String r6 = "sun.reflect.Reflection.getCallerClass is not supported. ReflectionUtil.getCallerClass will be much slower due to this."
            r5.info((java.lang.String) r6, (java.lang.Throwable) r4)
        L_0x0074:
            r5 = r1
        L_0x0075:
            if (r5 == 0) goto L_0x0078
            goto L_0x0079
        L_0x0078:
            r2 = 0
        L_0x0079:
            SUN_REFLECTION_SUPPORTED = r2
            GET_CALLER_CLASS = r5
            JDK_7u25_OFFSET = r0
            java.lang.SecurityManager r0 = java.lang.System.getSecurityManager()     // Catch:{ SecurityException -> 0x0096 }
            if (r0 == 0) goto L_0x008f
            java.lang.RuntimePermission r2 = new java.lang.RuntimePermission     // Catch:{ SecurityException -> 0x0096 }
            java.lang.String r3 = "createSecurityManager"
            r2.<init>(r3)     // Catch:{ SecurityException -> 0x0096 }
            r0.checkPermission(r2)     // Catch:{ SecurityException -> 0x0096 }
        L_0x008f:
            org.apache.logging.log4j.util.ReflectionUtil$PrivateSecurityManager r0 = new org.apache.logging.log4j.util.ReflectionUtil$PrivateSecurityManager     // Catch:{ SecurityException -> 0x0096 }
            r0.<init>()     // Catch:{ SecurityException -> 0x0096 }
            r1 = r0
            goto L_0x009d
        L_0x0096:
            org.apache.logging.log4j.Logger r0 = LOGGER
            java.lang.String r2 = "Not allowed to create SecurityManager. Falling back to slowest ReflectionUtil implementation."
            r0.debug((java.lang.String) r2)
        L_0x009d:
            SECURITY_MANAGER = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.logging.log4j.util.ReflectionUtil.<clinit>():void");
    }

    public static boolean supportsFastReflection() {
        return SUN_REFLECTION_SUPPORTED;
    }

    public static Class<?> getCallerClass(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException(Integer.toString(i));
        } else if (supportsFastReflection()) {
            try {
                return (Class) GET_CALLER_CLASS.invoke((Object) null, new Object[]{Integer.valueOf(i + 1 + JDK_7u25_OFFSET)});
            } catch (Exception e) {
                LOGGER.error("Error in ReflectionUtil.getCallerClass({}).", Integer.valueOf(i), e);
                return null;
            }
        } else {
            try {
                return LoaderUtil.loadClass(getEquivalentStackTraceElement(i + 1).getClassName());
            } catch (ClassNotFoundException e2) {
                LOGGER.error("Could not find class in ReflectionUtil.getCallerClass({}).", Integer.valueOf(i), e2);
                return null;
            }
        }
    }

    static StackTraceElement getEquivalentStackTraceElement(int i) {
        int i2 = 0;
        for (StackTraceElement stackTraceElement : new Throwable().getStackTrace()) {
            if (isValid(stackTraceElement)) {
                if (i2 == i) {
                    return stackTraceElement;
                }
                i2++;
            }
        }
        LOGGER.error("Could not find an appropriate StackTraceElement at index {}", Integer.valueOf(i));
        throw new IndexOutOfBoundsException(Integer.toString(i));
    }

    private static boolean isValid(StackTraceElement stackTraceElement) {
        if (stackTraceElement.isNativeMethod()) {
            return false;
        }
        String className = stackTraceElement.getClassName();
        if (className.startsWith("sun.reflect.")) {
            return false;
        }
        String methodName = stackTraceElement.getMethodName();
        if (className.startsWith("java.lang.reflect.") && (methodName.equals("invoke") || methodName.equals("newInstance"))) {
            return false;
        }
        if (className.equals("java.lang.Class") && methodName.equals("newInstance")) {
            return false;
        }
        if (!className.equals("java.lang.invoke.MethodHandle") || !methodName.startsWith("invoke")) {
            return true;
        }
        return false;
    }

    public static Class<?> getCallerClass(String str) {
        return getCallerClass(str, "");
    }

    public static Class<?> getCallerClass(String str, String str2) {
        if (supportsFastReflection()) {
            boolean z = false;
            int i = 2;
            while (true) {
                Class<?> callerClass = getCallerClass(i);
                if (callerClass == null) {
                    return null;
                }
                if (str.equals(callerClass.getName())) {
                    z = true;
                } else if (z && callerClass.getName().startsWith(str2)) {
                    return callerClass;
                }
                i++;
            }
        } else {
            PrivateSecurityManager privateSecurityManager = SECURITY_MANAGER;
            if (privateSecurityManager != null) {
                return privateSecurityManager.getCallerClass(str, str2);
            }
            try {
                return LoaderUtil.loadClass(getCallerClassName(str, str2, new Throwable().getStackTrace()));
            } catch (ClassNotFoundException unused) {
                return null;
            }
        }
    }

    public static Class<?> getCallerClass(Class<?> cls) {
        if (supportsFastReflection()) {
            boolean z = false;
            int i = 2;
            while (true) {
                Class<?> callerClass = getCallerClass(i);
                if (callerClass == null) {
                    return Object.class;
                }
                if (cls.equals(callerClass)) {
                    z = true;
                } else if (z) {
                    return callerClass;
                }
                i++;
            }
        } else {
            PrivateSecurityManager privateSecurityManager = SECURITY_MANAGER;
            if (privateSecurityManager != null) {
                return privateSecurityManager.getCallerClass(cls);
            }
            try {
                return LoaderUtil.loadClass(getCallerClassName(cls.getName(), "", new Throwable().getStackTrace()));
            } catch (ClassNotFoundException unused) {
                return Object.class;
            }
        }
    }

    private static String getCallerClassName(String str, String str2, StackTraceElement... stackTraceElementArr) {
        boolean z = false;
        for (StackTraceElement className : stackTraceElementArr) {
            String className2 = className.getClassName();
            if (className2.equals(str)) {
                z = true;
            } else if (z && className2.startsWith(str2)) {
                return className2;
            }
        }
        return Object.class.getName();
    }

    public static Stack<Class<?>> getCurrentStackTrace() {
        PrivateSecurityManager privateSecurityManager = SECURITY_MANAGER;
        if (privateSecurityManager != null) {
            Class[] classContext = privateSecurityManager.getClassContext();
            Stack<Class<?>> stack = new Stack<>();
            stack.ensureCapacity(classContext.length);
            for (Class push : classContext) {
                stack.push(push);
            }
            return stack;
        } else if (!supportsFastReflection()) {
            return new Stack<>();
        } else {
            Stack<Class<?>> stack2 = new Stack<>();
            int i = 1;
            while (true) {
                Class<?> callerClass = getCallerClass(i);
                if (callerClass == null) {
                    return stack2;
                }
                stack2.push(callerClass);
                i++;
            }
        }
    }

    static final class PrivateSecurityManager extends SecurityManager {
        PrivateSecurityManager() {
        }

        /* access modifiers changed from: protected */
        public Class<?>[] getClassContext() {
            return super.getClassContext();
        }

        /* access modifiers changed from: protected */
        public Class<?> getCallerClass(String str, String str2) {
            boolean z = false;
            for (Class<?> cls : getClassContext()) {
                if (str.equals(cls.getName())) {
                    z = true;
                } else if (z && cls.getName().startsWith(str2)) {
                    return cls;
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public Class<?> getCallerClass(Class<?> cls) {
            boolean z = false;
            for (Class<?> cls2 : getClassContext()) {
                if (cls.equals(cls2)) {
                    z = true;
                } else if (z) {
                    return cls2;
                }
            }
            return Object.class;
        }
    }

    private ReflectionUtil() {
    }
}
