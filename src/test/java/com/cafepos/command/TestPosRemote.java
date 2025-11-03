package com.cafepos.command;

import com.cafepos.order.Order;
import com.cafepos.order.OrderIds;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestPosRemote {
    @Test
    public void testUndoButton() {
        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        PosRemote remote = new PosRemote(2);
        remote.setSlot(0, new AddItemCommand(service, "ESP", 1));
        remote.setSlot(1, new AddItemCommand(service, "LAT+L", 2));
        remote.press(0);
        remote.press(1);
        assertEquals(2, order.items().size());
        remote.undo();
        assertEquals(1, order.items().size());
        assertTrue(order.items().get(0).product().name().toUpperCase().contains("ESP"));
    }
}
