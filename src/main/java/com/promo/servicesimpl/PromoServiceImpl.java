package com.promo.servicesimpl;

import com.promo.dao.ActivePromotionsDao;
import com.promo.models.ActivePromotions;
import com.promo.models.CartItems;
import com.promo.models.OrderedItems;
import com.promo.response.OrderResponse;
import com.promo.service.PromoService;
import com.promo.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PromoServiceImpl implements PromoService {

    @Autowired
    RuleService ruleService;

    @Autowired
    ActivePromotionsDao activePromotionsDao;

    Map<String, CartItems> cartItemsMap;

    public OrderResponse getOrderTotal(List<OrderedItems> orderedItems) {
        log.info("In PromoServiceImpl getOrderTotal");
        return OrderResponse.builder().
                orderTotal(ruleService.ruleExecution(orderedItems)).build();
    }

    @Override
    public List<OrderedItems> applyPromotions(List<CartItems> cartItemsList) {
        log.info("In PromoServiceImpl applyPromotions");
        List<OrderedItems> orderedItemsList = new ArrayList<>();
        cartItemsMap = cartItemsList.stream()
                .collect(Collectors.toMap(CartItems::getItemId, Function.identity()));
        for (Map.Entry<String, ActivePromotions> activePromotionsEntry : activePromotionsDao.getActivePromotion().entrySet()) {
            Optional<CartItems> cartItems = Optional.ofNullable(cartItemsMap.get(activePromotionsEntry.getKey()));
            boolean returnVal = nItemsPromotion(cartItems, orderedItemsList, activePromotionsEntry.getValue());
            if (!returnVal) {
                returnVal = fixedPricePromotion(cartItems, orderedItemsList, activePromotionsEntry.getValue());
            }
            if (!returnVal) {
                returnVal = noPromotionItems(cartItems, orderedItemsList, activePromotionsEntry.getValue());
            }

        }
        return orderedItemsList;
    }

    @Override
    public boolean nItemsPromotion(Optional<CartItems> cartItems, List<OrderedItems> orderedItemsList, ActivePromotions activePromotions) {
        if (cartItems.isPresent() && cartItems.get().getItemId().equals(activePromotions.getPromoItemId()) &&
                cartItems.get().getItemQuantity() >= activePromotions.getPromotionNo()) {
            log.info("In PromoServiceImpl executing nItemsPromotion for : {} {}", cartItems.get().getItemId(), activePromotions.getPromoItemIds());
            orderedItemsList.add(OrderedItems.builder().itemId(cartItems.get().getItemId())
                    .itemQuantity(cartItems.get().getItemQuantity())
                    .itemPrice(cartItems.get().getItemPrice())
                    .promotionId(activePromotions.getPromoItemId()).build());
            return true;
        }
        return false;
    }

    @Override
    public boolean fixedPricePromotion(Optional<CartItems> cartItems, List<OrderedItems> orderedItemsList, ActivePromotions activePromotions) {
        if (!ObjectUtils.isEmpty(activePromotions.getPromoItemIds())) {
            if (cartItems.isPresent())
                log.info("In PromoServiceImpl executing fixedPricePromotion for : {} {}", cartItems.get().getItemId(),
                        activePromotions.getPromoItemIds());
            String searchVal = activePromotions.getPromoItemIds().get(activePromotions.getPromoItemIds().size() - 1);
            Optional<List<String>> promoItemsList = Optional.ofNullable(activePromotions.getPromoItemIds().stream().
                    filter(ap -> ap.equals(searchVal) && cartItemsMap.containsKey(searchVal))
                    .collect(Collectors.toList()));
            if (cartItems.isPresent() && cartItems.get().getItemId().equals(activePromotions.getItemId()) &&
                    cartItems.get().getItemQuantity() == activePromotions.getPromotionNo() &&
                    promoItemsList.get().size() > 0) {
                if (cartItems.get().getItemId().equals(promoItemsList.get().get(0))) {
                    orderedItemsList.add(OrderedItems.builder().itemId(cartItems.get().getItemId())
                            .itemQuantity(cartItems.get().getItemQuantity())
                            .itemPrice(activePromotions.getPromotionItemPrice())
                            .promotionId(activePromotions.getPromoItemId()).build());
                } else {
                    orderedItemsList.add(OrderedItems.builder().itemId(cartItems.get().getItemId())
                            .itemQuantity(cartItems.get().getItemQuantity())
                            .itemPrice(activePromotions.getPromotionItemPrice())
                            .promotionId(activePromotions.getPromoItemId()).build());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean noPromotionItems(Optional<CartItems> cartItems, List<OrderedItems> orderedItemsList, ActivePromotions activePromotions) {
        if (orderedItemsList.size() < cartItemsMap.size()) {
            log.info("In PromoServiceImpl executing noPromotionItems for : {} {}", cartItems.get().getItemId(), activePromotions.getPromoItemIds());
            orderedItemsList.add(OrderedItems.builder().itemId(cartItems.get().getItemId())
                    .itemQuantity(cartItems.get().getItemQuantity())
                    .itemPrice(cartItems.get().getItemPrice())
                    .promotionId("").build());
            return true;
        }
        return false;
    }


}
