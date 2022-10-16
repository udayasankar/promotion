package com.promo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.promo.models.OrderedItems;
import com.promo.response.OrderResponse;
import com.promo.service.PromoService;
import com.promo.service.RuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PromotionController.class)
@ExtendWith(SpringExtension.class)
public class PromotionControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        given(promoService.getOrderTotal(orderedItemsList)).willReturn(orderResponse);
        given(ruleService.ruleExecution(orderedItemsList)).willReturn(100);
        System.out.println(objectMapper.writeValueAsString(orderedItemsList));
        mockMvc.perform(
                post("/api/v1/promotion/getOrderTotal")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderedItemsList)))
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
        Integer totalValue = ruleService.ruleExecution(orderedItemsList);
        given(promoService.getOrderTotal(orderedItemsList)).willReturn(orderResponse);
        given(ruleService.ruleExecution(orderedItemsList)).willReturn(370);

        mockMvc.perform(
                post("/api/v1/promotion/getOrderTotal")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderedItemsList)))
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
        orderedItemsList.add(OrderedItems.builder().itemId("CD").
                itemQuantity(1).itemPrice(new BigDecimal(30)).promotionId("CD").build());
        Integer totalValue = ruleService.ruleExecution(orderedItemsList);
        given(promoService.getOrderTotal(orderedItemsList)).willReturn(orderResponse);
        given(ruleService.ruleExecution(orderedItemsList)).willReturn(280);

        mockMvc.perform(
                post("/api/v1/promotion/getOrderTotal")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderedItemsList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderTotal").isNumber())
                .andExpect(jsonPath("$.orderTotal").value("280"));
        verify(promoService).getOrderTotal(orderedItemsList);
    }


}
