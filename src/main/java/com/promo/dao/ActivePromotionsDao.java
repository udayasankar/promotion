package com.promo.dao;

import com.promo.models.ActivePromotions;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ActivePromotionsDao {
    private static Map<String, ActivePromotions> activePromoMap = new HashMap<>();

    static {
        activePromoMap.put("A", ActivePromotions.builder().promoItemId("A").promotionNo(3).promotionItemPrice(new BigDecimal(130)).build());
        activePromoMap.put("B", ActivePromotions.builder().promoItemId("B").promotionNo(2).promotionItemPrice(new BigDecimal(45)).build());
        activePromoMap.put("CD", ActivePromotions.builder().promoItemId("CD").promotionNo(1).promotionItemPrice(new BigDecimal(30)).build());
    }

    public Map<String, ActivePromotions> getActivePromotion() {
        return activePromoMap;
    }
}
