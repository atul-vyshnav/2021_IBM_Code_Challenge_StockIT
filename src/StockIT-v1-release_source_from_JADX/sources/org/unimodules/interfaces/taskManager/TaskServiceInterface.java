package org.unimodules.interfaces.taskManager;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Bundle;
import java.util.List;
import java.util.Map;
import org.unimodules.core.interfaces.SingletonModule;

public interface TaskServiceInterface extends SingletonModule {
    boolean cancelJob(JobService jobService, JobParameters jobParameters);

    void executeTask(TaskInterface taskInterface, Bundle bundle, Error error, TaskExecutionCallback taskExecutionCallback);

    List<TaskConsumerInterface> getTaskConsumers(String str);

    Bundle getTaskOptions(String str, String str2);

    List<Bundle> getTasksForAppId(String str);

    void handleIntent(Intent intent);

    boolean handleJob(JobService jobService, JobParameters jobParameters);

    boolean hasRegisteredTask(String str, String str2);

    boolean isStartedByHeadlessLoader(String str);

    void notifyTaskFinished(String str, String str2, Map<String, Object> map);

    void registerTask(String str, String str2, String str3, Class cls, Map<String, Object> map) throws Exception;

    void setTaskManager(TaskManagerInterface taskManagerInterface, String str, String str2);

    boolean taskHasConsumerOfClass(String str, String str2, Class cls);

    void unregisterAllTasksForAppId(String str);

    void unregisterTask(String str, String str2, Class cls) throws Exception;
}
