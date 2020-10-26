package com.biskot.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdGeneratorTest {

    @Test
    void generate() {
        Integer id = IdGenerator.generate(10);
        assertThat(id).isNotNull();
        assertThat(id).isGreaterThan(IdGenerator.MIN);
        assertThat(id).isLessThan(10);
    }
}