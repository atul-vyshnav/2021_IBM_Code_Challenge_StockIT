package org.unimodules.interfaces.taskManager;

import android.os.Bundle;
import java.util.Map;

public interface TaskManagerInterface {
    public static final String ERR_TASK_SERVICE_NOT_FOUND = "ERR_TASK_SERVICE_NOT_FOUND";
    public static final String EVENT_NAME = "TaskManager.executeTask";

    void executeTaskWithBody(Bundle bundle);

    void flushQueuedEvents();

    String getAppId();

    void registerTask(String str, Class cls, Map<String, Object> map) throws Exception;

    boolean taskHasConsumerOfClass(String str, Class cls);

    void unregisterTask(String str, Class cls) throws Exception;
}
