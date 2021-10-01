package org.unimodules.interfaces.taskManager;

import java.util.Map;

public interface TaskExecutionCallback {
    void onFinished(Map<String, Object> map);
}
