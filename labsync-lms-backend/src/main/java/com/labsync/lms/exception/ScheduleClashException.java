package com.labsync.lms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

/**
 * ScheduleClashException — thrown when a schedule entry would create a booking conflict.
 * Maps to HTTP 409 Conflict.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ScheduleClashException extends RuntimeException {

    private final List<String> clashMessages;

    public ScheduleClashException(List<String> clashMessages) {
        super("Schedule clash detected: " + String.join("; ", clashMessages));
        this.clashMessages = clashMessages;
    }

    public List<String> getClashMessages() {
        return clashMessages;
    }
}
