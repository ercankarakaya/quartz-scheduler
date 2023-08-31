package com.ercan.job;

import com.ercan.service.EmailService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class EmailJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailJob.class);

    @Autowired
    private EmailService mailService;

    @Autowired
    private MailProperties mailProperties;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Executing Job with key {}", context.getJobDetail().getKey());

        JobDataMap jobDataMap = context.getMergedJobDataMap();
        String subject = jobDataMap.getString("subject");
        String body = jobDataMap.getString("body");
        String recipientEmail = jobDataMap.getString("email");

        mailService.sendMail(mailProperties.getUsername(), recipientEmail, subject, body);
    }
}
