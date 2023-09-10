package com.ercan.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public abstract class AbstractJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);


    abstract protected void doExecute(JobExecutionContext jobExecutionContext);

    @Override
    public void execute(JobExecutionContext jobExecutionContext){
        Assert.notNull(jobExecutionContext, "jobExecutionContext must not be null");

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Job execution starting: " + jobExecutionContext);
        }

        try {
            doExecute(jobExecutionContext);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Job execution complete: " + jobExecutionContext);
            }
        } catch (Exception ex) {
            LOGGER.error("Job executing failed! Error : " + ex+", JobDetail: {}",jobExecutionContext);
        }
    }
}
