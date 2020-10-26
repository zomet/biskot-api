package com.biskot.infra.repository;

import com.biskot.domain.model.Cart;
import com.biskot.domain.model.Item;
import com.biskot.infra.repository.entity.CartEntity;
import com.biskot.infra.repository.entity.ItemEntity;
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
public class CartEntityAdapterTest {

    private CartEntityAdapter cartEntityAdapter;

    private final ItemEntityAdapter itemEntityAdapter = mock(ItemEntityAdapter.class);

    @BeforeAll
    public void setup() {
        cartEntityAdapter = new CartEntityAdapter(itemEntityAdapter);
        ItemEntity mockedItemEntity = new ItemEntity();
        mockedItemEntity.setItemEntityId(2);
        mockedItemEntity.setItemQuantity(20);
        when(itemEntityAdapter.fromItem(any(Item.class))).thenReturn(mockedItemEntity);
    }

    @Test
    public void from_cart_test_null_cart_mapping_shoud_return_null() {
        assertThat(cartEntityAdapter.fromCart(null)).isNull();
    }

    @Test
    public void from_cart_test_not_null_cart_mapping_should_return_valid_cart_entity() {
        CartEntity cartEntity = cartEntityAdapter.fromCart(createCart());
        verify(itemEntityAdapter, times(1)).fromItem(new Item(new Item.Id(1), 10));
        verify(itemEntityAdapter, times(1)).fromItem(new Item(new Item.Id(2), 20));
        assertThat(cartEntity).isNotNull();
        assertThat(cartEntity.getCartEntityId()).isEqualTo(10);
        assertThat(cartEntity.getItemEntities()).hasSize(2);
    }

    private Cart createCart() {
        Cart cart = new Cart(new Cart.Id(10));
        cart.getItems().add(new Item(new Item.Id(1), 10));
        cart.getItems().add(new Item(new Item.Id(2), 20));
        return cart;
    }

    @Test
    public void to_cart_test_null_cart_mapping_shoud_return_null() {
        assertThat(cartEntityAdapter.toCart(null)).isNull();
    }

    @Test
    public void to_cart_test_not_null_cart_mapping_should_return_valid_cart_entity() {
        ItemEntity itemEntity1 = new ItemEntity();
        itemEntity1.setItemEntityId(1);
        itemEntity1.setItemQuantity(10);
        ItemEntity itemEntity2 = new ItemEntity();
        itemEntity2.setItemEntityId(2);
        itemEntity2.setItemQuantity(20);
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCartEntityId(1);
        cartEntity.getItemEntities().add(itemEntity1);
        cartEntity.getItemEntities().add(itemEntity2);
        Cart cart = cartEntityAdapter.toCart(cartEntity);
        verify(itemEntityAdapter, times(1)).toItem(itemEntity1);
        verify(itemEntityAdapter, times(1)).toItem(itemEntity2);
        assertThat(cart).isNotNull();
        assertThat(cart.getId().getValue()).isEqualTo(1);
        assertThat(cart.getItems()).hasSize(2);
    }

}