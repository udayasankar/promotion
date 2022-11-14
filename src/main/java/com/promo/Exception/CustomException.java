package com.promo.Exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private String message;
    private String details;
    private String hint;
    private String nextActions;
    private String support;

    protected CustomException() {
    }

    public CustomException(
            String message, String details, String hint, String nextActions, String support) {
        this.message = message;
        this.details = details;
        this.hint = hint;
        this.nextActions = nextActions;
        this.support = support;
    }
}
