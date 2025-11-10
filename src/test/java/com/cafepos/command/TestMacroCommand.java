package com.cafepos.command;

import com.cafepos.order.Order;
import com.cafepos.order.OrderIds;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestMacroCommand {
    @Test
    public void testUndoButton() {
        Order order = new Order(OrderIds.next());
        OrderService service = new OrderService(order);
        Command addEspressoCombo = new AddItemCommand(service, "ESP+SHOT+OAT", 1);
        Command addLargeLatte     = new AddItemCommand(service, "LAT+L", 1);
        Command macro = new MacroCommand(addEspressoCombo, addLargeLatte);
        macro.execute();
        assertEquals(2, order.items().size());
        macro.undo();
        assertEquals(0, order.items().size());
    }
}
