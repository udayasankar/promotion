package com.promo.service;

import com.promo.models.ActivePromotions;
import com.promo.models.CartItems;
import com.promo.models.OrderedItems;
import com.promo.response.OrderResponse;
import com.promo.servicesimpl.RuleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class PromoServiceImplTest {

    @Autowired
    RuleServiceImpl ruleService;

    @Autowired
    PromoService promoService;

    @Test
    void applyPromotionTest() {
        List<CartItems> cartItemsList = new ArrayList<>();
        cartItemsList.add(CartItems.builder().itemId("A").itemQuantity(1).
                itemPrice(new BigDecimal(50)).build());
        cartItemsList.add(CartItems.builder().itemId("B").
                itemQuantity(1).itemPrice(new BigDecimal(30)).build());
        cartItemsList.add(CartItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).build());
        List<OrderedItems> orderedItemsList = promoService.applyPromotions(cartItemsList);
        assertEquals(3, orderedItemsList.size());
    }

    @Test
    void nItemsPromotionTest() {
        List<OrderedItems> orderedItemsList = new ArrayList<>();
        CartItems cartItems = CartItems.builder().itemId("A").itemQuantity(1).
                itemPrice(new BigDecimal(50)).build();
        ActivePromotions activePromotions = ActivePromotions.builder().itemId("A").promoItemId("A").promotionNo(3).
                promotionItemPrice(new BigDecimal(130)).build();
        assertFalse(promoService.nItemsPromotion(Optional.ofNullable(cartItems), orderedItemsList, activePromotions));
    }

    @Test
    void nItemsPromotionQtyTest() {
        List<OrderedItems> orderedItemsList = new ArrayList<>();
        CartItems cartItems = CartItems.builder().itemId("A").itemQuantity(5).
                itemPrice(new BigDecimal(50)).build();
        ActivePromotions activePromotions = ActivePromotions.builder().itemId("A").promoItemId("A").promotionNo(3).
                promotionItemPrice(new BigDecimal(130)).build();
        assertTrue(promoService.nItemsPromotion(Optional.ofNullable(cartItems), orderedItemsList, activePromotions));
    }

    @Test
    void fixedPricePromotionTest() {
        List<OrderedItems> orderedItemsList = new ArrayList<>();
        List<CartItems> cartItemsList = new ArrayList<>();
        cartItemsList.add(CartItems.builder().itemId("A").itemQuantity(5).
                itemPrice(new BigDecimal(50)).build());
        cartItemsList.add(CartItems.builder().itemId("B").
                itemQuantity(5).itemPrice(new BigDecimal(30)).build());
        cartItemsList.add(CartItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).build());
        CartItems cartItems = CartItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).build();
        Map<String, CartItems> cartItemsMap = mock(Map.class);
        cartItemsMap.put("D", cartItems);
        ReflectionTestUtils.setField(promoService, "cartItemsMap", cartItemsMap);
        ActivePromotions activePromotions = ActivePromotions.builder().itemId("C").promoItemId("CD").
                promoItemIds(Arrays.asList("C", "D")).promotionNo(1).promotionItemPrice(new BigDecimal(0)).build();
        assertFalse(promoService.fixedPricePromotion(Optional.ofNullable(cartItems), orderedItemsList, activePromotions));
    }

    @Test
    void noPromotionItemsTest() {
        List<OrderedItems> orderedItemsList = new ArrayList<OrderedItems>();
        orderedItemsList.add(OrderedItems.builder().itemId("A").itemQuantity(5).
                itemPrice(new BigDecimal(50)).promotionId("A").build());
        orderedItemsList.add(OrderedItems.builder().itemId("B").
                itemQuantity(5).itemPrice(new BigDecimal(30)).promotionId("B").build());
        orderedItemsList.add(OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).promotionId("").build());
        List<CartItems> cartItemsList = new ArrayList<>();
        cartItemsList.add(CartItems.builder().itemId("A").itemQuantity(5).
                itemPrice(new BigDecimal(50)).build());
        cartItemsList.add(CartItems.builder().itemId("B").
                itemQuantity(5).itemPrice(new BigDecimal(30)).build());
        cartItemsList.add(CartItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).build());
        Map<String, CartItems> cartItemsMap = mock(Map.class);
        CartItems cartItems = CartItems.builder().itemId("A").itemQuantity(5).
                itemPrice(new BigDecimal(50)).build();
        ActivePromotions activePromotions = ActivePromotions.builder().itemId("A").promoItemId("A").promotionNo(3).
                promotionItemPrice(new BigDecimal(130)).build();
        cartItemsMap.put("D", cartItems);
        ReflectionTestUtils.setField(promoService, "cartItemsMap", cartItemsMap);
        assertFalse(promoService.noPromotionItems(Optional.ofNullable(cartItems), orderedItemsList, activePromotions));
    }

    @Test
    void promoGetOrderTotalTestOne() {
        List<OrderedItems> orderedItemsList = new ArrayList<OrderedItems>();
        orderedItemsList.add(OrderedItems.builder().itemId("A").itemQuantity(1).
                itemPrice(new BigDecimal(50)).promotionId("").build());
        orderedItemsList.add(OrderedItems.builder().itemId("B").
                itemQuantity(1).itemPrice(new BigDecimal(30)).promotionId("").build());
        orderedItemsList.add(OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).promotionId("").build());
        OrderResponse orderResponse = promoService.getOrderTotal(orderedItemsList);
        assertEquals(java.util.Optional.of(100).get(), orderResponse.getOrderTotal());
    }

    @Test
    void promoGetOrderTotalTestTwo() {
        List<OrderedItems> orderedItemsList = new ArrayList<OrderedItems>();
        orderedItemsList.add(OrderedItems.builder().itemId("A").itemQuantity(5).
                itemPrice(new BigDecimal(50)).promotionId("A").build());
        orderedItemsList.add(OrderedItems.builder().itemId("B").
                itemQuantity(5).itemPrice(new BigDecimal(30)).promotionId("B").build());
        orderedItemsList.add(OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).promotionId("").build());
        OrderResponse orderResponse = promoService.getOrderTotal(orderedItemsList);
        assertEquals(java.util.Optional.of(370).get(), orderResponse.getOrderTotal());
    }

    @Test
    void promoGetOrderTotalTestThree() {
        List<OrderedItems> orderedItemsList = new ArrayList<OrderedItems>();
        orderedItemsList.add(OrderedItems.builder().itemId("A").itemQuantity(3).
                itemPrice(new BigDecimal(50)).promotionId("A").build());
        orderedItemsList.add(OrderedItems.builder().itemId("B").
                itemQuantity(5).itemPrice(new BigDecimal(30)).promotionId("B").build());
        orderedItemsList.add(OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(0)).promotionId("CD").build());
        orderedItemsList.add(OrderedItems.builder().itemId("D").
                itemQuantity(1).itemPrice(new BigDecimal(30)).promotionId("CD").build());
        OrderResponse orderResponse = promoService.getOrderTotal(orderedItemsList);
        assertEquals(java.util.Optional.of(280).get(), orderResponse.getOrderTotal());
    }
}
