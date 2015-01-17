package OptionPricing;

import java.util.List;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class Payout_calculator implements PayOut{
	
	//two methods below, first one is to calculate the option's price given that option is only excised at the last day
	//just need to get the last day price of the path
	public double getPayout(StockPath path){
		List<Pair<DateTime, Double>> list = path.getPrices();
		//size will be 252
		int size = list.size();
		//get the last day price of the path, start with 0 , so (size-1) would be the end of the path
		double price = (list.get(size-1)).getValue();
		// if the price is above 165, the payout is S(T) - 165
		// else the payout is 0
		return (price > 165.0)? price - 165.0 : 0;
	}
	
	//the method below has one more string type parameter, and is used to calculate the Asian option price
	//so the difference from above is to calculate the average price of the path
	public double getPayout(StockPath path,String type){
		List<Pair<DateTime, Double>> list = path.getPrices();
		double sum = 0;
		// get the sum price of the path
		int size = list.size();
		for(int i = 0; i < size ; i++){
			double price = (list.get(i)).getValue();
			sum += price;
		}
		// divide by size will get the average
		double price = sum / size;
		// if the price is above 165, the payout is S(T) - 165
		// else the payout is 0
		return (price > 165.0)? price - 165.0 : 0;
	}
}
