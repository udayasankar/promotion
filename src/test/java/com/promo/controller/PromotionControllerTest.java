package com.promo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.promo.models.CartItems;
import com.promo.models.OrderedItems;
import com.promo.response.OrderResponse;
import com.promo.service.PromoService;
import com.promo.service.RuleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PromotionController.class)
public class PromotionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PromoService promoService;

    @MockBean
    private RuleService ruleService;

    @Test
    void getPromoTestOne() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderResponse orderResponse = OrderResponse.builder().orderTotal(100).build();
        List<OrderedItems> orderedItemsList = new ArrayList<OrderedItems>();
        orderedItemsList.add(OrderedItems.builder().itemId("A").itemQuantity(1).
                itemPrice(new BigDecimal(50)).promotionId("").build());
        orderedItemsList.add(OrderedItems.builder().itemId("B").
                itemQuantity(1).itemPrice(new BigDecimal(30)).promotionId("").build());
        orderedItemsList.add(OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).promotionId("").build());
        List<CartItems> cartItemsList = new ArrayList<>();
        cartItemsList.add(CartItems.builder().itemId("A").itemQuantity(1).
                itemPrice(new BigDecimal(50)).build());
        cartItemsList.add(CartItems.builder().itemId("B").
                itemQuantity(1).itemPrice(new BigDecimal(30)).build());
        cartItemsList.add(CartItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).build());
        given(promoService.applyPromotions(cartItemsList)).willReturn(orderedItemsList);
        given(promoService.getOrderTotal(orderedItemsList)).willReturn(orderResponse);
        given(ruleService.ruleExecution(orderedItemsList)).willReturn(100);
        mockMvc.perform(
                post("/api/v1/promotion/getOrderTotal")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemsList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderTotal").isNumber())
                .andExpect(jsonPath("$.orderTotal").value("100"));
        verify(promoService).getOrderTotal(orderedItemsList);
    }

    @Test
    void getPromoTestTwo() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderResponse orderResponse = OrderResponse.builder().orderTotal(370).build();
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
        given(promoService.applyPromotions(cartItemsList)).willReturn(orderedItemsList);
        given(promoService.getOrderTotal(orderedItemsList)).willReturn(orderResponse);
        given(ruleService.ruleExecution(orderedItemsList)).willReturn(370);
        mockMvc.perform(
                post("/api/v1/promotion/getOrderTotal")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemsList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderTotal").isNumber())
                .andExpect(jsonPath("$.orderTotal").value("370"));
        verify(promoService).getOrderTotal(orderedItemsList);
    }

    @Test
    void getPromoTestThree() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<OrderedItems> orderedItemsList = new ArrayList<OrderedItems>();
        OrderResponse orderResponse = OrderResponse.builder().orderTotal(280).build();
        orderedItemsList.add(OrderedItems.builder().itemId("A").itemQuantity(3).
                itemPrice(new BigDecimal(50)).promotionId("A").build());
        orderedItemsList.add(OrderedItems.builder().itemId("B").
                itemQuantity(5).itemPrice(new BigDecimal(30)).promotionId("B").build());
        orderedItemsList.add(OrderedItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(0)).promotionId("CD").build());
        orderedItemsList.add(OrderedItems.builder().itemId("D").
                itemQuantity(1).itemPrice(new BigDecimal(30)).promotionId("CD").build());
        List<CartItems> cartItemsList = new ArrayList<>();
        cartItemsList.add(CartItems.builder().itemId("A").itemQuantity(3).
                itemPrice(new BigDecimal(50)).build());
        cartItemsList.add(CartItems.builder().itemId("B").
                itemQuantity(5).itemPrice(new BigDecimal(30)).build());
        cartItemsList.add(CartItems.builder().itemId("C").
                itemQuantity(1).itemPrice(new BigDecimal(20)).build());
        cartItemsList.add(CartItems.builder().itemId("D").
                itemQuantity(1).itemPrice(new BigDecimal(15)).build());
        given(promoService.applyPromotions(cartItemsList)).willReturn(orderedItemsList);
        given(promoService.getOrderTotal(orderedItemsList)).willReturn(orderResponse);
        given(ruleService.ruleExecution(orderedItemsList)).willReturn(280);

        mockMvc.perform(
                post("/api/v1/promotion/getOrderTotal")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartItemsList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderTotal").isNumber())
                .andExpect(jsonPath("$.orderTotal").value("280"));
        verify(promoService).getOrderTotal(orderedItemsList);
    }


}
