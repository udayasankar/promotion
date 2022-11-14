package com.promo.response;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class OrderResponse {

    @NotNull(message = "OrderTotal cannot be null")
    private Integer orderTotal;
}
