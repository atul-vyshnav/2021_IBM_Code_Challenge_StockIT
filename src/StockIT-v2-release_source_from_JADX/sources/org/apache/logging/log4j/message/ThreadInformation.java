package org.apache.logging.log4j.message;

interface ThreadInformation {
    void printStack(StringBuilder sb, StackTraceElement[] stackTraceElementArr);

    void printThreadInfo(StringBuilder sb);
}
