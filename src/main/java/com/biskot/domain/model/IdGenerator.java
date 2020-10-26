package com.biskot.domain.model;

import java.util.concurrent.ThreadLocalRandom;

public class IdGenerator {

    public static final int MIN = 1;

    private IdGenerator() {}

    public static Integer generate(int maxValue) {
        return ThreadLocalRandom.current().nextInt(MIN, maxValue + 1);
    }
}
