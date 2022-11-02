package com.promo.serviceimpl;

import com.promo.models.ActivePromotions;
import com.promo.models.OrderedItems;

import java.util.List;

public interface RuleService {
    public Integer ruleExecution(List<OrderedItems> orderedItemsList);
    boolean nItemsPromotionRule(OrderedItems orderedItems, ActivePromotions activePromotions);
    boolean fixedPricePromotionRule(OrderedItems orderedItems,ActivePromotions activePromotions);
    boolean noPromotionItemsRules(OrderedItems orderedItems, ActivePromotions activePromotions);

}
