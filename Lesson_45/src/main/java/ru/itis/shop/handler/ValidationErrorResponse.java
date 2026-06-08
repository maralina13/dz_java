package ru.itis.shop.handler;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationErrorResponse {

    private String message;
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ValidationErrorResponse() {
    }

    public ValidationErrorResponse(String message, Map<String, String> errors, LocalDateTime timestamp) {
        this.message = message;
        this.errors = errors;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
