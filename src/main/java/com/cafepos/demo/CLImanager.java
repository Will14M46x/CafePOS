package com.cafepos.demo;

import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.domain.OrderIds;
import com.cafepos.observers.CustomerNotifier;
import com.cafepos.observers.DeliveryDesk;
import com.cafepos.observers.KitchenDisplay;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.CashPayment;
import com.cafepos.payment.WalletPayment;

import java.util.Scanner;

public class CLImanager implements Runnable {

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Catalog catalog = new InMemoryCatalog();
        catalog.add(new SimpleProduct("P-ESP","Espresso", Money.of(2.50)));
        catalog.add(new SimpleProduct("P-LAT","Latte", Money.of(3.20)));
        catalog.add(new SimpleProduct("P-CAP","Cappuccino", Money.of(3.00)));

        Order order = new Order(OrderIds.next());
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
                        System.out.println("1) Espresso - 2.50 Euro");
                        System.out.println("2) Latte - 3.20 Euro");
                        System.out.println("3) Cappuccino - 3.00 Euro");
                        System.out.println("B)ack");
                        String input = scanner.nextLine().toUpperCase().substring(0,1);
                        boolean runningHowMany = true;
                        while (runningHowMany){
                            if (input.equals("B")){
                                runningAdd = false;
                                break;
                            }
                            switch (input) {
                                case "1":
                                    boolean loop = true;
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
                                        System.out.println("Total amount will be: "+Money.of(2.50).multiply(num)+", do you wish to continue? Y/N");
                                        boolean yncon = true;
                                        while (yncon){
                                            input = scanner.nextLine().toUpperCase().substring(0,1);
                                            switch (input) {
                                                case "Y":
                                                    order.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(),num));
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
                                case "2":
                                    boolean loop2 = true;
                                    while (loop2){
                                        System.out.println("Please say how many ('1', '2' etc, maximum of 9) or B)ack: ");
                                        String amount2 = scanner.nextLine().toUpperCase().substring(0,1);
                                        if (amount2.equals("B")){
                                            runningHowMany = false;
                                            break;
                                        }
                                        int num2 = 1;
                                        try{
                                            num2 = Integer.parseInt(amount2);
                                        }catch (NumberFormatException e){
                                            System.out.println("Please enter a valid number");
                                            break;
                                        }
                                        System.out.println("Total amount will be: "+Money.of(3.20).multiply(num2)+", do you wish to continue? Y/N");
                                        boolean con2 = true;
                                        while (con2){
                                            input = scanner.nextLine().toUpperCase().substring(0,1);
                                            switch (input) {
                                                case "Y":
                                                    order.addItem(new LineItem(catalog.findById("P-LAT").orElseThrow(),num2));                                                runningAdd = false;
                                                    System.out.println("Total Currently: "+order.subtotal());
                                                    System.out.println("Total Tax: "+order.taxAtPercent(10));
                                                    System.out.println("Total: "+order.totalWithTax(10));
                                                    runningAdd = false;
                                                    runningHowMany = false;
                                                    con2 = false;
                                                    break;
                                                case "N":
                                                    con2 =false;
                                                    break;
                                                default:
                                                    System.out.println("Please enter a valid Y/N");
                                                    break;
                                            }
                                        }
                                    }
                                    break;
                                case "3":
                                    boolean loop3 = true;
                                    while (loop3) {
                                        System.out.println("Please say how many ('1', '2' etc, maximum of 9) or B)ack: ");
                                        String amount3 = scanner.nextLine().toUpperCase().substring(0,1);
                                        if (amount3.equals("B")){
                                            runningHowMany = false;
                                            break;
                                        }
                                        int num3 = 1;
                                        try{
                                            num3 = Integer.parseInt(amount3);
                                        }catch (NumberFormatException e){
                                            System.out.println("Please enter a valid number");
                                            break;
                                        }
                                        System.out.println("Total amount will be: "+Money.of(3.00).multiply(num3)+", do you wish to continue? Y/N");
                                        boolean con3 = true;
                                        while (con3){
                                            input = scanner.nextLine().toUpperCase().substring(0,1);
                                            switch (input) {
                                                case "Y":
                                                    order.addItem(new LineItem(catalog.findById("P-CAP").orElseThrow(),num3));
                                                    System.out.println("Total Currently: "+order.subtotal());
                                                    System.out.println("Total Tax: "+order.taxAtPercent(10));
                                                    System.out.println("Total: "+order.totalWithTax(10));
                                                    runningAdd = false;
                                                    runningHowMany = false;
                                                    con3 = false;
                                                    break;
                                                case "N":
                                                    con3 =false;
                                                    break;
                                                default:
                                                    System.out.println("Please enter a valid Y/N");
                                                    break;
                                            }
                                        }
                                    }
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
                    if (order.getItems().isEmpty()){
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
                                            break;
                                        }else{
                                            System.out.println("Please enter a valid card number");
                                        }
                                    }
                                case "2":
                                    order.pay(new CashPayment());
                                    order.markReady();
                                    runningPay = false;
                                    break;
                                case "3":
                                    System.out.println("Please enter Wallet ID: ");
                                        String walletId = scanner.nextLine();
                                        order.pay(new WalletPayment(walletId));
                                    order.markReady();
                                    runningPay = false;
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
                    if (order.getItems().isEmpty()){
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
}
