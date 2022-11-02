package com.promo.serviceimpl;

import com.promo.models.OrderedItems;
import com.promo.servicesimpl.RuleServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RuleServiceTest {

    @Autowired
    private RuleServiceImpl ruleService;

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
        orderedItemsList.add(OrderedItems.builder().itemId("CD").
                itemQuantity(1).itemPrice(new BigDecimal(30)).promotionId("CD").build());
        Integer totalValue = ruleService.ruleExecution(orderedItemsList);
        assertEquals(java.util.Optional.of(280).get(), totalValue);
    }
}
