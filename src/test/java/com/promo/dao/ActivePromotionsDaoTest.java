package com.promo.dao;

import com.promo.entities.ActivePromotion;
import com.promo.models.ActivePromotions;
import com.promo.repository.ActivePromotionsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class ActivePromotionsDaoTest {

    @Mock
    ActivePromotionsRepository activePromotionsRepository;

    @Autowired
    ActivePromotionsDao activePromotionsDao;

    @Test
    void fillActivePromotionsTest() {
        Map<String, ActivePromotions> activePromoMap = mock(Map.class);
        ReflectionTestUtils.setField(activePromotionsDao, "activePromoMap", activePromoMap);
        List<ActivePromotion> activePromotionsList = new ArrayList<>();
        activePromotionsList.add(ActivePromotion.builder().itemId("A").promoItemId("A").promoNo(3).promoItemPrice(new BigDecimal(130)).build());
        activePromotionsList.add(ActivePromotion.builder().itemId("B").promoItemId("B").promoNo(2).promoItemPrice(new BigDecimal(45)).build());
        activePromotionsList.add(ActivePromotion.builder().itemId("C").promoItemId("CD").getPromoItemIds("C,D").promoNo(1).promoItemPrice(new BigDecimal(0)).build());
        activePromotionsList.add(ActivePromotion.builder().itemId("D").promoItemId("CD").getPromoItemIds("C,D").promoNo(1).promoItemPrice(new BigDecimal(30)).build());
        doReturn(activePromotionsList).when(activePromotionsRepository).findAll();
        assertTrue(ReflectionTestUtils.invokeMethod(activePromotionsDao, "fillActivePromotions")
                .equals(activePromoMap.size()));
    }
}
