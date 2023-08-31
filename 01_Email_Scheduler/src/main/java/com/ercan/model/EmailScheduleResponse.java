package com.ercan.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailScheduleResponse {

    private boolean success;
    private String jobId;
    private String jobGroup;
    private String message;


    public EmailScheduleResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
