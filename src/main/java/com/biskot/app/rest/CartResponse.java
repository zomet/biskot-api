package com.biskot.app.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CartResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("items")
    private List<ItemResponse> items;

    @JsonProperty("totalPrice")
    private Double totalPrice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemResponse> items) {
        this.items = items;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
