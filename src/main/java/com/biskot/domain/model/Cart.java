package com.biskot.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {

    private final Id id;

    private final List<Item> items = new ArrayList<>();

    public Cart(Id id) {
        this.id = Objects.requireNonNull(id);
    }

    public Id getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
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
        Cart cart = (Cart) o;
        return id.equals(cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
