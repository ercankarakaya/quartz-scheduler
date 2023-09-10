package com.ercan.controller;

import com.ercan.dto.ScheduleRequest;
import com.ercan.dto.ScheduleResponse;
import com.ercan.service.SchedulerService;
import org.quartz.Job;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> schedule(@RequestBody ScheduleRequest request) throws ClassNotFoundException, SchedulerException {
        Class<?> jobClass = Class.forName(request.getJobClass());
        schedulerService.scheduleJob((Class<? extends Job>) jobClass, request.getJobName(), request.getJobGroup(), request.getCronExpression());
        return ResponseEntity.ok(new ScheduleResponse("Scheduled successfull. Job name :" + request.getJobName()));
    }

}
