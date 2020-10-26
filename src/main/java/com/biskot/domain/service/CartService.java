package com.biskot.domain.service;

import com.biskot.domain.model.Cart;

public interface CartService {

    Cart.Id createCart();

    Cart getCart(int cartId);

    void addItemToCart(int cartId, int productId, int quantityToAdd);
}
