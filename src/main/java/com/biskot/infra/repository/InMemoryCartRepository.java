package com.biskot.infra.repository;

import com.biskot.domain.model.Cart;
import com.biskot.domain.spi.CartPersistencePort;
import com.biskot.infra.repository.entity.CartEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryCartRepository implements CartPersistencePort {

    private final CartEntityAdapter cartEntityAdapter;

    private final Map<Integer, CartEntity> cartEntities = new HashMap<>();

    public InMemoryCartRepository(CartEntityAdapter cartEntityAdapter) {
        this.cartEntityAdapter = cartEntityAdapter;
    }

    @Override
    public Optional<Cart> getCart(int id) {
        return Optional.ofNullable(cartEntityAdapter.toCart(cartEntities.get(id)));
    }

    @Override
    public Cart.Id saveCart(Cart cart) {
        cartEntities.put(cart.getId().getValue(), cartEntityAdapter.fromCart(cart));
        return cart.getId();
    }
}
