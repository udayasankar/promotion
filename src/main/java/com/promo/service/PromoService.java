package com.promo.service;

import com.promo.models.OrderedItems;
import com.promo.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoService {

    @Autowired
    RuleService ruleService;

    public OrderResponse getOrderTotal(List<OrderedItems> orderedItems) {

        return OrderResponse.builder().
                orderTotal(ruleService.ruleExecution(orderedItems)).build();
    }
}
