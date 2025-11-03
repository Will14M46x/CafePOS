package com.cafepos.command;

import com.cafepos.order.Order;
import com.cafepos.order.OrderIds;
import com.cafepos.payment.PaymentStrategy;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCommandFlow {

    static final class FakePayment implements PaymentStrategy {
        boolean called = false;
        Order lastOrder = null;
        @Override
        public void pay(Order order) {
            called = true;
            lastOrder = order;
        }
    }

    @Test
    public void testCommandFlow() {
        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        FakePayment fakePay = new FakePayment();

        PosRemote remote = new PosRemote(3);
        remote.setSlot(0, new AddItemCommand(service, "LAT+L", 2));
        remote.setSlot(1, new AddItemCommand(service, "ESP+SHOT+OAT", 1));
        remote.setSlot(2, new PayOrderCommand(service, fakePay, 10));

        remote.press(0);
        remote.press(1);
        remote.press(2);

        assertTrue(fakePay.called);
        assertSame(order, fakePay.lastOrder);

        assertEquals(2, order.items().size());
    }
}
