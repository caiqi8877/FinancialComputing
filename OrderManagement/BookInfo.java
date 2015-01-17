package edu.nyu.compfin14;

import java.util.SortedMap;
import java.util.TreeMap;

public class BookInfo {
	double bid_price;
	double ask_price;
	// bidbook(askbook) sorts a map : key = "price" , value = "size"
	// it is sorted by the price, so the last key in bidbook is the largest price, in other words, the bid price
	// it is sorted by the price, so the first key in askbook is the smallest price, in other words, the ask price
	SortedMap<Double,Integer> bidbook = new TreeMap<Double,Integer>();
	SortedMap<Double,Integer> askbook = new TreeMap<Double,Integer>();
	
	// display the bid price if the bid price is changed
	private void show_bidprice(String companyName,boolean display){
		//as bidbook is updated every time, bid_price is the previous bid price,
		//if the bid price != bidbook.lastKey(), means the current bid price(lastkey of bidbook)
		//has changed
		if( (bidbook.isEmpty() == false) && bid_price != bidbook.lastKey()){
			// assign the bid_price to current bid price
			bid_price = bidbook.lastKey();
			if(display)
				System.out.println(companyName + " Bid Price: " + bid_price);
		}
	}
	// display the ask price if the bid price is changed
	private void show_askprice(String companyName,boolean display){
		//as askbook is updated every time, ask_price is the previous ask price,
		//if the ask price != askbook.firstKey(), means the current ask price(firstkey of askbook)
		//has changed
		if( (askbook.isEmpty() == false) && ask_price != askbook.firstKey()){
			ask_price = askbook.firstKey();
			if(display)
				System.out.println(companyName + " Ask Price: " + ask_price);
		}
	}
/**
 * When a new buy order comes in, after it is already stored in the hashtable,
 * it should updates the information in the bidbook, the add_to_bidbook method updates
 * the informaiton of bidbook
 * @param price : the new order's limitprice
 * @param amount : the new order's size
 * @param companyName: the new order's symbol
 * @param display: see the bid-ask price or not
 */
	public void add_to_bidbook(double price,int amount,String companyName,boolean display){
		if(bidbook.containsKey(price))
		{
			// find the price key, add the new amount to the value of that price
			int next_amount = bidbook.get(price) + amount;
			bidbook.put(price,next_amount);
			// during the change, whenever the value equal 0 , means that the order(at this price) is canceled
			if(next_amount == 0)
				bidbook.remove(price);
		}
		// if the price of the order is new, just add it to the sortedmap
		else{
			bidbook.put(price,amount);
		}
		// display the change
		show_bidprice(companyName,display);
	}
	/**
	 * When a new sell order comes in, after it is already stored in the hashtable,
	 * it should updates the information in the bidbook, the add_to_bidbook method updates
	 * the informaiton of bidbook
	 * @param price : the new order's limitprice
	 * @param amount : the new order's size
	 * @param companyName: the new order's symbol
	 * @param display: see the bid-ask price or not
	 */
	public void add_to_askbook(double price,int amount,String companyName,boolean display){
		if(askbook.containsKey(price))
		{
			// find the price key, add the new amount to the value of that price
			int next_amount = askbook.get(price) + amount;
			askbook.put(price,next_amount);
			// during the change, whenever the value equal 0 , means that the order(at this price) is canceled
			if(next_amount == 0)
				askbook.remove(price);
		}
		// if the price of the order is new, just add it to the sortedmap
		else{
			askbook.put(price,amount);
		}
		// display the change
		show_askprice(companyName,display);
	}



}
