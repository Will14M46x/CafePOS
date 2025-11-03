package com.cafepos.printing;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestLegacyAdapter {
    @Test
    public void test() {
        FakeLegacy fake = new FakeLegacy();
        Printer p = new LegacyPrinterAdapter(fake);
        p.print("ABC");
        assertTrue(fake.lastLen >= 3);
        p.print("Order #123\nTotal: 8.58");
        assertTrue(fake.lastLen >= "Order #123\nTotal: 8.58".getBytes().length);
    }
}
