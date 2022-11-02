package com.promo.dao;

import com.promo.entities.ActivePromotion;
import com.promo.models.ActivePromotions;
import com.promo.models.ItemInfo;
import com.promo.repository.ActivePromotionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivePromotionsDao {
    private static Map<String, ActivePromotions> activePromoMap = new HashMap<>();

    @Autowired
    ActivePromotionsRepository activePromotionsRepository;

    @PostConstruct
    void fillActivePromotions()
    {
        List<ActivePromotion> activePromotionsList = activePromotionsRepository.findAll();
        for(ActivePromotion activePromotion: activePromotionsList) {
            String[] itemIds;
            if(!ObjectUtils.isEmpty(activePromotion.getGetPromoItemIds()))
            {
                itemIds = activePromotion.getGetPromoItemIds().split(",");
                List<String> itemIdsList = Arrays.stream(itemIds).sequential().collect(Collectors.toList());
                activePromoMap.put(activePromotion.getItemId(), ActivePromotions.builder().itemId(activePromotion.getItemId())
                        .promoItemId(activePromotion.getPromoItemId()).promoItemIds(itemIdsList).promotionNo(activePromotion.getPromoNo())
                        .promotionItemPrice(activePromotion.getPromoItemPrice()).build());
            }
            else
            {
                activePromoMap.put(activePromotion.getItemId(), ActivePromotions.builder().itemId(activePromotion.getItemId())
                        .promoItemId(activePromotion.getPromoItemId()).promotionNo(activePromotion.getPromoNo())
                        .promotionItemPrice(activePromotion.getPromoItemPrice()).build());
            }

        }
        System.out.println(activePromoMap);
    }

    /*static {
        activePromoMap.put("A", ActivePromotions.builder().itemId("A").promoItemId("A").promotionNo(3).promotionItemPrice(new BigDecimal(130)).build());
        activePromoMap.put("B", ActivePromotions.builder().itemId("B").promoItemId("B").promotionNo(2).promotionItemPrice(new BigDecimal(45)).build());
        activePromoMap.put("C", ActivePromotions.builder().itemId("C").promoItemId("CD").promoItemIds(Arrays.asList("C","D")).promotionNo(1).promotionItemPrice(new BigDecimal(0)).build());
        activePromoMap.put("D", ActivePromotions.builder().itemId("D").promoItemId("CD").promoItemIds(Arrays.asList("C","D")).promotionNo(1).promotionItemPrice(new BigDecimal(30)).build());
    }*/

    public Map<String, ActivePromotions> getActivePromotion() {
        return activePromoMap;
    }
}
