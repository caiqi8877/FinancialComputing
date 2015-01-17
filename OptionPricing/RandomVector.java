package OptionPricing;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.random.CorrelatedRandomVectorGenerator;
import org.apache.commons.math3.random.GaussianRandomGenerator;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.UncorrelatedRandomVectorGenerator;

public class RandomVector implements RandomVectorGenerator{
		
		public double[] getVector(){
		// Create and seed a RandomGenerator (could use any of the generators in the random package here)
		RandomGenerator rg = new JDKRandomGenerator();
		
		// don't fixed the seed so get the random vector
		
		// Create a GassianRandomGenerator using rg as its source of randomness
		GaussianRandomGenerator rawGenerator = new GaussianRandomGenerator(rg);
		
		// Create a CorrelatedRandomVectorGenerator using rawGenerator for the components
		UncorrelatedRandomVectorGenerator generator = new UncorrelatedRandomVectorGenerator(252, rawGenerator);

		// Use the generator to generate correlated vectors
		double[] randomVector = generator.nextVector();
		
		return randomVector;
		}	
}
