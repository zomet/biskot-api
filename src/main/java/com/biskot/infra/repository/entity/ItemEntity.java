package com.biskot.infra.repository.entity;

import java.util.Objects;

public class ItemEntity {

    private Integer itemEntityId;
    private Integer itemQuantity;

    public Integer getItemEntityId() {
        return itemEntityId;
    }

    public void setItemEntityId(Integer itemEntityId) {
        this.itemEntityId = itemEntityId;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEntity that = (ItemEntity) o;
        return itemEntityId.equals(that.itemEntityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemEntityId);
    }
}
