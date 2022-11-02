package com.promo.models;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
public class CartItems {

    @NotBlank(message = "ItemId is mandatory")
    private String itemId;

    @NotNull(message = "ItemQuantity cannot be null")
    private Integer itemQuantity;

    @NotNull(message = "ItemPrice cannot be null")
    private BigDecimal itemPrice;
}
