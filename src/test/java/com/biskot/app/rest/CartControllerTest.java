package com.biskot.app.rest;

import com.biskot.app.BiskotApiApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {BiskotApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CartControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    @BeforeAll
    public void setup() {
        url = "http://localhost:" + port;
    }

    @Test
    public void create_cart_should_return_cart_id() {
        Integer cartId = restTemplate.postForObject( url+ "/carts", null, Integer.class);
        assertThat(cartId).isNotNull();
    }

    @Test
    public void get_cart_with_not_existing_id_should_return_not_found() {
        ResponseEntity<CartResponse> responseEntity = restTemplate.getForEntity(url + "/carts/10", CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void add_item_cart_with_not_existing_cart_id_should_return_not_found() {
        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setProductId(1);
        addItemRequest.setQuantity(10);
        HttpEntity<AddItemRequest> requestEntity = new HttpEntity<>(addItemRequest);
        ResponseEntity<CartResponse> responseEntity = restTemplate.exchange(url + "/carts/10/items", HttpMethod.PUT, requestEntity, CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void add_item_cart_with_not_allowed_quantity_should_return_bad_request() {
        Integer cartId = restTemplate.postForObject( url+ "/carts", null, Integer.class);
        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setProductId(1);
        addItemRequest.setQuantity(201);
        HttpEntity<AddItemRequest> requestEntity = new HttpEntity<>(addItemRequest);
        ResponseEntity<CartResponse> responseEntity = restTemplate.exchange(url + "/carts/" + cartId + "/items", HttpMethod.PUT, requestEntity, CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void add_item_cart_with_not_allowed_item_number_should_return_bad_request() {
        Integer cartId = restTemplate.postForObject( url+ "/carts", null, Integer.class);
        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setProductId(1);
        addItemRequest.setQuantity(5);
        HttpEntity<AddItemRequest> requestEntity = new HttpEntity<>(addItemRequest);
        ResponseEntity<CartResponse> responseEntity = restTemplate.exchange(url + "/carts/" + cartId + "/items", HttpMethod.PUT, requestEntity, CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        addItemRequest.setProductId(2);
        addItemRequest.setQuantity(5);
        responseEntity = restTemplate.exchange(url + "/carts/" + cartId + "/items", HttpMethod.PUT, requestEntity, CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        addItemRequest.setProductId(3);
        addItemRequest.setQuantity(5);
        responseEntity = restTemplate.exchange(url + "/carts/" + cartId + "/items", HttpMethod.PUT, requestEntity, CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        addItemRequest.setProductId(4);
        addItemRequest.setQuantity(5);
        responseEntity = restTemplate.exchange(url + "/carts/" + cartId + "/items", HttpMethod.PUT, requestEntity, CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void get_existing_cart_should_return_valid_response() {
        Integer cartId = restTemplate.postForObject( url+ "/carts", null, Integer.class);
        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setProductId(1);
        addItemRequest.setQuantity(5);
        HttpEntity<AddItemRequest> requestEntity = new HttpEntity<>(addItemRequest);
        ResponseEntity<CartResponse> responseEntity = restTemplate.exchange(url + "/carts/" + cartId + "/items", HttpMethod.PUT, requestEntity, CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        responseEntity = restTemplate.getForEntity(url + "/carts/" + cartId, CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        CartResponse cartResponse = responseEntity.getBody();
        assertThat(cartResponse).isNotNull();
        assertThat(cartResponse.getId()).isEqualTo(cartId);
        assertThat(cartResponse.getItems()).hasSize(1);
        assertThat(cartResponse.getItems().get(0).getProductId()).isEqualTo(1);
        assertThat(cartResponse.getItems().get(0).getProductLabel()).isEqualTo("Déodorant Spray 200ml Ice Dive ADIDAS");
        assertThat(cartResponse.getItems().get(0).getQuantity()).isEqualTo(5);
        assertThat(cartResponse.getItems().get(0).getUnitPrice()).isEqualTo(2.00);
        assertThat(cartResponse.getItems().get(0).getLinePrice()).isEqualTo(10.00);
        assertThat(cartResponse.getTotalPrice()).isEqualTo(10.0);
    }

    @Test
    public void put_card_with_same_product_should_override_item_quantity() {
        Integer cartId = restTemplate.postForObject( url+ "/carts", null, Integer.class);
        AddItemRequest addItemRequest = new AddItemRequest();
        addItemRequest.setProductId(1);
        addItemRequest.setQuantity(5);
        HttpEntity<AddItemRequest> requestEntity = new HttpEntity<>(addItemRequest);
        ResponseEntity<CartResponse> responseEntity = restTemplate.exchange(url + "/carts/" + cartId + "/items", HttpMethod.PUT, requestEntity, CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        addItemRequest.setProductId(1);
        addItemRequest.setQuantity(10);
        responseEntity = restTemplate.exchange(url + "/carts/" + cartId + "/items", HttpMethod.PUT, requestEntity, CartResponse.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        responseEntity = restTemplate.getForEntity(url + "/carts/" + cartId, CartResponse.class);
        CartResponse cartResponse = responseEntity.getBody();
        assertThat(cartResponse).isNotNull();
        assertThat(cartResponse.getId()).isEqualTo(cartId);
        assertThat(cartResponse.getItems()).hasSize(1);
        assertThat(cartResponse.getItems().get(0).getProductId()).isEqualTo(1);
        assertThat(cartResponse.getItems().get(0).getProductLabel()).isEqualTo("Déodorant Spray 200ml Ice Dive ADIDAS");
        assertThat(cartResponse.getItems().get(0).getQuantity()).isEqualTo(10);
        assertThat(cartResponse.getItems().get(0).getUnitPrice()).isEqualTo(2.00);
        assertThat(cartResponse.getItems().get(0).getLinePrice()).isEqualTo(20.00);
        assertThat(cartResponse.getTotalPrice()).isEqualTo(20.0);
    }

}