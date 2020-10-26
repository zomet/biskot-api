package com.biskot.app.rest;

import com.biskot.domain.service.CartService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final CartResponseAdapter cartResponseAdapter;

    public CartController(CartService cartService, CartResponseAdapter cartResponseAdapter) {
        this.cartService = cartService;
        this.cartResponseAdapter = cartResponseAdapter;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer addCart() {
        return cartService.createCart().getValue();
    }

    @GetMapping(value = "/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CartResponse getCartById(@PathVariable("cartId") int cartId) {
        return cartResponseAdapter.fromCart(cartService.getCart(cartId));
    }

    @PutMapping(value = "/{cartId}/items", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateCart(@PathVariable("cartId") Integer cartId, @RequestBody AddItemRequest itemRequest) {
        cartService.addItemToCart(cartId, itemRequest.getProductId(), itemRequest.getQuantity());
    }

}
