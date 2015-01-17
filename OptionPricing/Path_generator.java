package OptionPricing;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;

public class Path_generator implements StockPath{
	
	double[] vector = new double[252];
	
	//constructor using parameter vector to initialize the filed " double[] vector "(above) which will be used in 
	// below function getPrices()
	public Path_generator(double[] vector){
		for(int i = 0; i < vector.length; i++){
			this.vector[i] = vector[i];
			}
	}
	
	public List<Pair<DateTime, Double>> getPrices(){
		
		// using joda library to create DateTime object
		DateTime dt = new DateTime();
		
		// using joda library to create List<pair> container
		List<Pair<DateTime,Double>> list = new ArrayList<Pair<DateTime,Double>>();
		
		//calculate second day's option price using the first day's price and iterate until the 252's day
		double lastday_price = 152.35;
		double today_price;
		
		for(int day = 0; day < 252 ; day++){
			// this_day is today's option price
			DateTime this_day = dt.plusDays(day+1);
			// using formula given in the ppt
			today_price = lastday_price * Math.pow(Math.E, (0.0001 - 0.01*0.01/2 + 0.01 * vector[day]));
			// add today's price to the path
			list.add(new Pair<DateTime,Double>(this_day,today_price));
			// used to calculate the next iteration
			lastday_price = today_price;
		}
		return list;
	};
	
}
