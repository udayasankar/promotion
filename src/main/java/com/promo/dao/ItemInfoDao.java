package com.promo.dao;

import com.promo.models.ItemInfo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ItemInfoDao {
    private static Map<String, ItemInfo> itemInfoMap = new HashMap<>();

    static {
        itemInfoMap.put("A", ItemInfo.builder().itemId("A").itemPrice(new BigDecimal(50)).build());
        itemInfoMap.put("B", ItemInfo.builder().itemId("B").itemPrice(new BigDecimal(30)).build());
        itemInfoMap.put("C", ItemInfo.builder().itemId("C").itemPrice(new BigDecimal(20)).build());
        itemInfoMap.put("D", ItemInfo.builder().itemId("D").itemPrice(new BigDecimal(10)).build());
    }

    public Map<String, ItemInfo> getItemInfoMap() {
        return itemInfoMap;
    }
}
