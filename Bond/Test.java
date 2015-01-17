package edu.nyu.cims.compfin14.hw2;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;


public class Test {
/**
 * 	
 * @param bond: object created in other class
 * @param ytm: double type is the time ( yield to maturity ) here we assume continuous computing
 * @return the bond's fair price
 */
	public static double getPrice(Bond bond, double ytm){
		return bond.FaceValue / Math.pow(Math.E, ytm * bond.Maturity);
	}
/**
 * 	
 * @param bond: object created in other class
 * @param price: the bond fair price or present value
 * @return the yield to maturity
 */
	public static double getYTM(Bond bond, double price){
		return Math.log(bond.FaceValue / price) / bond.Maturity ;
	}
/**
 * 	
 * @param yc: yieldcurve
 * @param bond: object created in other class
 * @return the bond's fair price
 */
	public static double getPrice(YieldCurve yc, Bond bond){
		Map<Double,Double> cashflow = bond.getCashFlow();
		double sum = 0.0;
		// using Bond's method cash flow and YieldCurve's method getDiscountFactor to calculate the present value of the cash flow
		// 'time' is the bond maturity
		for(double time = 0.5; time <= bond.Maturity ; time += 0.5){
			sum += cashflow.get(time) / yc.getDiscountFactor(time);
		}
		return sum;
	}
	
	public static void main(String arg[]){
		
// test bond's 5 methods 
		
// using a string ( containing bond info ) to initialize a bond : in the format of "price coupone maturity facevalue"	
		Bond bond0 = new Bond("900 0.05 2 1000");
		System.out.print("coupon bearing bond:" + "  | price " + bond0.getPrice() +" | coupon  " + bond0.getCoupon() 
				         + " | maturity " + bond0.getMaturity() + " | facevalue " + bond0.getFaceValue() + " | \ncashflow:   ");
		
// test the cashflow method, creating new map see its contents : the cash flows
// 'time' is the key value in the map
		Map<Double,Double> cashflow0 = bond0.getCashFlow();
		for(double time:cashflow0.keySet()) System.out.print(time + "year: " + cashflow0.get(time) + " | ");

// above is a coupon bearing bond, below is a zero-coupon bond
// other aspects are the same
		Bond bond1 = new Bond("95 0 0.5 100");
		System.out.print("\n\nzero coupon bond:"  + "    | price " + bond1.getPrice() +" | coupon  " + bond1.getCoupon() 
				         + " | maturity " + bond1.getMaturity() + " | facevalue " + bond1.getFaceValue() + " | \ncashflow:   ");
		Map<Double,Double> cashflow1 = bond1.getCashFlow();
		for(double time:cashflow1.keySet()) System.out.print(time + "year: " + cashflow1.get(time) + " | ");
		
		System.out.println("\n");
		
// question 1: implement toString, below 'yc0' is the default yield curve, which is initialized without parameters	
		
		YieldCurve yc0 = new YieldCurve();
		System.out.println(yc0.toString() + "\n");

// question 2: instantiate a yield curve with a list of bonds print the yield curve
		Bond bond2 = new Bond("895 0 1 1000");
		
// bond1 previous initialized, 'bond' is a list of bonds(bond1 and bond2)		
		List<Bond> bond = new LinkedList<Bond>();
		bond.add(bond1);
		bond.add(bond2);
		YieldCurve yc = new YieldCurve(bond);
		
// print it
		System.out.println(yc.toString());
		
// print getInterestRate(0.75)	
		System.out.println("\ngetInterestRate(0.75) returns: " + yc.getInterestRate(0.75) + "\n");
//		
//		System.out.println(getPrice(yc,bond2));
		
//question3:
//		use your yield curve to price a 5% coupon bond with 500$ face value, which pays semi-annually.	
//           suppose this bond's maturity varies from 0.5 year to 5 year, using the question 2 yield curve
//		     we can suppose the bond's is previous pricing at wrong price, use the wrong price to create a bond object
//   		 then use method getPrice(YieldCurve yc, Bond bond) to get the fair price
		for(double maturity = 0.5 ; maturity <= 5.0 ; maturity += 0.5){
			String bond_info = "400 " + "0.05 " + String.valueOf(maturity) + " " + "500";
			Bond bond3 = new Bond(bond_info);
			double price = getPrice(yc, bond3);
			double YTM = getYTM(bond3,price);
			System.out.println("When maturity = " + maturity +": price = " + price + " YTM = " + YTM);
		}
	}
}
