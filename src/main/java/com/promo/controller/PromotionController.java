package com.promo.controller;

import com.promo.models.CartItems;
import com.promo.response.OrderResponse;
import com.promo.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/promotion")
@Validated
public class PromotionController {

    @Autowired
    PromoService promoService;

    @PostMapping("/getOrderTotal")
    public OrderResponse getOrderTotal(@RequestBody List<@Valid CartItems> cardItems) {
        return promoService.getOrderTotal(promoService.applyPromotions(cardItems));
    }
}
