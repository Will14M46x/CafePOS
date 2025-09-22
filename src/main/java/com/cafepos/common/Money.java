package com.cafepos.common;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

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
        if (a.signum() < 0) throw new IllegalArgumentException("amount must be positive");
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

    public Money multiply(BigDecimal factor) {
        Objects.requireNonNull(factor, "factor required");
        return new Money(this.amount.multiply(factor));
    }

    @Override
    public int compareTo(Money o) {
        return this.amount.compareTo(o.amount);
    }

    @Override
    public String toString() {
        return amount.toPlainString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money other = (Money) o;
        return this.amount.compareTo(other.amount) == 0;
    }

    @Override
    public int hashCode() {
        return amount.stripTrailingZeros().hashCode();
    }
}
