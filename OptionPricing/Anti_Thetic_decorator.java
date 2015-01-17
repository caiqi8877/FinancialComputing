package OptionPricing;

public class Anti_Thetic_decorator {
	
	public static double[] Anti_Thetic(double[] vector){
		
		//create a new vector of the same length, opposite every value of it and 
		//return the new vector
		double[] new_vector = new double[vector.length];
		for(int i = 0; i < vector.length ; i++){
			new_vector[i] = -vector[i];
		}
		return new_vector;
	}
}
