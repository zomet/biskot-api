package com.biskot.app.rest;

import com.biskot.domain.model.Item;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ItemResponeAdapter {

    public ItemResponse fromItem(Item item) {
        if (Objects.isNull(item)) {
            return null;
        }

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.setProductId(item.getId().getValue());
        itemResponse.setProductLabel(item.getProduct().getLabel());
        itemResponse.setUnitPrice(item.getProduct().getUnitPrice());
        itemResponse.setQuantity(item.getQuantity());
        itemResponse.setLinePrice(item.getProduct().getUnitPrice() * item.getQuantity());
        return itemResponse;
    }
}
