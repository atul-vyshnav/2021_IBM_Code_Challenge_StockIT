package org.unimodules.interfaces.taskManager;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import java.util.Map;

public interface TaskConsumerInterface {
    boolean canReceiveCustomBroadcast(String str);

    boolean didCancelJob(JobService jobService, JobParameters jobParameters);

    boolean didExecuteJob(JobService jobService, JobParameters jobParameters);

    void didReceiveBroadcast(Intent intent);

    void didRegister(TaskInterface taskInterface);

    void didUnregister();

    void setOptions(Map<String, Object> map);

    String taskType();
}
