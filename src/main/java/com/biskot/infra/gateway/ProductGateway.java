package com.biskot.infra.gateway;

import com.biskot.domain.model.Product;
import com.biskot.domain.service.NotFoundException;
import com.biskot.domain.spi.ProductPort;
import com.biskot.infra.gateway.payload.ProductResponse;
import com.biskot.infra.gateway.payload.ProductResponseAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class ProductGateway implements ProductPort {

    private final RestTemplate restTemplate;

    public ProductGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product getProduct(int productId) {
        String productServerUrl = "http://localhost:9001";
        ProductResponse productResponse;
        try {
            productResponse = restTemplate.getForObject(productServerUrl + "/products/" + productId, ProductResponse.class);
        } catch (HttpClientErrorException e) {
            throw new NotFoundException("product not found");
        }
        if (Objects.isNull(productResponse)) {
            throw new NotFoundException("product not found");
        }
        return ProductResponseAdapter.fromProductResponse(productResponse);
    }

}
