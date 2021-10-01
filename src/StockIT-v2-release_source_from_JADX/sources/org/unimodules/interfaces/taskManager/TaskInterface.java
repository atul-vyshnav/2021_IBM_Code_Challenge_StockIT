package org.unimodules.interfaces.taskManager;

import android.os.Bundle;
import java.util.Map;

public interface TaskInterface {
    void execute(Bundle bundle, Error error);

    void execute(Bundle bundle, Error error, TaskExecutionCallback taskExecutionCallback);

    String getAppId();

    String getAppUrl();

    TaskConsumerInterface getConsumer();

    String getName();

    Map<String, Object> getOptions();

    Bundle getOptionsBundle();

    void setOptions(Map<String, Object> map);
}
