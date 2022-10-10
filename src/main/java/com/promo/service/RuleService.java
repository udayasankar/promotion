package com.promo.service;

import com.promo.dao.ActivePromotionsDao;
import com.promo.dao.ItemInfoDao;
import com.promo.models.ActivePromotions;
import com.promo.models.ItemInfo;
import com.promo.models.OrderedItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {

    @Autowired
    ActivePromotionsDao activePromotionsDao;

    @Autowired
    ItemInfoDao itemInfoDao;

    public Integer ruleExecution(List<OrderedItems> orderedItemsList) {
        Integer totalPrice = 0;
        int partialQty = 0;
        ActivePromotions activePromotions = null;
        for (OrderedItems orderedItems : orderedItemsList) {

            if (activePromotionsDao.getActivePromotion().containsKey(orderedItems.getPromotionId())) {
                activePromotions = activePromotionsDao.getActivePromotion()
                        .get(orderedItems.getPromotionId());
                partialQty = orderedItems.getItemQuantity() - activePromotions.getPromotionNo();
                totalPrice = totalPrice + activePromotions.getPromotionItemPrice().intValue();
                if (partialQty > 0 && partialQty >= activePromotions.getPromotionNo()) {
                    while (partialQty > activePromotions.getPromotionNo()) {
                        partialQty = partialQty - activePromotions.getPromotionNo();
                        totalPrice = totalPrice + activePromotions.getPromotionItemPrice().intValue();
                    }
                }
                if (partialQty > 0 && partialQty <= activePromotions.getPromotionNo()) {
                    while (partialQty > 0) {
                        totalPrice = totalPrice + partialQty * orderedItems.getItemPrice().intValue();
                        partialQty = partialQty - activePromotions.getPromotionNo();
                    }
                }
            } else {
                ItemInfo itemInfo = itemInfoDao.getItemInfoMap().get(orderedItems.getItemId());
                totalPrice = totalPrice + (
                        orderedItems.getItemQuantity() * itemInfo.getItemPrice().intValue());
            }
        }
        return totalPrice;
    }
}
