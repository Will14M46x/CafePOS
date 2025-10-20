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
        Money discount = discountPolicy.discountOf(subtotal);

        if (discountCode != null) {
            LAST_DISCOUNT_CODE = discountCode;
        }

        Money discounted =
                Money.of(subtotal.asBigDecimal().subtract(discount.asBigDecimal()));
        if (discounted.asBigDecimal().signum() < 0) discounted =
                Money.zero();

        TaxPolicy taxPolicy = new FixedRateTaxPolicy(TAX_PERCENT);
        Money tax = taxPolicy.taxOn(discounted);

        //Primitive Obsession
        //Feature Envy
        //Shotgun Surgery Risk
        var total = discounted.add(tax);
        if (paymentType != null) {
            if (paymentType.equalsIgnoreCase("CASH")) {
                System.out.println("[Cash] Customer paid " + total + " EUR");
            } else if (paymentType.equalsIgnoreCase("CARD")) {
                System.out.println("[Card] Customer paid " + total + " EUR with card ****1234");
            } else if (paymentType.equalsIgnoreCase("WALLET")) {
                System.out.println("[Wallet] Customer paid " + total + " EUR via wallet user-wallet-789");
            } else {
                System.out.println("[UnknownPayment] " + total);
            }
        }
        //Feature Envy
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n");
                receipt.append("Subtotal: ").append(subtotal).append("\n");
        if (discount.asBigDecimal().signum() > 0) {
            receipt.append("Discount: -").append(discount).append("\n");
        }
        //Shotgun Surgery Risk
        receipt.append("Tax (").append(TAX_PERCENT).append("%): ").append(tax).append("\n");
                receipt.append("Total: ").append(total);
        String out = receipt.toString();
        if (printReceipt) {
            System.out.println(out);
        }
        return out;
    }
}
