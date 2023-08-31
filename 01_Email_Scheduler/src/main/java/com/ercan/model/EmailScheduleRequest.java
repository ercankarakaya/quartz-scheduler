package com.ercan.model;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
public class EmailScheduleRequest {

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String body;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    private ZoneId timeZone;
}
