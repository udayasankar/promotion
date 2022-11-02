package com.promo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;

@Builder
@AllArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

}
