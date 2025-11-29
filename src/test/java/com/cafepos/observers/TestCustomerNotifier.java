package com.cafepos.observers;

import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.order.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.order.OrderIds;
import com.cafepos.payment.CashPayment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestCustomerNotifier {
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
        order.register(new CustomerNotifier());
        order.addItem(new LineItem(catalog.findById("P- ESP").orElseThrow(), 1));
        order.markReady();


        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Customer] Dear customer, your Order #"));
        assertTrue(outputString.contains(" has been updated: ready"));
    }

    @Test
    public void testBruteForceReadyEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new CustomerNotifier());
        order.notifyObservers("ready");

        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Customer] Dear customer, your Order #"));
        assertTrue(outputString.contains(" has been updated: ready"));
    }

    @Test
    public void testItemAddedEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new CustomerNotifier());
        order.addItem(new LineItem(catalog.findById("P- ESP").orElseThrow(), 1));

        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Customer] Dear customer, your Order #"));
        assertTrue(outputString.contains(" has been updated: itemAdded"));
    }

    @Test
    public void testBruteForceItemAddedEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new CustomerNotifier());
        order.notifyObservers("itemAdded");

        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Customer] Dear customer, your Order #"));
        assertTrue(outputString.contains(" has been updated: itemAdded"));
    }

    @Test
    public void testPaidEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new CustomerNotifier());
        order.pay(new CashPayment());

        String outputString = outContent.toString().trim();
        assertFalse(outputString.contains(" has been updated: paid"));
    }

    @Test
    public void testBruteForcePaidEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new CustomerNotifier());
        order.notifyObservers("paid");

        String outputString = outContent.toString().trim();
        assertFalse(outputString.contains(" has been updated: paid"));
    }
}
