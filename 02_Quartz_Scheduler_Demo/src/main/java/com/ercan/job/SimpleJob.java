package com.ercan.job;

import com.ercan.annotation.QuartzJob;
import com.ercan.service.SimpleService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;


@QuartzJob
@DisallowConcurrentExecution
public class SimpleJob extends AbstractJob {

    @Autowired
    private SimpleService simpleService;


    @Override
    protected void doExecute(JobExecutionContext jobExecutionContext) {
         simpleService.getSimpleMethod();
    }
}
