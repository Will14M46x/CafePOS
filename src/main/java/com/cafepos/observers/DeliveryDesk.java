package com.cafepos.observers;

import com.cafepos.domain.Order;
import com.cafepos.domain.OrderObserver;

public class DeliveryDesk implements OrderObserver {

    @Override
    public void updated(Order order, String eventType) {
        if (eventType.equals("ready")){
            System.out.println("[Delivery] Order #"+order.id()+" is ready for delivery");
        }
    }
}
