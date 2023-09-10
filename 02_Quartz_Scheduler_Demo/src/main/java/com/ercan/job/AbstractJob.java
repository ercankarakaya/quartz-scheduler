package com.ercan.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.Assert;

import java.util.Date;

public abstract class AbstractJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);


    abstract protected void doExecute(JobExecutionContext jobExecutionContext);

    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext) {
        Assert.notNull(jobExecutionContext, "jobExecutionContext must not be null");
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        LOGGER.info("Job execution started with ThreadName: {}, JobName: {}, JobGroup: {}, TimeNow: {} ",
                Thread.currentThread().getName(), jobKey.getName(), jobKey.getGroup(), new Date());

        try {
            doExecute(jobExecutionContext);
            LOGGER.info("Job execution completed. ThreadName: {} ", Thread.currentThread().getName());
        } catch (Exception ex) {
            LOGGER.error("Job executing failed! Error : " + ex + ", JobDetail: {}", jobExecutionContext);
        }
    }
}
