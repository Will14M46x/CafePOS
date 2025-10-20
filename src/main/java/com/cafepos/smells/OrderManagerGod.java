package com.cafepos.smells;

import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.catalog.Product;
import com.cafepos.pricing.*;

//God Class
public class OrderManagerGod {
    //Global/Static State
    //Primitive Obsession
    public static int TAX_PERCENT = 10;
    //Global/Static State
    public static String LAST_DISCOUNT_CODE = null;
    //Long Method
    //God Class
    public static String process(String recipe, int qty, String paymentType, String discountCode, boolean printReceipt) {
        ProductFactory factory = new ProductFactory();
        Product product = factory.create(recipe);
        Money unitPrice;
        try {
            var priced = product instanceof com.cafepos.decorator.Priced
                    p ? p.price() : product.basePrice();
            unitPrice = priced;
        } catch (Exception e) {
            unitPrice = product.basePrice();
        }
        if (qty <= 0) qty = 1;
        Money subtotal = unitPrice.multiply(qty);
        DiscountPolicy discountPolicy = DiscountPolicyFactory.getDiscountPolicy(discountCode);
        TaxPolicy taxPolicy = new FixedRateTaxPolicy(TAX_PERCENT);
        PricingService pricingService = new PricingService(discountPolicy, taxPolicy);
        PricingService.PricingResult pricingResult = pricingService.price(subtotal);

        if (discountCode != null) {
            LAST_DISCOUNT_CODE = discountCode;
        }




        if (paymentType != null) {
            if (paymentType.equalsIgnoreCase("CASH")) {
                System.out.println("[Cash] Customer paid " + pricingResult.total() + " EUR");
            } else if (paymentType.equalsIgnoreCase("CARD")) {
                System.out.println("[Card] Customer paid " + pricingResult.total() + " EUR with card ****1234");
            } else if (paymentType.equalsIgnoreCase("WALLET")) {
                System.out.println("[Wallet] Customer paid " + pricingResult.total() + " EUR via wallet user-wallet-789");
            } else {
                System.out.println("[UnknownPayment] " + pricingResult.total());
            }
        }
        //Feature Envy
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();
        String out = receiptPrinter.format(recipe,qty,pricingResult,TAX_PERCENT);
        if (printReceipt) {
            System.out.println(out);
        }
        return out;
    }
}
