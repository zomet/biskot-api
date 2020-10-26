package com.biskot.app.rest;

import com.biskot.domain.model.Cart;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CartResponseAdapter {

    private final ItemResponeAdapter itemResponeAdapter;

    public CartResponseAdapter(ItemResponeAdapter itemResponeAdapter) {
        this.itemResponeAdapter = itemResponeAdapter;
    }

    public CartResponse fromCart(Cart cart) {
        CartResponse cartResponse = new CartResponse();
        cartResponse.setId(cart.getId().getValue());
        cartResponse.setItems(cart.getItems()
                .stream()
                .map(itemResponeAdapter::fromItem)
                .collect(Collectors.toList()));
        cartResponse.setTotalPrice(cartResponse.getItems().stream().map(itemResponse -> itemResponse.getUnitPrice() * itemResponse.getQuantity()).reduce(0.0, Double::sum));
        return cartResponse;
    }
}
