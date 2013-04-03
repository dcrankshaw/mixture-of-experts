package optimization;

import java.util.Arrays;

import data.DataContainer;

/**
 * This class performs optimization on the alphas and betas using
 * gradient descent.
 */
public class GradientDescent extends Optimizer
{
	private static double STEP_SIZE = 1.0; 
	private static double EPSILON = 0.001;

	public void optimize(DataContainer labels, double[] alpha, double[] beta, int[] trueLabels, int numberOfLabels)
	{
		// figure out the new parameter values
		double[] newAlphas = this.optimizeAlphas(labels, alpha, beta, trueLabels, numberOfLabels);
		double[] newBetas = this.optimizeBetas(labels, alpha, beta, trueLabels, numberOfLabels);
		
		// update the values
		alpha = newAlphas;
		beta = newBetas;
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
	private double[] optimizeAlphas(DataContainer labels, double[] alpha, double[] beta, int[] trueLabels, int numberOfLabels)
	{
		double[] newAlphas = Arrays.copyOf(alpha, alpha.length);
		double[] gradient = Optimizer.alphaDerivative(labels, newAlphas, beta, trueLabels, numberOfLabels);
		
		//while (!this.hasConverged(gradient))
		for (int i = 0; i < 5; ++i)
		{
			this.takeStep(newAlphas, gradient);
			System.out.println("aaa");
			gradient = Optimizer.alphaDerivative(labels, newAlphas, beta, trueLabels, numberOfLabels);
			System.out.println("Finished step " + i + " for gradient descent alpha");
		}
		
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
	private double[] optimizeBetas(DataContainer labels, double[] alpha, double[] beta, int[] trueLabels, int numberOfLabels)
	{
		double[] newBetas = Arrays.copyOf(beta, beta.length);
		double[] gradient = Optimizer.betaDerivative(labels, alpha, newBetas, trueLabels, numberOfLabels);
		
		//while (!this.hasConverged(gradient))
		for (int i = 0; i < 5; ++i)
		{
			this.takeStep(newBetas, gradient);
			System.out.println("bbb");
			gradient = Optimizer.betaDerivative(labels, alpha, newBetas, trueLabels, numberOfLabels);
			System.out.println("Finished step " + i + " for gradient descent beta");
		}
		
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
			origin[i] = origin[i] - STEP_SIZE * gradient[i];
	}
	
	/**
	 * Determines whether or not the algorithm has converged by checking
	 * whether or not all values of the gradient are below an epsilon value.
	 * @param gradient The gradient.
	 * @return True if it has converged, false otherwise.
	 */
	private boolean hasConverged(double[] gradient)
	{
		for (int index = 0; index < gradient.length; index++)
		{
			if (gradient[index] > EPSILON)
				return false;
		}
		return true;
	}
}