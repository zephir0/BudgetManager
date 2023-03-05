package com.budgetmanager.chat.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ChatMessageDto {
    private final String message;
    private final LocalDateTime createdAt;
    private final boolean isAdmin;

    @JsonCreator
    public ChatMessageDto(@JsonProperty("message") String message,
                          @JsonProperty("createdAt") LocalDateTime createdAt,
                          @JsonProperty("isAdmin") boolean isAdmin) {
        this.message = message;
        this.createdAt = createdAt;
        this.isAdmin = isAdmin;
    }


    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
