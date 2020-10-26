package com.biskot.infra.gateway.payload;

import com.biskot.domain.model.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseAdapterTest {

    @Test
    public void from_product_response_test() {
        Product product = ProductResponseAdapter.fromProductResponse(createProductResponse());
        assertThat(product).isNotNull();
        assertThat(product.getId().getValue()).isEqualTo(1);
        assertThat(product.getLabel()).isEqualTo("product label");
        assertThat(product.getUnitPrice()).isEqualTo(9.99);
        assertThat(product.getQuantityInStock()).isEqualTo(100);
    }

    private ProductResponse createProductResponse() {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(1);
        productResponse.setLabel("product label");
        productResponse.setUnitPrice(9.99);
        productResponse.setQuantityInStock(100);
        return productResponse;
    }

}