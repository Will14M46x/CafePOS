package com.cafepos.observers;

import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.payment.CashPayment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;

public class TestKitchenDisplay {
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
    public void testItemAddedEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new KitchenDisplay());
        order.addItem(new LineItem(catalog.findById("P- ESP").orElseThrow(), 1));

        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Kitchen] Order #"));
        assertTrue(outputString.contains(": 1x Espresso added"));
    }

    @Test
    public void testBruteForceItemAddedEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new KitchenDisplay());
        order.notifyObservers("itemAdded");

        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Kitchen] Order #"));
        assertTrue(outputString.contains(":  added"));
    }

    @Test
    public void testPaidEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new KitchenDisplay());
        order.pay(new CashPayment());

        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Kitchen] Order #"));
        assertTrue(outputString.contains(": payment received"));
    }

    @Test
    public void testBruteForcePaidEventType(){
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P- ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.register(new KitchenDisplay());
        order.notifyObservers("paid");

        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Kitchen] Order #"));
        assertTrue(outputString.contains(": payment received"));
    }
}
