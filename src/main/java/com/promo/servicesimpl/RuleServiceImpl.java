package com.promo.servicesimpl;

import com.promo.dao.ActivePromotionsDao;
import com.promo.dao.ItemInfoDao;
import com.promo.models.ActivePromotions;
import com.promo.models.ItemInfo;
import com.promo.models.OrderedItems;
import com.promo.serviceimpl.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    ActivePromotionsDao activePromotionsDao;

    @Autowired
    ItemInfoDao itemInfoDao;

    Integer totalPrice = 0;

    public Integer ruleExecution(List<OrderedItems> orderedItemsList) {
          ActivePromotions activePromotions = null;
            totalPrice=0;
        for (OrderedItems orderedItems : orderedItemsList) {
            boolean returnVal = nItemsPromotionRule(orderedItems, activePromotions);
            if(!returnVal) {
                returnVal = fixedPricePromotionRule(orderedItems, activePromotions);
            }
            if(!returnVal)
            {
                returnVal = noPromotionItemsRules(orderedItems, activePromotions);
            }
        }
        return totalPrice;
    }

        @Override
        public boolean nItemsPromotionRule(OrderedItems orderedItems, ActivePromotions activePromotions) {
            int partialQty = 0;
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
                return true;
            }
            return false;
        }

        @Override
        public boolean fixedPricePromotionRule(OrderedItems orderedItems, ActivePromotions activePromotions) {
            if(!orderedItems.getPromotionId().equals(""))
            {
                totalPrice = totalPrice + orderedItems.getItemPrice().intValue();
                return true;
            }
            return false;
        }

        @Override
        public boolean noPromotionItemsRules(OrderedItems orderedItems, ActivePromotions activePromotions) {
              ItemInfo itemInfo = itemInfoDao.getItemInfoMap().get(orderedItems.getItemId());
              totalPrice = totalPrice + (
                      orderedItems.getItemQuantity() * itemInfo.getItemPrice().intValue());
              return true;
        }
}
