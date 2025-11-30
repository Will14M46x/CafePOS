package com.cafepos.demo;

import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.order.OrderIds;
import com.cafepos.factory.ProductFactory;
import com.cafepos.observers.CustomerNotifier;
import com.cafepos.observers.DeliveryDesk;
import com.cafepos.observers.KitchenDisplay;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.CashPayment;
import com.cafepos.payment.WalletPayment;

import java.util.Scanner;

public class CLImanager implements Runnable {
    private Order order;
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        ProductFactory factory = new ProductFactory();

        order = new Order(OrderIds.next());
        order.register(new KitchenDisplay());
        order.register(new DeliveryDesk());
        order.register(new CustomerNotifier());


        boolean running = true;
        while (running) {
            System.out.println("Welcome to the Cafe");
            System.out.println("A)dd Item  ,  P)ay  ,  R)eady Order  ,  Q)uit");
            String cmd = scanner.nextLine().toUpperCase().substring(0,1);
            switch (cmd) {
                case "A":
                    boolean runningAdd = true;
                    while (runningAdd){
                        System.out.println("Please choose from selection: ");
                        System.out.println("1) "+factory.create("ESP").name() + " - "+ factory.create("ESP").basePrice() + " Euro");
                        System.out.println("2) "+factory.create("LAT").name() + " - "+ factory.create("LAT").basePrice() + " Euro");
                        System.out.println("3) "+factory.create("CAP").name() + " - "+ factory.create("CAP").basePrice() + " Euro");
                        System.out.println("B)ack");
                        String input = scanner.nextLine().toUpperCase().substring(0,1);
                        boolean runningHowMany = true;
                        while (runningHowMany){
                            String id = "";
                            Money money;
                            LineItem item;
                            if (input.equals("B")){
                                runningAdd = false;
                                break;
                            }
                            switch (input) {
                                case "1","2","3":
                                    if(input.equals("1")){
                                        id = "ESP";
                                        money = factory.create(id).basePrice();
                                    }else if (input.equals("2")){
                                        id = "LAT";
                                        money = factory.create(id).basePrice();
                                    }else{
                                        id = "CAP";
                                        money = factory.create(id).basePrice();
                                    }
                                    boolean decoratorQ = true;
                                    boolean loop = true;
                                    while (decoratorQ){
                                        item = new LineItem(factory.create(id),1);
                                        System.out.println("Current selection: "+item.product().name()+" for "+item.lineTotal());
                                        System.out.println("Please select decorator: ");
                                        System.out.println("1) Extra Shot,  +0.80 Euro");
                                        System.out.println("2) Oat Milk,  +0.50 Euro");
                                        System.out.println("3) Syrup,  +0.40 Euro");
                                        System.out.println("4) Size Large,  +0.70 Euro");
                                        System.out.println("T)hat's All");
                                        System.out.println("B)ack");
                                        String decoratorChoice = scanner.nextLine().toUpperCase().substring(0,1);
                                        switch (decoratorChoice) {
                                            case "1":
                                                id += "+SHOT";
                                                break;
                                            case "2":
                                                id += "+OAT";
                                                break;
                                            case "3":
                                                id += "+SYP";
                                                break;
                                            case "4":
                                                id += "+L";
                                                break;
                                            case "T":
                                                decoratorQ = false;
                                                break;
                                            case "B":
                                                decoratorQ = false;
                                                loop = false;
                                                runningHowMany = false;
                                                break;
                                        }
                                    }
                                    while (loop){
                                        System.out.println("Please say how many ('1', '2' etc, maximum of 9) or B)ack: ");
                                        String amount = scanner.nextLine().toUpperCase().substring(0,1);
                                        if (amount.equals("B")){
                                            runningHowMany = false;
                                            break;
                                        }
                                        int num = 1;
                                        try{
                                            num = Integer.parseInt(amount);
                                        }catch (NumberFormatException e){
                                            System.out.println("Please enter a valid number");
                                            break;
                                        }
                                        item = new LineItem(factory.create(id),num);
                                        System.out.println("Total amount will be: "+item.lineTotal()+", do you wish to continue? Y/N");
                                        boolean yncon = true;
                                        while (yncon){
                                            input = scanner.nextLine().toUpperCase().substring(0,1);
                                            switch (input) {
                                                case "Y":
                                                    order.addItem(item);
                                                    System.out.println("Total Currently: "+order.subtotal());
                                                    System.out.println("Total Tax: "+order.taxAtPercent(10));
                                                    System.out.println("Total: "+order.totalWithTax(10));
                                                    loop = false;
                                                    yncon = false;
                                                    runningHowMany = false;
                                                    runningAdd = false;
                                                    break;
                                                case "N":
                                                    yncon = false;
                                                    break;
                                                default:
                                                    System.out.println("Please enter a valid Y/N");
                                                    break;
                                            }
                                        }
                                    }
                                    break;
//
                                default:
                                    System.out.println("Please enter valid input");
                                    runningHowMany = false;
                                    break;
                            }
                        }
                    }
                    break;
                case "P":
                    boolean runningPay = true;
                    if (order.items().isEmpty()){
                        System.out.println("Please select at least one item");
                        runningPay = false;
                    }
                    while (runningPay) {
                        System.out.println("Please choose how to pay: ");
                        System.out.println("1) Card");
                        System.out.println("2) Cash");
                        System.out.println("3) Wallet");
                        boolean runningPaymentType = true;
                        while (runningPaymentType) {
                            String input = scanner.nextLine().toUpperCase().substring(0, 1);
                            switch (input) {
                                case "1":
                                    System.out.println("Please enter Card Number: ");
                                    boolean cardEntered = true;
                                    while (cardEntered) {
                                        String cardNum = scanner.nextLine();
                                        if (cardNum.matches("\\d{16}")){
                                            order.pay(new CardPayment(cardNum));
                                            order.markReady();
                                            runningPay = false;
                                            removeAllFromOrder();
                                            break;
                                        }else{
                                            System.out.println("Please enter a valid card number");
                                        }
                                    }
                                    break;
                                case "2":
                                    order.pay(new CashPayment());
                                    order.markReady();
                                    runningPay = false;
                                    removeAllFromOrder();
                                    break;
                                case "3":
                                    System.out.println("Please enter Wallet ID: ");
                                        String walletId = scanner.nextLine();
                                        order.pay(new WalletPayment(walletId));
                                    order.markReady();
                                    runningPay = false;
                                    removeAllFromOrder();
                                        break;
                                default:
                                    System.out.println("Please enter valid input");
                                    break;
                            }
                            break;
                        }
                    }
                    break;
                case "R":
                    if (order.items().isEmpty()){
                        System.out.println("Please select at least one item");
                    }else{
                        order.markReady();
                    }
                    break;
                case "Q":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }
    }

    private void removeAllFromOrder(){
        for (int i = 0; i < order.items().size(); i++){
            order.items().remove(i);
        }
    }
}
