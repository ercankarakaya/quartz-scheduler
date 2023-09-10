package com.ercan.job;

import com.ercan.annotation.QuartzJob;
import com.ercan.service.SimpleService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * InterruptableJob is a marker interface that allows running jobs in Quartz Scheduler to be interruptible.
 * Thanks to this interface, if a job task is interrupted while it is running, the job task can terminate its execution.
 * The use of InterruptableJob is useful for keeping the job safe during extended or potentially freezing operations of job tasks.
 * For example, a job task may be interrupted when processing a large set of data from the database or performing a long-running operation over the network.
 */

//@QuartzJob
//@DisallowConcurrentExecution
public class SimpleJob extends AbstractJob implements InterruptableJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJob.class);


    private volatile boolean interrupted = true;

    @Autowired
    private SimpleService simpleService;


    @Override
    protected void doExecute(JobExecutionContext jobExecutionContext) {
        simpleService.getSimpleMethod();
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        LOGGER.info("Stopping thread...");
        interrupted = false;
    }
}
