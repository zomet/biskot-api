package com.biskot.domain.service;

import com.biskot.domain.model.Cart;
import com.biskot.domain.model.IdGenerator;
import com.biskot.domain.model.Item;
import com.biskot.domain.model.Product;
import com.biskot.domain.spi.CartPersistencePort;
import com.biskot.domain.spi.ProductPort;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class CartServiceImpl implements CartService {

    private static final int MAX_CART_ITEM_NUMBER = 3;

    private final CartPersistencePort cartPersistencePort;
    private final ProductPort productPort;

    public CartServiceImpl(CartPersistencePort cartPersistencePort, ProductPort productPort) {
        this.cartPersistencePort = cartPersistencePort;
        this.productPort = productPort;
    }

    @Override
    public Cart.Id createCart() {
        return cartPersistencePort.saveCart(new Cart(new Cart.Id(IdGenerator.generate(100))));
    }

    @Override
    public Cart getCart(int cartId) {
        Cart cart = cartPersistencePort.getCart(cartId).orElseThrow(() -> new NotFoundException("Cart not found"));
        cart.getItems().forEach(this::fillItemProductInfos);
        return cart;
    }

    @Override
    public void addItemToCart(int cartId, int productId, int quantityToAdd) {
        Product product = productPort.getProduct(productId);
        if (product.getQuantityInStock() < quantityToAdd) {
            throw new ApplicationException(MessageFormat.format("Quantity not allowed, max {0}", product.getQuantityInStock()));
        }
        Cart cart = getCart(cartId);
        Item item = new Item(new Item.Id(productId), quantityToAdd);
        if (cart.getItems().size() == MAX_CART_ITEM_NUMBER && !cart.getItems().contains(item)) {
            throw new ApplicationException(MessageFormat.format("Items Number not allowed, max {0}", MAX_CART_ITEM_NUMBER));
        }
        cart.getItems().remove(item);
        cart.getItems().add(item);
        cartPersistencePort.saveCart(cart);
    }

    private void fillItemProductInfos(Item item) {
        Product product = productPort.getProduct(item.getId().getValue());
        item.setProduct(product);
    }
}
