package com.budgetmanager.ticket.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TicketDto {
    private final String subject;
    private final String message;

    @JsonCreator
    public TicketDto(@JsonProperty("subject") String subject,
                     @JsonProperty("message") String message) {
        this.subject = subject;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

}
