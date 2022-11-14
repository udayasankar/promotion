package com.promo.service;

import com.promo.models.ActivePromotions;
import com.promo.models.CartItems;
import com.promo.models.OrderedItems;
import com.promo.response.OrderResponse;

import java.util.List;
import java.util.Optional;

public interface PromoService {
    OrderResponse getOrderTotal(List<OrderedItems> orderedItems);

    List<OrderedItems> applyPromotions(List<CartItems> cartItemsList);

    boolean nItemsPromotion(Optional<CartItems> cartItems, List<OrderedItems> orderedItemsList, ActivePromotions activePromotions);

    boolean fixedPricePromotion(Optional<CartItems> cartItems, List<OrderedItems> orderedItemsList, ActivePromotions activePromotions);

    boolean noPromotionItems(Optional<CartItems> cartItems, List<OrderedItems> orderedItemsList, ActivePromotions activePromotions);
}
