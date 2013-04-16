package optimization;

import java.util.Arrays;

import data.DataContainer;

/**
 * This class performs optimization on the alphas and betas using
 * gradient descent.
 */
public class GradientDescent extends Optimizer
{
	private static double STEP_SIZE = .001; 
	private static double EPSILON = 0.01;

	public void optimize(DataContainer labels, double[] alpha, double[] beta, double[][] posterior, int numberOfLabels)
	{
		// figure out the new parameter values
		
		double[] tmpAlpha = alpha;
		double[] tmpBeta = beta;
		for (int i = 0; i < 1000000; ++i) {
			double[] tmptmpAlpha = this.optimizeAlphas(labels, tmpAlpha,tmpBeta, numberOfLabels, posterior);
			double[] tmptmpBeta = this.optimizeBetas(labels, tmpAlpha, tmpBeta, numberOfLabels, posterior);
			tmpAlpha = tmptmpAlpha;
			tmpBeta = tmptmpBeta;
			//System.out.println(Arrays.toString(tmpAlpha));
			//System.out.println(Arrays.toString(posterior));
			
		}
		System.out.println("DONE");
		System.exit(0);
		// update the values
		//for (int i = 0; i < alpha.length; i++)
	//		alpha[i] = newAlphas[i];
	//	for (int i = 0; i < beta.length; i++)
		//	beta[i] = newBetas[i];
		
	//	System.out.println("alphas: " + Arrays.toString(alpha));
	//	System.out.println("betas: " + Arrays.toString(beta));
	}
	
	/**
	 * Optimizes the alphas. This cannot be done in place because the old
	 * alphas need to be used to optimize the betas.
	 * @param labels The observed labels. 
	 * @param alpha The expertise of the labelers.
	 * @param beta The difficulty of the instances.
	 * @param trueLabels The current labels.
	 * @param numberOfLabels The number of possible labels.
	 * @return The new alphas.
	 */
	private double[] optimizeAlphas(DataContainer labels, double[] alpha, double[] beta, int numberOfLabels, double[][] posterior)
	{
		double[] newAlphas = Arrays.copyOf(alpha, alpha.length);
		double[] gradient = Optimizer.alphaDerivative(labels, newAlphas, beta, numberOfLabels, posterior);
//		while (!this.hasConverged(gradient))
		//for (int i = 0; i < ; i++)
		//{
			this.takeStep(newAlphas, gradient);
			//System.out.println("alphas: " + Arrays.toString(newAlphas));
			//gradient = Optimizer.alphaDerivative(labels, newAlphas, beta, numberOfLabels, posterior);
			System.out.println("gradient: " + Arrays.toString(gradient));
			
		//}
		
		return newAlphas;
	}

	/**
	 * Optimizes the betas. This cannot be done in place because the old
	 * betas need to be used to optimize the betas.
	 * @param labels The observed labels. 
	 * @param alpha The expertise of the labelers.
	 * @param beta The difficulty of the instances.
	 * @param trueLabels The current labels.
	 * @param numberOfLabels The number of possible labels.
	 * @return The new betas.
	 */
	private double[] optimizeBetas(DataContainer labels, double[] alpha, double[] beta, int numberOfLabels, double[][] posterior)
	{
		double[] newBetas = Arrays.copyOf(beta, beta.length);
		double[] gradient = Optimizer.betaDerivative(labels, alpha, newBetas, numberOfLabels, posterior);
		//while (!this.hasConverged(gradient))
		//{
			this.takeStep(newBetas, gradient);
			//gradient = Optimizer.betaDerivative(labels, alpha, newBetas, numberOfLabels, posterior);
		//}
		
		return newBetas;
	}
	
	/**
	 * Updates the current position to the new position given the gradient.
	 * @param origin The current position.
	 * @param gradient The gradient.
	 */
	private void takeStep(double[] origin, double[] gradient)
	{
		for (int i = 0; i < origin.length; i++)
			origin[i] = origin[i] + STEP_SIZE * gradient[i];
	}
	
	/**
	 * Determines whether or not the algorithm has converged by checking
	 * whether or not all values of the gradient are below an epsilon value.
	 * @param gradient The gradient.
	 * @return True if it has converged, false otherwise.
	 */
	private boolean hasConverged(double[] gradient)
	{
		boolean converged = true;
		for (int index = 0; index < gradient.length; index++)
		{
			if (gradient[index] > EPSILON) {
				converged = false;
			}
		}
		return converged;
	}
}
