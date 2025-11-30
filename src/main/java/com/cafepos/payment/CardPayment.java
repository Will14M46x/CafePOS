package com.cafepos.payment;

import com.cafepos.common.Money;
import com.cafepos.domain.Order;

public final class CardPayment implements PaymentStrategy {
    private final String cardNumber;
    public CardPayment(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()){
            throw new IllegalArgumentException("cardNumber is null");
        }
        this.cardNumber = cardNumber;
    }
    @Override
    public void pay(Order order) {
// mask card and print payment confirmation
        String maskedDetails = "****"+cardNumber.substring(cardNumber.length()-4);
        int taxPct = 10;
        Money total = order.totalWithTax(taxPct);
        System.out.printf("[Card] Customer paid %s EUR with card %s%n", total, maskedDetails);
    }
}
