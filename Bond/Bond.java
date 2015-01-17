package edu.nyu.cims.compfin14.hw2;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * 
 * @Bond object: it has four member values, which is the nature of the bond
 *               one construct method which is used to initizlize the bond
 *               five member methods as required
 */
public class Bond {
// four members
	double Price;
	double CouponRate;
	double Maturity;
	double FaceValue;
	
// member methods:
/**
 * 	
 * @param s:  using a string ( containing bond info ) to initialize a bond : in the format of "price coupone maturity facevalue"
 */
	public Bond(String s){
		String[] string = s.split(" ");
		this.Price = Double.parseDouble(string[0]);
		this.CouponRate = Double.parseDouble(string[1]);
		this.Maturity = Double.parseDouble(string[2]);
		this.FaceValue = Double.parseDouble(string[3]);
	}
	
	public double getPrice(){
		return Price;
	}
// CouponRate * FaceValue is the annual coupon payment, plus 0.5 is the semi-annual value
	public double getCoupon(){
		return CouponRate * FaceValue * 0.5;
	}
	public double getMaturity(){
		return Maturity;
	}
	public double getFaceValue(){
		return FaceValue;
	}

	
// For simplicity we assume coupon is only semi-annual payed
// using a hashmap data structure to store the cash flow
	public Map<Double,Double> getCashFlow(){
		Map<Double,Double> cashflow = new LinkedHashMap<Double,Double>();
		// use the class's own method get the coupon, semi-annually computed
		double coupon = this.getCoupon();
		// store the current time cash flow, it is negative because you pay the money to buy the bond
		cashflow.put(0.0 , -1 * Price);
		// semi-annually computed that is why 'time' is set to 0.5 initially, then put the <time,coupon> key-value pair in the map
		for(double time = 0.5; time < Maturity ; time += 0.5){
			cashflow.put(time, coupon);
		}
		// When maturity, the bond owner should get face value plus coupon from the issuer 
		cashflow.put(Maturity,FaceValue + coupon);
		return cashflow;
	}
}
