package com.biskot.infra.repository;

import com.biskot.domain.model.Item;
import com.biskot.infra.repository.entity.ItemEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemEntityAdapterTest {

    ItemEntityAdapter itemEntityAdapter = new ItemEntityAdapter();

    @Test
    public void from_item_test_null_item_entity_map_should_return_null() {
        assertThat(itemEntityAdapter.fromItem(null)).isNull();
    }

    @Test
    public void from_item_test_not_null_entity_mapping_should_return_valid_item_entity() {
        Item item = new Item(new Item.Id(1), 20);
        ItemEntity itemEntity = itemEntityAdapter.fromItem(item);
        assertThat(itemEntity).isNotNull();
        assertThat(itemEntity.getItemEntityId()).isEqualTo(1);
        assertThat(itemEntity.getItemQuantity()).isEqualTo(20);
    }

    @Test
    public void to_item_test_null_item_entity_map_should_return_null() {
        assertThat(itemEntityAdapter.toItem(null)).isNull();
    }

    @Test
    public void to_item_test_not_null_entity_mapping_should_return_valid_item_entity() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setItemEntityId(1);
        itemEntity.setItemQuantity(20);
        Item item = itemEntityAdapter.toItem(itemEntity);
        assertThat(item).isNotNull();
        assertThat(item.getId().getValue()).isEqualTo(1);
        assertThat(item.getQuantity()).isEqualTo(20);
    }

}