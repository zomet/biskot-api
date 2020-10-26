package com.biskot.domain.model;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Item {

    private final Id id;
    private final Integer quantity;
    private Product product;

    public Item(Id id, Integer quantity) {
        this.id = requireNonNull(id);
        this.quantity = requireNonNull(quantity);
    }

    public Id getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static class Id {

        private final int value;

        public Id(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return value == id.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
