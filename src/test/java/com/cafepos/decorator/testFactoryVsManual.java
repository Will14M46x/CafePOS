package com.cafepos.decorator;

import com.cafepos.catalog.Product;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.factory.ProductFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class testFactoryVsManual {
    private Product viaFactory = new ProductFactory().create("ESP+SHOT+OAT+L");
    private Product viaManual = new SizeLarge(new OatMilk(new ExtraShot(new SimpleProduct("P-ESP","Espresso", Money.of(2.50)))));

    @Test
    public void testFactoryVsManualName() {
        assertTrue(viaFactory.name().equals(viaManual.name()));
    }

    @Test
    public void testFactoryVsManualPrice() {
        assertEquals(((Priced) viaFactory).price(), Money.of(4.50));
        assertEquals(Money.of(4.50),((Priced) viaManual).price());
        assertEquals(((Priced) viaFactory).price(),((Priced) viaManual).price());
    }
}
