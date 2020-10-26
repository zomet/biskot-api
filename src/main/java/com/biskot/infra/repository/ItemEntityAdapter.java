package com.biskot.infra.repository;

import com.biskot.domain.model.Item;
import com.biskot.infra.repository.entity.ItemEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ItemEntityAdapter {

    public ItemEntity fromItem(Item item) {
        if (Objects.isNull(item)) {
            return null;
        }
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setItemEntityId(item.getId().getValue());
        itemEntity.setItemQuantity(item.getQuantity());
        return itemEntity;
    }

    public Item toItem(ItemEntity itemEntity) {
        if (itemEntity ==  null) {
            return null;
        }
        return new Item(new Item.Id(itemEntity.getItemEntityId()), itemEntity.getItemQuantity());
    }
}
