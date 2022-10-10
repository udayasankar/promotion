package com.promo.service;

import com.promo.models.OrderedItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoService {

    @Autowired
    RuleService ruleService;

    public Integer getOrderTotal(List<OrderedItems> orderedItems) {

        return ruleService.ruleExecution(orderedItems);
    }
}
