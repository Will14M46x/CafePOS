package com.cafepos.domain;

import com.cafepos.common.Money;
import com.cafepos.order.LineItem;
import com.cafepos.payment.PaymentStrategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class Order implements OrderPublisher {
    private final long id;
    private final List<LineItem> items = new ArrayList<>();
    private final List<OrderObserver> observers = new ArrayList<>();

    public Order(long id) {
        this.id = id;
    }
    public void addItem(LineItem li) {
        items.add(li);
        notifyObservers("itemAdded");
    }
    public Money subtotal() {
        return items.stream().map(LineItem::lineTotal).reduce(Money.zero(), Money::add);
    }
    public Money taxAtPercent(int percent) {
        if (percent < 0) {
            throw new IllegalArgumentException("percent must be >= 0");
        }
        Money multiplied = subtotal().multiply(percent);
        BigDecimal numeric = BigDecimal.valueOf(
                Double.parseDouble(multiplied.toString())
        );

        BigDecimal divided = numeric.divide(
                BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP
        );
        return Money.of(divided.doubleValue());
    }
    public Money totalWithTax(int percent) {
        if (percent <= 0) throw new IllegalArgumentException("percent must be greater than zero");
        return subtotal().add(taxAtPercent(percent));
    }
    public long id() {
        return id;
    }
    public List<LineItem> items() {
        return items;
    }

    public void pay(PaymentStrategy strategy) {
        if (strategy == null) throw new IllegalArgumentException("strategy required");
        strategy.pay(this);
        notifyObservers("paid");
    }

    @Override
    public void register(OrderObserver o) {
        if (o == null) throw new IllegalArgumentException("observer required");
        observers.add(o);
    }

    @Override
    public void unregister(OrderObserver o) {
        if (o == null) throw new IllegalArgumentException("observer required");
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Order order, String eventType) {}

    public void notifyObservers(String eventType) {
        for (OrderObserver observer : observers) {
            observer.updated(this,eventType);
        }
    }

    public void markReady(){
        notifyObservers("ready");
    }
}
