package com.ercan.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

//@DisallowConcurrentExecution
public class MyJob extends AbstractJob{


    @Override
    protected void doExecute(JobExecutionContext jobExecutionContext) {
        System.out.println("MyJob started.");
    }
}
