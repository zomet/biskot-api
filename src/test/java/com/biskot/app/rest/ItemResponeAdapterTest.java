package com.biskot.app.rest;

import com.biskot.domain.model.Item;
import com.biskot.domain.model.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemResponeAdapterTest {

    private final ItemResponeAdapter itemResponeAdapter = new ItemResponeAdapter();

    @Test
    void null_item_mapping_should_return_null() {
        assertThat(itemResponeAdapter.fromItem(null)).isNull();
    }

    @Test
    void not_null_item_mapping_should_return_valid_response() {
        ItemResponse itemResponse = itemResponeAdapter.fromItem(createItem());
        assertThat(itemResponse).isNotNull();
        assertThat(itemResponse.getProductId()).isEqualTo(1);
        assertThat(itemResponse.getProductLabel()).isEqualTo("product label");
        assertThat(itemResponse.getUnitPrice()).isEqualTo(9.99);
        assertThat(itemResponse.getQuantity()).isEqualTo(100);
        assertThat(itemResponse.getLinePrice()).isEqualTo(999.00);
    }

    private Item createItem() {
        Item item = new Item(new Item.Id(1), 100);
        item.setProduct(new Product.Builder()
            .withId(new Product.Id(2))
                .withLabel("product label")
                .withUnitPrice(9.99)
                .withQuantityInStock(100)
                .build());
        return item;
    }

}