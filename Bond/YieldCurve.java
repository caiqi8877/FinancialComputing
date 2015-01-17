package edu.nyu.cims.compfin14.hw2;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
/**
 * 
 * @YieldCurve object: it has two member values, one is a map containing the <time,rate> key-value pair, 
 * 				one the 'prev' just act as a global variable for getInterestRate() method using
 * 				 one construct method which takes no parameters
 *               one construct method which takes a list of bonds as parameters
 *               five member methods as required
 */
public class YieldCurve {
// map yc containing the <time,rate> key-value pair
	Map<Double,Double> yc= new LinkedHashMap<Double,Double> ();
	// only used in getInterestRate()
	double prev = 0.0;
/**
 *  construct method which takes no parameters	
 */
	public YieldCurve(){
		yc.put(1.0, 0.02);
		yc.put(2.0, 0.023);
		yc.put(3.0, 0.03);
	}
/**
 * 	construct method which takes a list of bonds as parameters
 * @param bond: type ( list of Bond )
 */
	public YieldCurve(List<Bond> bond){
		// using in class method to compute the rate, using bond's member Maturity to get the time
		// then put the <time,rage> pair in the yield curve map
		for(Bond b:bond){
			yc.put(b.Maturity,getYTM(b,b.Price)); 
		}
	}
/**
 * 
 * @param time: a given time
 * @return the corresponding interest rate of that give time 
 */
	public double getInterestRate(double time){
		// iterate through the map, using 'first' to see whether the time given is smaller than the first time in the map
		boolean first = true;
		for(double t:yc.keySet()){
			if(time <= t && first) {
				// if it does (time < earliest time in map) , simply return the rate of the earliest time in the map
				return yc.get(t);
			}else if(time == t){
				// if the given time is already exists in the map, then return the corresponding rate
				return yc.get(t);
			}else if(time < t){
				// if the given time is between two records in the map, then using 2-dimention line properites to compute the rate
				double y1 = yc.get(prev);
				double y2 = yc.get(t);
				return y1 + (y2 - y1) * (time - prev) / (t - prev);
			}
			// prev used to record the previous record, so could see the  given time lies between which two record
			// as the iterate get one record a time
			prev = t;
			first = false;
		}
		// if the given time is so large that it is even larger the the latest time in the map, then return the rate of the latest time
		return yc.get(prev);
	}
/**
 * 	
 * @param t0 start date
 * @param t1 end date
 * @return the forward rate between two dates in continues compounding 
 */
	public double getForwardRate(double t0, double t1)
	{
		double r0 = getInterestRate(t0);
		double r1 = getInterestRate(t1);
		return ( r1 * t1 ) / ( r0 * t0 * (t1 - t0) );
	}
/**
 * 
 * @param t: the bond's duration
 * @return the discount factor for a given duration
 */
	public double getDiscountFactor(double t){
		double r0 = getInterestRate(t);
		return Math.pow(Math.E, r0 * t);
	}
/**
 * implement toString()
 * using StringBuilder
 * return the object(yield curve) information such as time periods and corresponding rates and the class name
 * 
 */
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		// NEW_LINE is similar to "\n" , used to start a new line
		String NEW_LINE = System.getProperty("line.separator");
		// this.getClass().getSimpleName() should return the class name : YieldCurve
		result.append(this.getClass().getSimpleName() + " Object {" + NEW_LINE);
		for(double t:yc.keySet()){
		// convert every key-value pair in the yc map into a string and put it in the result
			result.append("time:" + String.valueOf(t) + " | rate: " + String.valueOf(yc.get(t)) + NEW_LINE);
		}
		result.append("}");
		return String.valueOf(result);
	}
/**
 * 
 * @param bond: object created in other class
 * @param price: the fair price of the bond
 * @return
 */
	public double getYTM(Bond bond, double price){
		return Math.log(bond.FaceValue / price) / bond.Maturity ;
	}
}
