package org.apache.logging.log4j.message;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Typography;

public class ThreadDumpMessage implements Message {
    private static final ThreadInfoFactory FACTORY;
    private static final long serialVersionUID = -1103400781608841088L;
    private String formattedMessage;
    private volatile Map<ThreadInformation, StackTraceElement[]> threads;
    /* access modifiers changed from: private */
    public final String title;

    private interface ThreadInfoFactory {
        Map<ThreadInformation, StackTraceElement[]> createThreadInfo();
    }

    public Object[] getParameters() {
        return null;
    }

    public Throwable getThrowable() {
        return null;
    }

    static {
        Method[] methods = ThreadInfo.class.getMethods();
        int length = methods.length;
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = true;
                break;
            } else if (methods[i].getName().equals("getLockInfo")) {
                break;
            } else {
                i++;
            }
        }
        FACTORY = z ? new BasicThreadInfoFactory() : new ExtendedThreadInfoFactory();
    }

    public ThreadDumpMessage(String str) {
        this.title = str == null ? "" : str;
        this.threads = FACTORY.createThreadInfo();
    }

    private ThreadDumpMessage(String str, String str2) {
        this.formattedMessage = str;
        this.title = str2 == null ? "" : str2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ThreadDumpMessage[");
        if (this.title.length() > 0) {
            sb.append("Title=\"");
            sb.append(this.title);
            sb.append(Typography.quote);
        }
        sb.append(']');
        return sb.toString();
    }

    public String getFormattedMessage() {
        String str = this.formattedMessage;
        if (str != null) {
            return str;
        }
        StringBuilder sb = new StringBuilder(this.title);
        if (this.title.length() > 0) {
            sb.append(10);
        }
        for (Map.Entry next : this.threads.entrySet()) {
            ThreadInformation threadInformation = (ThreadInformation) next.getKey();
            threadInformation.printThreadInfo(sb);
            threadInformation.printStack(sb, (StackTraceElement[]) next.getValue());
            sb.append(10);
        }
        return sb.toString();
    }

    public String getFormat() {
        String str = this.title;
        return str == null ? "" : str;
    }

    /* access modifiers changed from: protected */
    public Object writeReplace() {
        return new ThreadDumpMessageProxy(this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Proxy required");
    }

    private static class ThreadDumpMessageProxy implements Serializable {
        private static final long serialVersionUID = -3476620450287648269L;
        private final String formattedMsg;
        private final String title;

        public ThreadDumpMessageProxy(ThreadDumpMessage threadDumpMessage) {
            this.formattedMsg = threadDumpMessage.getFormattedMessage();
            this.title = threadDumpMessage.title;
        }

        /* access modifiers changed from: protected */
        public Object readResolve() {
            return new ThreadDumpMessage(this.formattedMsg, this.title);
        }
    }

    private static class BasicThreadInfoFactory implements ThreadInfoFactory {
        private BasicThreadInfoFactory() {
        }

        public Map<ThreadInformation, StackTraceElement[]> createThreadInfo() {
            Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
            HashMap hashMap = new HashMap(allStackTraces.size());
            for (Map.Entry next : allStackTraces.entrySet()) {
                hashMap.put(new BasicThreadInformation((Thread) next.getKey()), next.getValue());
            }
            return hashMap;
        }
    }

    private static class ExtendedThreadInfoFactory implements ThreadInfoFactory {
        private ExtendedThreadInfoFactory() {
        }

        public Map<ThreadInformation, StackTraceElement[]> createThreadInfo() {
            ThreadInfo[] dumpAllThreads = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
            HashMap hashMap = new HashMap(dumpAllThreads.length);
            for (ThreadInfo threadInfo : dumpAllThreads) {
                hashMap.put(new ExtendedThreadInformation(threadInfo), threadInfo.getStackTrace());
            }
            return hashMap;
        }
    }
}
