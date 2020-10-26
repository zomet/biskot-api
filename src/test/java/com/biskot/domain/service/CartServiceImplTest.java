package com.biskot.domain.service;

import com.biskot.domain.model.Cart;
import com.biskot.domain.model.Item;
import com.biskot.domain.model.Product;
import com.biskot.domain.spi.CartPersistencePort;
import com.biskot.domain.spi.ProductPort;
import com.biskot.infra.repository.InMemoryCartRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartServiceImplTest {

    CartPersistencePort cartPersistencePort = mock(InMemoryCartRepository.class);
    ProductPort productPort = mock(ProductPort.class);
    CartService cartService;

    @BeforeAll
    public void setup() {
        cartService = new CartServiceImpl(cartPersistencePort, productPort);
    }

    @Test
    public void add_item_to_cart_with_not_allowed_quantity_should_throw_exception() {
        when(productPort.getProduct(1)).thenReturn(createProduct(1, 50));
        assertThrows(ApplicationException.class, () -> cartService.addItemToCart(1, 1, 100));
        verify(productPort, times(1)).getProduct(1);
    }

    @Test
    public void add_item_to_cart_with_not_allowed_item_number_should_throw_exception() {
        when(productPort.getProduct(4)).thenReturn(createProduct(4, 50));
        when(cartPersistencePort.getCart(anyInt())).thenReturn(createCart());
        assertThrows(ApplicationException.class, () -> cartService.addItemToCart(1, 4, 5));
        verify(productPort, times(1)).getProduct(4);
    }

    private Optional<Cart> createCart() {
        Cart cart = new Cart(new Cart.Id(1));
        Item item1 = new Item(new Item.Id(1), 10);
        item1.setProduct(createProduct(1, 10));
        Item item2 = new Item(new Item.Id(2), 20);
        item2.setProduct(createProduct(2, 20));
        Item item3 = new Item(new Item.Id(3), 30);
        item3.setProduct(createProduct(3, 30));
        cart.getItems().add(item1);
        cart.getItems().add(item2);
        cart.getItems().add(item3);
        return Optional.of(cart);
    }

    private Product createProduct(int productId, int quantityInStock) {
        return new Product.Builder()
                .withId(new Product.Id(productId))
                .withLabel("product label")
                .withUnitPrice(9.99)
                .withQuantityInStock(quantityInStock)
                .build();
    }

}