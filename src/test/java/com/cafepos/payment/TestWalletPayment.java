package com.cafepos.payment;

import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.order.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.order.OrderIds;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestWalletPayment {
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
    @Ignore
    public void testWalletPayment() {
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P-ESP", "Espresso", Money.of(2.50)));
        Order order = new Order(OrderIds.next());
        order.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2));
        order.pay(new WalletPayment("0001"));

        String outputString = outContent.toString().trim();
        assertTrue(outputString.contains("[Wallet]"));
        assertTrue(outputString.contains("EUR"));
        assertTrue(outputString.contains("0001"));
    }
}
