package com.promo.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class OrderResponse {

    @NotNull(message = "OrderTotal cannot be null")
    private Integer orderTotal;
}
