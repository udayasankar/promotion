package com.promo.controller;

import com.promo.models.OrderedItems;
import com.promo.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/promotion")
public class PromotionController {

    @Autowired
    PromoService promoService;

    @PostMapping("/getOrderTotal")
    public Integer getOrderTotal(@RequestBody List<OrderedItems> orderedItems) {
        return promoService.getOrderTotal(orderedItems);
    }
}
