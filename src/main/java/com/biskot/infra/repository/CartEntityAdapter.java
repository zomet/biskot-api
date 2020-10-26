package com.biskot.infra.repository;

import com.biskot.domain.model.Cart;
import com.biskot.infra.repository.entity.CartEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CartEntityAdapter {

    private final ItemEntityAdapter itemEntityAdapter;

    public CartEntityAdapter(ItemEntityAdapter itemEntityAdapter) {
        this.itemEntityAdapter = itemEntityAdapter;
    }

    public CartEntity fromCart(Cart cart) {
        if (Objects.isNull(cart)) {
            return null;
        }
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartEntityId(cart.getId().getValue());
        cartEntity.setItemEntities(cart.getItems().stream().map(itemEntityAdapter::fromItem).collect(Collectors.toList()));
        return cartEntity;
    }

    public Cart toCart(CartEntity cartEntity) {
        if (Objects.isNull(cartEntity)) {
            return null;
        }
        Cart cart = new Cart(new Cart.Id(cartEntity.getCartEntityId()));
        cart.getItems().addAll(cartEntity.getItemEntities().stream().map(itemEntityAdapter::toItem).collect(Collectors.toList()));
        return cart;
    }
}
