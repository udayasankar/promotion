package com.promo.dao;

import com.promo.entities.Items;
import com.promo.models.ActivePromotions;
import com.promo.models.ItemInfo;
import com.promo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemInfoDao {
    private static Map<String, ItemInfo> itemInfoMap = new HashMap<>();

    @Autowired
    ItemRepository itemRepository;

    @PostConstruct
    void fillItemMap()
    {
        List<Items> itemsList = itemRepository.findAll();
        for(Items items: itemsList)
            itemInfoMap.put(items.getItemId(), ItemInfo.builder().itemId(items.getItemId()).itemPrice(items.getItemPrice()).build());
        System.out.println(itemInfoMap);
    }

    /*static {
        itemInfoMap.put("A", ItemInfo.builder().itemId("A").itemPrice(new BigDecimal(50)).build());
        itemInfoMap.put("B", ItemInfo.builder().itemId("B").itemPrice(new BigDecimal(30)).build());
        itemInfoMap.put("C", ItemInfo.builder().itemId("C").itemPrice(new BigDecimal(20)).build());
        itemInfoMap.put("D", ItemInfo.builder().itemId("D").itemPrice(new BigDecimal(10)).build());
    }*/

    public Map<String, ItemInfo> getItemInfoMap() {
        return itemInfoMap;
    }
}
