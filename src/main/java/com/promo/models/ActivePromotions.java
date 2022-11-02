package com.promo.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class ActivePromotions {

    private String promoItemId;
    private String itemId;
    private List<String> promoItemIds;
    private Integer promoItemNo;
    private Integer promotionNo;
    private BigDecimal promotionItemPrice;


}
