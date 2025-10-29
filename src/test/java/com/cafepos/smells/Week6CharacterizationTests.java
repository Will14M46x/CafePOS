package com.cafepos.smells;

import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.pricing.*;
import com.cafepos.smells.OrderManagerGod;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

@Ignore
public class Week6CharacterizationTests {

    @Test
    public void no_discount_cash_payment() {
        String receipt = OrderManagerGod.process("ESP+SHOT+OAT", 1, "CASH", "NONE", false);
        assertTrue(receipt.startsWith("Order (ESP+SHOT+OAT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.80"));
        assertTrue(receipt.contains("Tax (10%): 0.38"));
        assertTrue(receipt.contains("Total: 4.18"));
    }

    @Test
    public void loyalty_discount_card_payment() {
        String receipt = OrderManagerGod.process("LAT+L", 2, "CARD", "LOYAL5", false);
        assertTrue(receipt.contains("Subtotal: 7.80"));
        assertTrue(receipt.contains("Discount: -0.39"));
        assertTrue(receipt.contains("Tax (10%): 0.74"));
        assertTrue(receipt.contains("Total: 8.15"));
    }

    @Test
    public void coupon_fixed_amount_and_qty_clamp() {
        String receipt = OrderManagerGod.process("ESP+SHOT", 0, "WALLET", "COUPON1", false);
        assertTrue(receipt.contains("Order (ESP+SHOT) x1"));
        assertTrue(receipt.contains("Subtotal: 3.30"));
        assertTrue(receipt.contains("Discount: -1.00"));
        assertTrue(receipt.contains("Tax (10%): 0.23"));
        assertTrue(receipt.contains("Total: 2.53"));
    }
    // DiscountPolicy test
    @Test
    public void loyalty_discount_5_percent() {
        DiscountPolicy d = new LoyaltyPercentDiscount(5);
        assertEquals(Money.of(0.39), d.discountOf(Money.of(7.80)));
    }
    // TaxPolicy test
    @Test
    public void fixed_rate_tax_10_percent() {
        TaxPolicy t = new FixedRateTaxPolicy(10);
        assertEquals(Money.of(0.74), t.taxOn(Money.of(7.41)));
    }
    // PricingService test
    @Test
    public void pricing_pipeline() {
        var pricing = new PricingService(new LoyaltyPercentDiscount(5), new
                FixedRateTaxPolicy(10));
        var pr = pricing.price(Money.of(7.80));
        assertEquals(Money.of(0.39), pr.discount());
        assertEquals(Money.of(7.41),
                Money.of(pr.subtotal().asBigDecimal().subtract(pr.discount().asBigDecimal
                        ())));
        assertEquals(Money.of(0.74), pr.tax());
        assertEquals(Money.of(8.15), pr.total());
    }
}
