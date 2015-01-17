package edu.nyu.compfin14;
import java.util.Iterator;
import java.util.List;
import java.util.Hashtable;
import java.util.SortedMap;
import java.util.TreeMap;
public class Solution extends OrdersIterator {
/**
 * * create two collections:
 		* idbook stored the key  = "Order ID of each order" , value = "order object"(six fields contains all
 		* imformation about the order) 
 		* 1,String symbol;
		* 2,String orderId;
		* 3,int size;
		* 4,double limitPrice;
		* 5,LinkedList pre;(previous order)
		* 6,LinkedList next;(next order)
        * company store the key-value pair which key : order symbol , value : object of that symbol
        * the object class is defined in class: BookInfo
        * make them global variable because they are used almost in all methods
 */
	static Hashtable<String,LinkedList> idbook = new Hashtable<String,LinkedList>();
	static Hashtable<String,BookInfo> company = new Hashtable<String,BookInfo>();

/**
 * when a new order comes in, addNewOrder method take in the new order, and change the related id book and bidbook(or askbook);
 * @param obj : the the object of the new order
 * @param display: used to display the order or not
 * @param previous : previous node in the linkedlist, represents last order information
 */
	private static void addNewOrder(Object obj,LinkedList previous,boolean display){
		NewOrder new_order = (NewOrder) obj;
		LinkedList node = new LinkedList();
		//using NewOrder interface to store the order imformation in each node
		node.size = new_order.getSize();
		node.orderId = new_order.getOrderId();
		node.symbol = new_order.getSymbol();
		// if the company symbol appears previous, let comp point to that company name instance (for example, IBM BookInfo)
		// if the company symbol does not exist, for example, a new order with symbol name "google" comes in
		// then create new instance of BookInfo
		if(!company.containsKey(node.symbol)){
			company.put(node.symbol, new BookInfo());
		}
		BookInfo comp = company.get(node.symbol);
		// if the order limitPrice is NaN, then use the BookInfo class method to get the market price
		// size > 0 means it is a buy order, then look for that in bidbook
		// size < 0 means it is a seel order, then look for that in sellbook
		if(Double.isNaN(new_order.getLimitPrice())){
			node.limitPrice = (node.size > 0) ? comp.bidbook.lastKey() : comp.askbook.firstKey();
		}
		// or just assign the price to the node attribute
		else {
			node.limitPrice = new_order.getLimitPrice();
		}
		// if the price is NaN, replace it with the market value
		// if node.size (the amount of the order) is positive, means it is a buy order
		// if node.size (the amount of the order) is negative, means it is a sell order

		// connect the current node ( present order) to the previous node ( previous order) in the doubly linkedlist
		// made the current node the previous node , so in next iteration, could make the linkedlist constantly add new element

		previous.next = node;
		node.pre = previous;
		previous = node;
		// stored the key  = "Order ID of each order" , value = "order object" pair in the hashtable
		// this is used to minimize the query time, which reduce from O(n) to O(1)
		idbook.put(node.orderId,node);
		// again, node.size determine it is a buy order or sell order
		// add the new order amount (classied by price ) to the bidbook (askbook)
		if(node.size > 0){
			comp.add_to_bidbook(node.limitPrice,node.size,node.symbol,display);
		}
		else{
			comp.add_to_askbook(node.limitPrice, node.size, node.symbol, display);
		}
	}

/**
 * 
 * @param node: order information and store in the linkedlist
 * @param new_amount: when a replace order comes in, the buy(sell) order amount is replaced, 
 * @param comp: decide it is which company, IBM or MSFT
 * @param display: decide whether to display it or not
 * @return the updated order's amount
 */
	private static void replace_order(LinkedList node,int new_amount,double new_price,BookInfo comp,boolean display){
		//
		//if CxR order price equal to related order price, then just change the size of bidbook (askbook)
		//in comparison if CxR order price not equal to related order price, means has to reduce original
		//price amount to zero and add new price amount
		if(node.limitPrice == new_price){
			// buy order condition
			// in class BookInfo method add_to_bidbook , it add existed amount with new coming amount
			// in writing the param as (new_amount - node.size), this is the increase( or decrease ) of the amount
			// which in method add_to_bidbook, this is taken as a change value
			if(node.size > 0){
				comp.add_to_bidbook(node.limitPrice, new_amount - node.size , node.symbol , display);
				node.size = new_amount;
			}
			else{
				comp.add_to_askbook(node.limitPrice, new_amount - node.size , node.symbol , display);
				node.size = new_amount;
			}
		}
		// In this condition, as the ID remains, unlike that in cancel order, you have to remain the
		// node in the linkedlist, just update imformation in the bidbook(askbook)
		// and it completes in two step, first reduce the original amount of original price , and then 
		// add new amount of new price , in the same time , update information in this node , as the 
		// node information(size, price ) is changed
		else if(node.limitPrice != new_price){
			// buy order
			if(node.size > 0){
				comp.add_to_bidbook(node.limitPrice ,(0 - node.size) ,node.symbol, display);
				node.size = new_amount;
				node.limitPrice = new_price;
				comp.add_to_bidbook(new_price, new_amount, node.symbol , display);
			}
			// sell order
			else{
				comp.add_to_askbook(node.limitPrice ,(0 - node.size) ,node.symbol, display);
				node.size = new_amount;
				node.limitPrice = new_price;
				comp.add_to_askbook(new_price, new_amount, node.symbol , display);
			}
		}
	}
/**
 * 
 * @param node: order information and store in the linkedlist
 * @param display: if flag is ture, then display the bid-ask price
 * @param comp: decide which belongs to which company: IBM or MSFT
 */ 
	private static void cancel_order(LinkedList node,BookInfo comp,boolean display){
		
		//updata book information
		if(node.size>0){
			//update bidbook , buy order
			// 0 - node.size is to reduce the amount = node.size in the bid book ( of that price)
			comp.add_to_bidbook(node.limitPrice ,(0 - node.size) ,node.symbol, display);
		}else{
			//update askbook , see order
			// 0 - node.size same as above
			comp.add_to_askbook(node.limitPrice ,(0 - node.size) ,node.symbol, display);
		}
		// deleting a node in doubly linkedlist
		// letting pre.next point to next
		// and next.pre point to pre , so no connect between cur and pre (or cur and next)
		node.pre.next = node.next;
		// if it is the last node, just let pre.next point to null
		if(node.next != null){
			node.next.pre = node.pre;
		}
		// remove this key-value pair in the hashtable (idbook)
		idbook.remove(node.orderId);
	}


/**
 * run iterator in Ordersiterator.java
 * @param display : display the bid-ask price
 * @param a : the orders iterator 
 */
	public static void Runable(boolean display,OrdersIterator a){
		// create an instance of OrdersIterator a
		
		// create an instance of Iterator (type interface Message) m
		Iterator<Message> m;
		// using interface method to get the date in the instance of OrdersIterator
		m = a.getIterator();
		// create new linkedlist, and head point to the head of the linkedlist
		LinkedList head = new LinkedList();
		// previous used for link the linkedlist
		LinkedList previous = head;
		
		double bid_price = Double.NaN, ask_price = Double.NaN;
		while(m.hasNext()){
			// iterate each order
			// m.next() point to the order object
			// String name get the class name
			
			Object obj = m.next();
			String name = obj.getClass().getSimpleName();
			// if it is a new order
			if(name.compareTo("NewOrderImpl") == 0){
				addNewOrder(obj,previous,display);
			}
			// if it is a cancel or replace order
			else if(name.compareTo("OrderCxRImpl") == 0)
			{
//				//type cast the object to OrderCxR
				OrderCxR cxr_order = (OrderCxR) obj;
//				//using interface method to get the orderid
				String find_id = cxr_order.getOrderId();
//				//search the id in the idbook(linkedlist)
				LinkedList node = idbook.get(find_id);
				BookInfo comp = company.get(node.symbol);
				// size = 0 means cancel order
				if( cxr_order.getSize() == 0 ){
					cancel_order(node,comp,display);
				}
			    // siez != 0 means replace order
				else
				{
					replace_order(idbook.get(find_id),cxr_order.getSize(),cxr_order.getLimitPrice(),comp,display);
				}
			}
		}
	}
	// main function: takes two param : a boolean to decide whether to display the bid-ask price
	// another is the orders iterator
	public static void main(String arg[]){
		boolean display = true;
//		boolean display = false;
		OrdersIterator a = new OrdersIterator();
		// you can change the display value(from true to false or vice verse) 
		// to decide whether to see the bid-ask price
		Runable(display,a);
	}
}

