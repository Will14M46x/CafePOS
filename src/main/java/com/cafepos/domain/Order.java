package com.cafepos.domain;

import com.cafepos.common.Money;

import java.util.ArrayList;
import java.util.List;

public final class Order {
    private final long id;
    private final List<LineItem> items = new ArrayList<>();
    public Order(long id) {
        this.id = id;
    }
    public void addItem(LineItem li) {
        items.add(li);
    }
    public Money subtotal() {
        return items.stream().map(LineItem::lineTotal).reduce(Money.zero(), Money::add);
    }
    public Money taxAtPercent(int percent) {
        if (percent <= 0) throw new IllegalArgumentException("percent must be greater than zero");
        return subtotal().multiply(percent/100);
    }
    public Money totalWithTax(int percent) {
        if (percent <= 0) throw new IllegalArgumentException("percent must be greater than zero");
        return subtotal().add(subtotal().multiply(percent/100));
    }
}
