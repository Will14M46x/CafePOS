package com.cafepos.observers;

import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderObserver;

public class KitchenDisplay implements OrderObserver {
    @Override
    public void updated(Order order, String eventType){
        if (eventType.equals("itemAdded")){
            String quantityAndName = "";
            for (int i = 0; i < order.getItems().size(); i++){
                LineItem li = order.getItems().get(i);
                if (i>=1) quantityAndName += ", ";
                quantityAndName += li.quantity() + "x "+li.product().name();
            }
            System.out.println("[Kitchen] Order #"+order.id()+": "+quantityAndName+" added");
        }else if (eventType.equals("paid")){
            System.out.println("[Kitchen] Order #"+order.id()+": payment received");
        }
    }
}
