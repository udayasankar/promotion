package com.promo.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class ItemInfo {
    private String itemId;
    private BigDecimal itemPrice;
}
