package com.ercan.job;

import com.ercan.annotation.QuartzJob;
import com.ercan.service.SimpleService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@QuartzJob
@DisallowConcurrentExecution
public class SimpleJob extends AbstractJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleJob.class);

    @Autowired
    private SimpleService simpleService;


    @Override
    protected void doExecute(JobExecutionContext jobExecutionContext) {
         simpleService.getSimpleMethod();
    }
}
