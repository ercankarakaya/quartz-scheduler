package com.ercan.controller;

import com.ercan.job.EmailJob;
import com.ercan.model.EmailScheduleRequest;
import com.ercan.model.EmailScheduleResponse;
import org.quartz.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;


@RestController
@RequestMapping("/emailJobSchedule")
public class EmailJobSchedulerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailJobSchedulerController.class);

    @Autowired
    private Scheduler scheduler;

    /* Custom Messages */
    private static final String EMAIL_SCHEDULE_SUCCESS = "Email Scheduled Succesfully";
    private static final String EMAIL_SCHEDULE_UNSUCCESSFUL = "Error email scheduling!";
    private static final String DATETIME_BEFORE_ERROR = "DateTime must be after current time!";


    @PostMapping
    public ResponseEntity<EmailScheduleResponse> emailSchedule(@RequestBody @Validated EmailScheduleRequest request) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(request.getDateTime(), request.getTimeZone());
            if (dateTime.isBefore(ZonedDateTime.now())) {
                EmailScheduleResponse response = new EmailScheduleResponse(false, DATETIME_BEFORE_ERROR);
                return ResponseEntity.badRequest().body(response);
            }

            JobDetail jobDetail = buildJobDetail(request);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);

            EmailScheduleResponse response = new EmailScheduleResponse();
            response.setSuccess(true);
            response.setJobId(jobDetail.getKey().getName());
            response.setJobGroup(jobDetail.getKey().getGroup());
            response.setMessage(EMAIL_SCHEDULE_SUCCESS);

            LOGGER.info(EMAIL_SCHEDULE_SUCCESS);
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            LOGGER.error("Error email scheduling -> ", ex);
            EmailScheduleResponse response = new EmailScheduleResponse(false, EMAIL_SCHEDULE_UNSUCCESSFUL);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private JobDetail buildJobDetail(EmailScheduleRequest request) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("email", request.getEmail());
        jobDataMap.put("subject", request.getSubject());
        jobDataMap.put("body", request.getBody());

        return JobBuilder
                .newJob(EmailJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Email Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();

    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder
                .newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "email-triggers")
                .withDescription("Send Email Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }

}
