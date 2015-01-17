package edu.nyu.compfin14;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class OrdersIterator {

    static  class NewOrderImpl implements NewOrder{
        private String symbol;
        private String orderId;
        private int size;
        private double limitPrice;

        NewOrderImpl(String symbol, String orderId, int size, double limitPrice) {
            this.symbol = symbol;
            this.orderId = orderId;
            this.size = size;
            this.limitPrice = limitPrice;
        }



        public String getSymbol() {
            return symbol;
        }

        public String getOrderId() {
            return orderId;
        }

        public int getSize() {
            return size;
        }

        public double getLimitPrice() {
            return limitPrice;
        }
    }

    static class OrderCxRImpl implements OrderCxR{

        private int size;
        private double limitPx;
        private String orderId;


        public OrderCxRImpl(int size, double limitPx, String orderId) {
            this.size = size;
            this.limitPx = limitPx;
            this.orderId = orderId;
        }

        public int getSize() {
            return size;
        }

        public double getLimitPrice() {
            return limitPx;
        }

        public String getOrderId() {
            return orderId;
        }
    }
    private List<Message> msgs;
    
    public OrdersIterator(){
        msgs = new LinkedList<Message>();
//test case 1: a few of orders mainly test order cancel
//        msgs.add(new NewOrderImpl("IBM","ABC1",1000,96.00));
//        msgs.add(new NewOrderImpl("IBM","ABC2",1000,99.00));
//        msgs.add(new OrderCxRImpl(100, 98, "ABC2"));
//        msgs.add(new OrderCxRImpl(0, 98, "ABC2"));
//test case 2: test different symbol      
//      
        msgs.add(new NewOrderImpl("IBM","ABC1",1000,96.00));
        msgs.add(new NewOrderImpl("IBM","ABC2",1000,97.00));
        msgs.add(new NewOrderImpl("IBM","ABC3",1000,95.00));
        msgs.add(new NewOrderImpl("MSFT","XYZ1",1000,198.00));
        msgs.add(new NewOrderImpl("MSFT","XYZ2",1000,199.00));
        msgs.add(new NewOrderImpl("IBM","IBM3",-1000, 103.00));
        msgs.add(new NewOrderImpl("IBM","IBM4",-1000, 102.00));
        msgs.add(new NewOrderImpl("IBM","IBM6", 100, Double.NaN));
        msgs.add(new NewOrderImpl("Google","Google1",-1000, 1003.00));
        msgs.add(new NewOrderImpl("Google","Google2",1000, 999.00));
        

//test case 3: test on large data: IBM for example
//          msgs.add(new NewOrderImpl("IBM" ,"ABC1",1000,85.00));
//          msgs.add(new NewOrderImpl("IBM","XYZ1",-1000,115.00));
//          int id = 1;
//          for(double price = 90; price < 101 ; price++ ){
//        	  for(int count = 20000; count >0 ; count--){
//        		  String str_id = "IBM" + id;
//        		  id++;
//        		  msgs.add(new NewOrderImpl("IBM",str_id, 1000, price));
//        	  }
//          }
//          for(double price = 111; price >= 101 ; price-- ){
//        	  for(int count = 20000; count >0 ; count--){
//        		  String str_id = "IBM" + id;
//        		  id++;
//        		  msgs.add(new NewOrderImpl("IBM",str_id, -1000, price));
//        	  }
//          }
          
    }
    public Iterator<Message> getIterator(){
        return msgs.iterator();
    }
}
