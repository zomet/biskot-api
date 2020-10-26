package com.biskot.infra.gateway;

import com.biskot.domain.model.Product;
import com.biskot.domain.service.NotFoundException;
import com.biskot.infra.mock.ProductMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ProductGateway.class, ProductMockServer.class})
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RestTemplateConfiguration.class)
class ProductGatewayTest {

    @Autowired
    private ProductGateway productGateway;

    @Test
    public void find_existing_product_test() {
        Product product = productGateway.getProduct(1);
        assertThat(product).isNotNull();
        assertThat(product.getId().getValue()).isEqualTo(1);
        assertThat(product.getLabel()).isEqualTo("Déodorant Spray 200ml Ice Dive ADIDAS");
        assertThat(product.getUnitPrice()).isEqualTo(2.00);
        assertThat(product.getQuantityInStock()).isEqualTo(200);
    }

    @Test
    public void find_not_existing_product_should_throw_NotFoundException() {
        Assertions.assertThrows(NotFoundException.class, () -> productGateway.getProduct(10));
    }

}