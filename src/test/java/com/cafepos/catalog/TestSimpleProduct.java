package com.cafepos.catalog;

import com.cafepos.common.Money;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TestSimpleProduct {
    String testId;
    String testName;
    Money testPrice;
    SimpleProduct testProduct;

    @Before
    public void createSimpleProduct() {
        testId = "tester";
        testName = "testing";
        testPrice = Money.of(15);
        testProduct = new SimpleProduct(testId, testName, testPrice);
    }

    @Test
    public void testID() {
        assertEquals(testProduct.id(), testId);
    }

    @Test
    public void testName() {
        assertEquals(testProduct.name(), testName);
    }

    @Test
    public void testBasePrice() {
        assertEquals(testProduct.basePrice(), testPrice);
    }
}
