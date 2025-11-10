package com.cafepos.domain;

import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.order.LineItem;
import com.cafepos.order.Order;
import com.cafepos.payment.PaymentStrategy;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestOrder {
    @Test
    public void order_totals() {
        var p1 = new SimpleProduct("A", "A", Money.of(2.50));
        var p2 = new SimpleProduct("B", "B", Money.of(3.50));
        var o = new Order(1);
        o.addItem(new LineItem(p1, 2));
        o.addItem(new LineItem(p2, 1));
        assertEquals(Money.of(8.50), o.subtotal());
        assertEquals(Money.of(0.85), o.taxAtPercent(10));
        assertEquals(Money.of(9.35), o.totalWithTax(10));
    }

    @Test
    @Ignore
    public void payment_strategy_called(){
        var p = new SimpleProduct("A","A", Money.of(5));
        var order = new Order(42);
        order.addItem(new LineItem(p, 1));
        final boolean[] called = {false};
        PaymentStrategy fake = o -> called[0] = true;
        order.pay(fake);
        assertTrue("Payment strategy should be called",called[0]);
    }

    @Test
    public void observers_notified_on_item_add(){
        var p = new SimpleProduct("A","A", Money.of(5));
        var o = new Order(1);
        o.addItem(new LineItem(p, 1));

        List<String> events = new ArrayList<>();
        o.register((order,evt) -> events.add(evt));

        o.addItem(new LineItem(p, 1));
        assertTrue(events.contains("itemAdded"));
    }
}
