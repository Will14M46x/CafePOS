package com.cafepos.pricing;

import com.cafepos.common.Money;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IndividualDiscountPolicyTests {

    @Test
    void noDiscount_returns_zero() {
        DiscountPolicy policy = new NoDiscount();
        assertEquals(Money.zero(), policy.discountOf(Money.of(10.00)));
        assertEquals(Money.zero(), policy.discountOf(Money.of(100.00)));
        assertEquals(Money.zero(), policy.discountOf(Money.of(0.01)));
    }

    @Test
    void noDiscount_handles_zero2() {
        DiscountPolicy policy = new NoDiscount();
        assertEquals(Money.zero(), policy.discountOf(Money.zero()));
    }

    @Test
    void loyaltyPercent_5_percent_of() {
        DiscountPolicy policy = new LoyaltyPercentDiscount(5);
        Money subtotal = Money.of(3.80);
        Money discount = policy.discountOf(subtotal);
        assertEquals(Money.of(0.19), discount);
    }

    @Test
    void loyaltyPercent_5_percent_of2() {
        DiscountPolicy policy = new LoyaltyPercentDiscount(5);
        Money subtotal = Money.of(7.80);
        Money discount = policy.discountOf(subtotal);
        assertEquals(Money.of(0.39), discount);
    }

    @Test
    void loyaltyPercent_5_percent_of3() {
        DiscountPolicy policy = new LoyaltyPercentDiscount(5);
        Money subtotal = Money.of(2.50);

        Money discount = policy.discountOf(subtotal);

        // 2.50 * 5% = 0.125, rounds to 0.13
        assertEquals(Money.of(0.13), discount);
    }

    @Test
    void fixedCoupon_1_euro() {
        DiscountPolicy policy = new FixedCouponDiscount(Money.of(1.00));
        Money subtotal = Money.of(3.30);
        Money discount = policy.discountOf(subtotal);
        assertEquals(Money.of(1.00), discount);
    }

    @Test
    void fixedCoupon_1_euro2() {
        DiscountPolicy policy = new FixedCouponDiscount(Money.of(1.00));
        Money subtotal = Money.of(11.70);

        Money discount = policy.discountOf(subtotal);

        assertEquals(Money.of(1.00), discount);
    }

    @Test
    void fixedCoupon_1_euro3() {
        DiscountPolicy policy = new FixedCouponDiscount(Money.of(1.00));
        Money subtotal = Money.of(2.50);

        Money discount = policy.discountOf(subtotal);

        assertEquals(Money.of(1.00), discount);
    }

    @Test
    void fixedCoupon_caps_at_subtotal_when_coupon_larger() {
        DiscountPolicy policy = new FixedCouponDiscount(Money.of(5.00));
        Money subtotal = Money.of(2.00);

        Money discount = policy.discountOf(subtotal);

        // Discount should be capped at subtotal
        assertEquals(Money.of(2.00), discount);
    }
}