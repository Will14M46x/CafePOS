package com.cafepos.pricing;
import com.cafepos.factory.ProductFactory;
import com.cafepos.smells.OrderManagerGod;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


public class OrderManagerGodComparedToCheckoutServiceTests {
    @Test
    void loyaltyCheckout() {
        ProductFactory productFactory = new ProductFactory();
        DiscountPolicy discountPolicy = DiscountPolicyFactory.getDiscountPolicy("LOYAL5");
        TaxPolicy taxPolicy = new FixedRateTaxPolicy(10);
        PricingService pricingService = new PricingService(discountPolicy,taxPolicy);
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();
        int taxPercent = 10;
        CheckoutService checkoutService = new CheckoutService(productFactory,pricingService,receiptPrinter,taxPercent);

        String resultOMG = OrderManagerGod.process("ESP+SHOT+OAT", 1, "CASH", "LOYAL5", false);
        String resutlCS = checkoutService.checkout("ESP+SHOT+OAT",1);

        assertEquals(resutlCS,resultOMG);
    }
    @Test
    void couponCheckout() {
        ProductFactory productFactory = new ProductFactory();
        DiscountPolicy discountPolicy = DiscountPolicyFactory.getDiscountPolicy("COUPON1");
        TaxPolicy taxPolicy = new FixedRateTaxPolicy(10);
        PricingService pricingService = new PricingService(discountPolicy,taxPolicy);
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();
        int taxPercent = 10;
        CheckoutService checkoutService = new CheckoutService(productFactory,pricingService,receiptPrinter,taxPercent);

        String resultOMG = OrderManagerGod.process("ESP+SHOT+OAT", 1, "CASH", "COUPON1", false);
        String resutlCS = checkoutService.checkout("ESP+SHOT+OAT",1);

        assertEquals(resutlCS,resultOMG);
    }
    @Test
    void noDiscountCheckout() {
        ProductFactory productFactory = new ProductFactory();
        DiscountPolicy discountPolicy = DiscountPolicyFactory.getDiscountPolicy("NONE");
        TaxPolicy taxPolicy = new FixedRateTaxPolicy(10);
        PricingService pricingService = new PricingService(discountPolicy,taxPolicy);
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();
        int taxPercent = 10;
        CheckoutService checkoutService = new CheckoutService(productFactory,pricingService,receiptPrinter,taxPercent);

        String resultOMG = OrderManagerGod.process("ESP+SHOT+OAT", 1, "CASH", "NONE", false);
        String resutlCS = checkoutService.checkout("ESP+SHOT+OAT",1);

        assertEquals(resutlCS,resultOMG);
    }
}
