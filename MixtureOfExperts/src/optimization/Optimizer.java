package optimization;

import data.DataContainer;
import em.EM;

public abstract class Optimizer
{
	/**
	 * Optimizes the alphas and the betas in place.
	 * @param labels The observed labels.
	 * @param alpha The expertise of each labeler.
	 * @param beta The difficulty of each instance.
	 * @param trueLabels The current labels.
	 * @param numberOfLabels The number of possible labels.
	 */
	public abstract void optimize(DataContainer labels, double[] alpha, double[] beta, int[] trueLabels, int numberOfLabels);
	
	/**
	 * Takes the derivative of Q with respect to the alphas.
	 * @param labels The observed labels.
	 * @param alpha The expertise of the labelers.
	 * @param beta The difficult of the instance.
	 * @param trueLabels The current guessed labels.
	 * @param numberOfLabels The number of possible labels.
	 * @return The derivatives with respect to the alphas.
	 */
	protected static double[] alphaDerivative(DataContainer labels, double[] alpha, double[] beta, int[] trueLabels, int numberOfLabels, double[] probabilityOfZ)
	{
		// precompute the probability of z
		//double[] probabilityOfZ = EM.probabilityOfZ(trueLabels, numberOfLabels);
		
		double[] derivatives = new double[alpha.length];
		
		for (int index = 0; index < alpha.length; index++)
		{
			double derivative = 0;
			for (int j = 0; j < beta.length; j++)
			{
				for (int k = 0; k < numberOfLabels; k++)
				{
					derivative += probabilityOfZ[k] 
							* ((EM.kronecker(labels.get(index, j), trueLabels[k]) - EM.sigmoid(alpha[index] * beta[j])) * beta[j]
							+ (1 - EM.kronecker(labels.get(index, j), k)) * Math.log(numberOfLabels - 1));
				}
			}
			
			derivatives[index] = derivative;
		}
		
		return derivatives;
	}
	
	/**
	 * Takes the derivative of Q with respect to the betas.
	 * @param labels The observed labels.
	 * @param alpha The expertise of the labelers.
	 * @param beta The difficulty of each instance.
	 * @param trueLabels The current labels.
	 * @param numberOfLabels Then number of possible labels.
	 * @return The derivative with respect to the betas.
	 */
	protected static double[] betaDerivative(DataContainer labels, double[] alpha, double[] beta, int[] trueLabels, int numberOfLabels, double[] probabilityOfZ)
	{
		// precompute the probability of z
		// double[] probabilityOfZ = EM.probabilityOfZ(trueLabels, numberOfLabels);
		
		double[] derivatives = new double[beta.length];
		
		for (int j = 0; j < beta.length; j++)
		{
			double derivative = 0;
			for (int i = 0; i < alpha.length; i++)
			{
				for (int k = 0; k < numberOfLabels; k++)
				{
					derivative += probabilityOfZ[k] *
							((EM.kronecker(labels.get(i, j), k) - EM.sigmoid(alpha[i] * beta[j])) * alpha[i]
							+ (1 - EM.kronecker(labels.get(i, j), k)) * Math.log(numberOfLabels - 1));
				}
			}
			
			derivatives[j] = derivative;
		}
		
		return derivatives;
	}
}
