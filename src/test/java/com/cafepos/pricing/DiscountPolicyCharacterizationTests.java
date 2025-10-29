package com.cafepos.pricing;

import com.cafepos.smells.OrderManagerGod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountPolicyCharacterizationTests {
    @Test
    void loyal5_applies_5_percent_discount() {
        String receipt = OrderManagerGod.process("ESP+SHOT+OAT", 1, "CASH", "LOYAL5", false);
        assertTrue(receipt.contains("Discount: -0.19"), "LOYAL5 should apply 5% discount (0.19) on subtotal 3.80");
        assertTrue(receipt.contains("Subtotal: 3.80"));
        assertTrue(receipt.contains("Tax (10%): 0.36"));
        assertTrue(receipt.contains("Total: 3.97"));
        assertEquals("LOYAL5", OrderManagerGod.LAST_DISCOUNT_CODE);
    }

    @Test
    void coupon1_applies_fixed_1_dollar_discount() {
        String receipt = OrderManagerGod.process("ESP+SHOT", 1, "WALLET", "COUPON1", false);
        assertTrue(receipt.contains("Discount: -1.00"), "COUPON1 should apply fixed $1.00 discount");
        assertTrue(receipt.contains("Subtotal: 3.30"));
        assertTrue(receipt.contains("Tax (10%): 0.23"));
        assertTrue(receipt.contains("Total: 2.53"));
        assertEquals("COUPON1", OrderManagerGod.LAST_DISCOUNT_CODE);
    }
    @Test
    void unknown_discount_code_applies_no_discount() {
        String receipt = OrderManagerGod.process("ESP", 1, "CARD", "INVALID_CODE", false);
        assertFalse(receipt.contains("Discount:"), "Unknown discount code should not apply any discount");
        assertTrue(receipt.contains("Subtotal: 2.50"));
        assertTrue(receipt.contains("Tax (10%): 0.25"));
        assertTrue(receipt.contains("Total: 2.75"));
        assertEquals("INVALID_CODE", OrderManagerGod.LAST_DISCOUNT_CODE);
    }

    @Test
    void none_discount_code_applies_no_discount() {
        String receipt = OrderManagerGod.process("ESP+SHOT+OAT", 1, "CASH", "NONE", false);
        assertFalse(receipt.contains("Discount:"));
        assertTrue(receipt.contains("Subtotal: 3.80"));
        assertTrue(receipt.contains("Tax (10%): 0.38"));
        assertTrue(receipt.contains("Total: 4.18"));

        assertEquals("NONE", OrderManagerGod.LAST_DISCOUNT_CODE);
    }
}