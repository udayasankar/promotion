package com.promo.Exception;

public class CustomExceptionSchema {
    private String message;
    private String details;
    private String hint;
    private String nextActions;
    private String support;

    protected CustomExceptionSchema() {
    }

    public CustomExceptionSchema(
            String message, String details, String hint, String nextActions, String support) {
        this.message = message;
        this.details = details;
        this.hint = hint;
        this.nextActions = nextActions;
        this.support = support;
    }
}
