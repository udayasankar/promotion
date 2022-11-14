package com.promo.dao;

import com.promo.entities.Items;
import com.promo.models.ItemInfo;
import com.promo.repository.ItemRepository;
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
public class ItemInfoDaoTest {


    @Mock
    ItemRepository itemRepository;

    @Autowired
    ItemInfoDao itemInfoDao;

    @Test
    void fillItemMap() {
        Map<String, ItemInfo> itemInfoMap = mock(Map.class);
        ReflectionTestUtils.setField(itemInfoDao, "itemInfoMap", itemInfoMap);
        List<Items> itemsList = new ArrayList<>();
        itemsList.add(Items.builder().itemId("A").itemPrice(new BigDecimal(50)).build());
        itemsList.add(Items.builder().itemId("B").itemPrice(new BigDecimal(30)).build());
        itemsList.add(Items.builder().itemId("C").itemPrice(new BigDecimal(20)).build());
        itemsList.add(Items.builder().itemId("D").itemPrice(new BigDecimal(10)).build());
        doReturn(itemsList).when(itemRepository).findAll();
        assertTrue(ReflectionTestUtils.invokeMethod(itemInfoDao, "fillItemMap")
                .equals(itemInfoMap.size()));
    }
}
