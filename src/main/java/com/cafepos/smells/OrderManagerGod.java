package com.cafepos.smells;

import com.cafepos.checkout.DiscountPolicy;
import com.cafepos.checkout.ReceiptPrinter;
import com.cafepos.checkout.TaxPolicy;
import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.order.OrderIds;
import com.cafepos.factory.ProductFactory;
import com.cafepos.catalog.Product;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.CashPayment;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.payment.WalletPayment;
import com.cafepos.pricing.*;

//God Class
public class OrderManagerGod {
    private final ProductFactory productFactory;
    private final ReceiptPrinter receiptPrinter;
    private final int taxPercent;

    //They stay for now because they are literally needed thanks to later parts of the lab requiring them :)
    public static int TAX_PERCENT = 10;
    public static String LAST_DISCOUNT_CODE = null;

    public OrderManagerGod(ProductFactory productFactory, ReceiptPrinter receiptPrinter, int taxPercent){
        this.productFactory = productFactory;
        this.receiptPrinter = receiptPrinter;
        this.taxPercent = taxPercent;
    }
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


        PaymentStrategy paymentStrategy;
        Order order = new Order(OrderIds.next());
        order.addItem(new LineItem(new ProductFactory().create(recipe),qty));

        if (paymentType != null) {
            if (paymentType.equalsIgnoreCase("CASH")) {
                paymentStrategy = new CashPayment();
            } else if (paymentType.equalsIgnoreCase("CARD")) {
                paymentStrategy = new CardPayment("1234123412341234");
            } else if (paymentType.equalsIgnoreCase("WALLET")) {
                paymentStrategy = new WalletPayment("user-wallet-789");
            } else {
                paymentStrategy = new CashPayment();
            }
            paymentStrategy.pay(order);
        }
        ReceiptPrinter receiptPrinter1 = new ReceiptPrinter();
        String out = receiptPrinter1.format(recipe,qty,pricingResult,TAX_PERCENT);
        if (printReceipt) {
            System.out.println(out);
        }
        return out;
    }
}
