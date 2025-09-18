package com.cafepos.domain;

import java.util.concurrent.atomic.AtomicLong;

public class OrderIds {
    private static final AtomicLong number = new AtomicLong(1000);
    private OrderIds() {}

    public static long next() {
        return number.incrementAndGet();
    }
}
