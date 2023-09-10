package com.ercan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {

    private String jobClass;
    private String jobName;
    private String jobGroup;
    private String cronExpression;
}
