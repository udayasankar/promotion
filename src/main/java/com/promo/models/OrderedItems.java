package com.promo.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class OrderedItems {
    private String itemId;
    private Integer itemQuantity;
    private BigDecimal itemPrice;
    private String promotionId;
}
