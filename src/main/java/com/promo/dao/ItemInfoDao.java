package com.promo.dao;

import com.promo.entities.Items;
import com.promo.models.ItemInfo;
import com.promo.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ItemInfoDao {
    private static Map<String, ItemInfo> itemInfoMap = new HashMap<>();

    @Autowired
    ItemRepository itemRepository;

    @PostConstruct
    int fillItemMap() {
        log.info("Loading the iteam information and saving in cache");
        List<Items> itemsList = itemRepository.findAll();
        for (Items items : itemsList)
            itemInfoMap.put(items.getItemId(), ItemInfo.builder().itemId(items.getItemId()).itemPrice(items.getItemPrice()).build());
        return itemInfoMap.size();
    }

    public Map<String, ItemInfo> getItemInfoMap() {
        return itemInfoMap;
    }
}
