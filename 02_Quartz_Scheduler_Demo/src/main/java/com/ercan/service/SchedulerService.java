package com.ercan.service;

import org.quartz.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService implements InitializingBean {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    /**  Registers a job without a Trigger */
    private void register(Class<? extends Job> jobClass, String jobName, String jobGroup, boolean replace) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                .withIdentity(jobName, jobGroup)
                .storeDurably()
                .build();
        scheduler.addJob(jobDetail, replace);
    }

    /** Schedules a job using cron expression. */
    public void scheduleJob(Class<? extends Job> jobClass, String jobName, String jobGroup, String cronExpression) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        TriggerKey triggerKey = TriggerKey.triggerKey(jobGroup, jobName);

        if (!scheduler.checkExists(jobKey)) {
            register(jobClass, jobName, jobGroup, true);
        }

        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .forJob(jobDetail)
                .withSchedule(cronScheduleBuilder)
                .build();

        if (!scheduler.checkExists(triggerKey)) {
            scheduler.scheduleJob(cronTrigger);
        } else {
            scheduler.rescheduleJob(triggerKey, cronTrigger);
        }
    }
}
