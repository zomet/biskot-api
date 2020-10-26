package com.biskot.infra.gateway.payload;

import com.biskot.domain.model.Product;

public class ProductResponseAdapter {

    private ProductResponseAdapter() {}

    public static Product fromProductResponse(ProductResponse productResponse) {
        return new Product.Builder()
                .withId(new Product.Id(productResponse.getId()))
                .withLabel(productResponse.getLabel())
                .withUnitPrice(productResponse.getUnitPrice())
                .withQuantityInStock(productResponse.getQuantityInStock())
                .build();
    }
}
