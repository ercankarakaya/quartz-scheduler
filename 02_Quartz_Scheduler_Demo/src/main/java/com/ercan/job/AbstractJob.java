package com.ercan.job;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.Assert;

public abstract class AbstractJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);


    abstract protected void doExecute(JobExecutionContext jobExecutionContext);

    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext) {
        Assert.notNull(jobExecutionContext, "jobExecutionContext must not be null");
        LOGGER.info("Job execution starting: " + jobExecutionContext);

        try {
            doExecute(jobExecutionContext);
            LOGGER.info("Job execution complete: " + jobExecutionContext);
        } catch (Exception ex) {
            LOGGER.error("Job executing failed! Error : " + ex + ", JobDetail: {}", jobExecutionContext);
        }
    }
}
