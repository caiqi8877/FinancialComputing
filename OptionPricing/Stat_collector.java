package OptionPricing;

public class Stat_collector {
	public double getsigma(double average, double square_sum){
		return Math.sqrt( square_sum - Math.pow(average,2));
	}
	public double getaverage(double price, int n, double average){
		return (average * ( n - 1) + price ) / (double)n;
	}
	public double getsquare_sum(double price, int n,double square_sum){
		return (square_sum * ( n - 1) + price * price ) / (double)n;
	}
}
