package com.cafepos.observers;

import com.cafepos.domain.Order;
import com.cafepos.domain.OrderObserver;

public class CustomerNotifier implements OrderObserver {
    @Override
    public void updated(Order order, String eventType) {
        if (!eventType.equals("paid")) {
            System.out.println("[Customer] Dear customer, your Order #"+order.id()+" has been updated: "+eventType);
        }
    }
}
