package com.promo.controller;

import com.promo.entities.Items;
import com.promo.models.CartItems;
import com.promo.repository.ItemRepository;
import com.promo.response.OrderResponse;
import com.promo.serviceimpl.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/promotion")
@Validated
public class PromotionController {

    @Autowired
    PromoService promoService;

    @Autowired
    ItemRepository itemRepository;

    @PostMapping("/getOrderTotal")
    public OrderResponse getOrderTotal(@RequestBody List<@Valid CartItems> cardItems) {
        return promoService.getOrderTotal(promoService.applyPromotions(cardItems));
    }

    @GetMapping("/test")
    public List<Items> getUsers() {
        List<Items> itemsList = itemRepository.findAll();
        System.out.println(itemsList);
                return itemsList;
    }
}
