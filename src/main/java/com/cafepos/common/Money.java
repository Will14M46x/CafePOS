package com.cafepos.common;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money implements Comparable<Money> {
    private final BigDecimal amount;
    public static Money of(double value) {
        return new Money(new BigDecimal(value));
    }
    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }
    private Money(BigDecimal a) {
        if (a == null) throw new IllegalArgumentException("amount required");
        this.amount = a.setScale(2, RoundingMode.HALF_UP);
    }
    public Money add(Money other) {
        if (other == null) throw new IllegalArgumentException("amount required");
        return new Money(this.amount.add(other.amount));
    }
    public Money multiply(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("amount required");
        return new Money(this.amount.multiply(new BigDecimal(qty)));
    }

    public int compareTo(Money o) {
        return 0;
    }
}
