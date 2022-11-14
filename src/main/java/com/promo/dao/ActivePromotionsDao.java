package com.promo.dao;

import com.promo.entities.ActivePromotion;
import com.promo.models.ActivePromotions;
import com.promo.repository.ActivePromotionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivePromotionsDao {
    private static Map<String, ActivePromotions> activePromoMap = new HashMap<>();

    @Autowired
    ActivePromotionsRepository activePromotionsRepository;

    @PostConstruct
    int fillActivePromotions() {
        log.info("Loading the promotions and saving in cache");
        List<ActivePromotion> activePromotionsList = activePromotionsRepository.findAll();
        for (ActivePromotion activePromotion : activePromotionsList) {
            String[] itemIds;
            if (!ObjectUtils.isEmpty(activePromotion.getGetPromoItemIds())) {
                itemIds = activePromotion.getGetPromoItemIds().split(",");
                List<String> itemIdsList = Arrays.stream(itemIds).sequential().collect(Collectors.toList());
                activePromoMap.put(activePromotion.getItemId(), ActivePromotions.builder().itemId(activePromotion.getItemId())
                        .promoItemId(activePromotion.getPromoItemId()).promoItemIds(itemIdsList).promotionNo(activePromotion.getPromoNo())
                        .promotionItemPrice(activePromotion.getPromoItemPrice()).build());
            } else {
                activePromoMap.put(activePromotion.getItemId(), ActivePromotions.builder().itemId(activePromotion.getItemId())
                        .promoItemId(activePromotion.getPromoItemId()).promotionNo(activePromotion.getPromoNo())
                        .promotionItemPrice(activePromotion.getPromoItemPrice()).build());
            }

        }
        return activePromoMap.size();
    }

    public Map<String, ActivePromotions> getActivePromotion() {
        return activePromoMap;
    }
}
