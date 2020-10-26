package com.biskot.infra.repository.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartEntity {

    private Integer cartEntityId;
    private List<ItemEntity> itemEntities = new ArrayList<>();

    public Integer getCartEntityId() {
        return cartEntityId;
    }

    public void setCartEntityId(Integer cartEntityId) {
        this.cartEntityId = cartEntityId;
    }

    public List<ItemEntity> getItemEntities() {
        return itemEntities;
    }

    public void setItemEntities(List<ItemEntity> itemEntities) {
        this.itemEntities = itemEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartEntity that = (CartEntity) o;
        return cartEntityId.equals(that.cartEntityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartEntityId);
    }
}
