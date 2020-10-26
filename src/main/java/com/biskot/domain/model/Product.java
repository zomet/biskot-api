package com.biskot.domain.model;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Product {

    private Id id;
    private String label;
    private Double unitPrice;
    private Integer quantityInStock;

    public Id getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    private Product() {}

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

    public static class Builder {

        private Id id;
        private String label;
        private Double unitPrice;
        private Integer quantityInStock;

        public Builder withId(Id id) {
            this.id = id;
            return this;
        }

        public Builder withLabel(String label) {
            this.label = label;
            return this;
        }

        public Builder withUnitPrice(Double unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public Builder withQuantityInStock(Integer quantityInStock) {
            this.quantityInStock = quantityInStock;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.id = requireNonNull(id);
            product.label = requireNonNull(label);
            product.unitPrice = requireNonNull(unitPrice);
            product.quantityInStock = requireNonNull(quantityInStock);
            return product;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
