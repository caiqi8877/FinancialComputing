package OptionPricing;

import org.apache.commons.math3.distribution.TDistribution;

public class Simulation_Manager {
	public static void main(String[] arg){
		
		TDistribution dist = new TDistribution(252);
		// is 96% probability it falls in the confidence interval, then the right side upper bound is 
		// 1 - ( 1 - 96% ) / 2 = 98%
		double y = dist.inverseCumulativeProbability(0.98);
		
		// n is the number of times using different path 
		int n = 0;
		
		// Sigma,average are used to track the current calculation information
		// once Sigma is below some certain value, we decide to stop
		double sigma = 0;
		double square_sum = 0;
		double average = 0;
		Payout_calculator payout = new Payout_calculator();
		Stat_collector states = new Stat_collector();
		
		//for problem one:
		
		//because during the start phrase iteration , we might get a lot of 0 prices and the iteration might stop unexpectedly,
		//so we set condition while (n < 10000 ) so that it runs at least 10000 iterations
		//y is abased on the 96% condition and others from the formula from the ppt
		while(n < 10000 || y * sigma > 0.01 * Math.sqrt(n)){
			//n records the number of iteration
			n++;
			//get random vector
			RandomVector random_vector = new RandomVector();
			double[] vector = random_vector.getVector();
			
			// using vector the generator a new path
			// and using payout.getPayout(Path) function to get the payout 
			double price1 = payout.getPayout(new Path_generator(vector));
			
			//below tracks the up to now states like average and sigma
			average = states.getaverage(price1, n, average);
			square_sum = states.getsquare_sum(price1,n,square_sum);
			sigma = states.getsigma(average,square_sum);
			
			// the second time to calculate the price, use the anti Thetic decorator 
			// others the same
			n++;
			double[] nvector = Anti_Thetic_decorator.Anti_Thetic(vector);
			
			// using vector the generator a new path
			// and using payout.getPayout(Path) function to get the payout 
			double price2 = payout.getPayout(new Path_generator(nvector));
			
			//below tracks the up to now states like average and sigma
			average = states.getaverage(price2, n, average);
			square_sum = states.getsquare_sum(price2,n,square_sum);
			sigma = states.getsigma(average,square_sum);
		}
		
		// output the option's price
		// notice that because the might be millions of iteration, it might takes time as long as 10 minutes to get the result
		System.out.println("Problem's 1 option price: " + average);
		
		// for problem 2: calculate this Asian option
		// nearly no difference from above, just the number of parameters in the payout.getPayout() function is 
		// different from above, and this is used to calculate Asian option pay out 
		
		// these variables need to be zero-initialized in order to calculate another option's price
		// Sigma,average are used to track the current calculation information
		// once Sigma is below some certain value, we decide to stop
		n = 0;
		sigma = 0;
		square_sum = 0;
		average = 0;
		//used to pass a string parameters(any string is ok here) in the payout.getPayout() function
		String asian_option = "";
		
		//because during the start phrase iteration , we might get a lot of 0 prices and the iteration might stop unexpectedly,
		//so we set condition while (n < 10000 ) so that it runs at least 10000 iterations
		//y is abased on the 96% condition and others from the formula from the ppt
		while(n < 10000 || y * sigma > 0.01 * Math.sqrt(n)){
			n++;
			RandomVector random_vector = new RandomVector();
			double[] vector = random_vector.getVector();
			
			// below here is the difference 
			// using vector the generator a new path
			// and using payout.getPayout(Path,String) function to get the payout 
			double price1 = payout.getPayout(new Path_generator(vector),asian_option);
			
			//below tracks the up to now states like average and sigma
			average = states.getaverage(price1, n, average);
			square_sum = states.getsquare_sum(price1,n,square_sum);
			sigma = states.getsigma(average,square_sum);
			
			//another path:
			n++;
			double[] nvector = Anti_Thetic_decorator.Anti_Thetic(vector);
			
			// below here is the difference 
			double price2 = payout.getPayout(new Path_generator(nvector),asian_option);
			
			//below tracks the up to now states like average and sigma
			average = states.getaverage(price2, n, average);
			square_sum = states.getsquare_sum(price2,n,square_sum);
			sigma = states.getsigma(average,square_sum);
		}
		
		System.out.println("Problem's 2 option price: " + average);
	}
}
