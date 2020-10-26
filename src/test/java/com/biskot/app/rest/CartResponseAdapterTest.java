package com.biskot.app.rest;

import com.biskot.domain.model.Cart;
import com.biskot.domain.model.Item;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartResponseAdapterTest {

    private CartResponseAdapter cartResponseAdapter;
    ItemResponeAdapter itemResponeAdapter = mock(ItemResponeAdapter.class);

    @BeforeAll
    public void setup() {
        ItemResponse mockItemResponse = new ItemResponse();
        mockItemResponse.setProductId(1);
        mockItemResponse.setProductLabel("product label");
        mockItemResponse.setUnitPrice(9.99);
        mockItemResponse.setQuantity(1);
        mockItemResponse.setLinePrice(9.99);
        when(itemResponeAdapter.fromItem(any(Item.class))).thenReturn(mockItemResponse);
        cartResponseAdapter = new CartResponseAdapter(itemResponeAdapter);
    }

    @Test
    void not_null_cart_mapping_should_return_valid_response() {
        CartResponse cartResponse = cartResponseAdapter.fromCart(createCart());
        verify(itemResponeAdapter, times(1)).fromItem(new Item(new Item.Id(2), 20));
        verify(itemResponeAdapter, times(1)).fromItem(new Item(new Item.Id(3), 30));
        assertThat(cartResponse).isNotNull();
        assertThat(cartResponse.getId()).isEqualTo(1);
        assertThat(cartResponse.getItems()).hasSize(2);
        assertThat(cartResponse.getTotalPrice()).isEqualTo(19.98);
    }

    private Cart createCart() {
        Cart cart = new Cart(new Cart.Id(1));
        cart.getItems().add(new Item(new Item.Id(2), 20));
        cart.getItems().add(new Item(new Item.Id(3), 30));
        return cart;
    }
}