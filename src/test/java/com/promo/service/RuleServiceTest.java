package com.promo.service;

import com.promo.models.ActivePromotions;
import com.promo.models.OrderedItems;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RuleServiceTest {

    @Autowired
    private RuleService ruleService;

    @Test
    void ruleExecutionTest() {
        List<OrderedItems> orderedItemsList = new ArrayList<OrderedItems>();
        orderedItemsList.add(OrderedItems.builder().itemId("A").itemQuantity(3).
                itemPrice(new BigDecimal(50)).promotionId("A").build());
        orderedItemsList.add(OrderedItems.builder().itemId("B").
                itemQuantity(5).itemPrice(new BigDecimal(30)).promotionId("B").build());
        orderedItemsList.add(OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(0)).promotionId("CD").build());
        orderedItemsList.add(OrderedItems.builder().itemId("D").
                itemQuantity(1).itemPrice(new BigDecimal(30)).promotionId("CD").build());
        assertEquals(java.util.Optional.of(280).get(), ruleService.ruleExecution(orderedItemsList));
    }

    @Test
    void nItemsPromotionRuleTest() {
        OrderedItems orderedItems = OrderedItems.builder().itemId("A").itemQuantity(3).
                itemPrice(new BigDecimal(50)).promotionId("A").build();
        ActivePromotions activePromotions = ActivePromotions.builder().itemId("A").promoItemId("A").promotionNo(3).
                promotionItemPrice(new BigDecimal(130)).build();
        assertTrue(ruleService.nItemsPromotionRule(orderedItems, activePromotions));
    }

    @Test
    void fixedPricePromotionRule() {
        OrderedItems orderedItems = OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(0)).promotionId("CD").build();
        ActivePromotions activePromotions = ActivePromotions.builder().itemId("C").promoItemId("CD").
                promoItemIds(Arrays.asList("C", "D")).promotionNo(1).promotionItemPrice(new BigDecimal(0)).build();
        assertTrue(ruleService.fixedPricePromotionRule(orderedItems, activePromotions));
    }

    @Test
    void noPromotionItemsRulesTest() {
        OrderedItems orderedItems = OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(0)).promotionId("CD").build();
        ActivePromotions activePromotions = ActivePromotions.builder().itemId("C").promoItemId("CD").
                promoItemIds(Arrays.asList("C", "D")).promotionNo(1).promotionItemPrice(new BigDecimal(0)).build();
        assertTrue(ruleService.noPromotionItemsRules(orderedItems, activePromotions));
    }

    @Test
    void ruleExecutionTestOne() {
        List<OrderedItems> orderedItemsList = new ArrayList<OrderedItems>();
        orderedItemsList.add(OrderedItems.builder().itemId("A").itemQuantity(1).
                itemPrice(new BigDecimal(50)).promotionId("").build());
        orderedItemsList.add(OrderedItems.builder().itemId("B").
                itemQuantity(1).itemPrice(new BigDecimal(30)).promotionId("").build());
        orderedItemsList.add(OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).promotionId("").build());
        Integer totalValue = ruleService.ruleExecution(orderedItemsList);
        assertEquals(java.util.Optional.of(100).get(), totalValue);
    }

    @Test
    void ruleExecutionTestTwo() {
        List<OrderedItems> orderedItemsList = new ArrayList<OrderedItems>();
        orderedItemsList.add(OrderedItems.builder().itemId("A").itemQuantity(5).
                itemPrice(new BigDecimal(50)).promotionId("A").build());
        orderedItemsList.add(OrderedItems.builder().itemId("B").
                itemQuantity(5).itemPrice(new BigDecimal(30)).promotionId("B").build());
        orderedItemsList.add(OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).promotionId("").build());
        Integer totalValue = ruleService.ruleExecution(orderedItemsList);
        assertEquals(java.util.Optional.of(370).get(), totalValue);
    }

    @Test
    void ruleExecutionTestThree() {
        List<OrderedItems> orderedItemsList = new ArrayList<OrderedItems>();
        orderedItemsList.add(OrderedItems.builder().itemId("A").itemQuantity(3).
                itemPrice(new BigDecimal(50)).promotionId("A").build());
        orderedItemsList.add(OrderedItems.builder().itemId("B").
                itemQuantity(5).itemPrice(new BigDecimal(30)).promotionId("B").build());
        orderedItemsList.add(OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(0)).promotionId("CD").build());
        orderedItemsList.add(OrderedItems.builder().itemId("D").
                itemQuantity(1).itemPrice(new BigDecimal(30)).promotionId("CD").build());
        Integer totalValue = ruleService.ruleExecution(orderedItemsList);
        assertEquals(java.util.Optional.of(280).get(), totalValue);
    }
}
