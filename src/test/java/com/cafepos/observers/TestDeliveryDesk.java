package com.cafepos.observers;

import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.order.OrderIds;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class TestDeliveryDesk {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream output = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(output);
    }

    @Test
    public void testReadyEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new DeliveryDesk());
        order.addItem(new LineItem(catalog.findById("P- ESP").orElseThrow(), 1));
        order.markReady();


        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Delivery] Order #"));
        assertTrue(outputString.contains(" is ready for delivery"));
    }

    @Test
    public void testBruteForceReadyEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new DeliveryDesk());
        order.notifyObservers("ready");

        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Delivery] Order #"));
        assertTrue(outputString.contains(" is ready for delivery"));
    }
}
