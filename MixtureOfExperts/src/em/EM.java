package em;
import java.util.Arrays;

import optimization.GradientDescent;
import optimization.Optimizer;
import data.DataContainer;

/**
 * This class is a utility class which helps to perform the
 * steps of the Expectation-Maximization algorithm.
 */
public class EM
{
	// the optimizer to do the M step
	private static Optimizer optimizer = new GradientDescent();
	
	/**
	 * Performs the expectation step of EM in place.
	 * @param labels The observed labels.
	 * @param alpha The expertise of each labeler.
	 * @param beta The difficulty of each instance.
	 * @param trueLabels The currently proposed true labels.
	 */
	public static void performExpectationStep(DataContainer labels, double[] alpha, double[] beta, int[] trueLabels)
	{
		System.out.println("Don't do that");
		System.exit(0);
	}
	
	/**
	 * Performs the maximization step of EM in place.
	 * @param labels The observed labels.
	 * @param alpha The expertise of each labeler.
	 * @param beta The difficulty of each instance.
	 * @param trueLabels The currently proposed true labels.
	 */
	public static void performMaximizationStep(DataContainer labels, double[] alpha, double[] beta,int numberOfLabels)
	{
		double[][] posterior = EM.posterior(labels, alpha, beta, numberOfLabels);

		System.out.println("posterior: " + Arrays.deepToString(posterior));
		
		EM.optimizer.optimize(labels, alpha, beta, posterior, labels.getNumberOfLabels());
		
		System.out.println("Liklihood: " + likelihoodOfData(labels,posterior,alpha,beta));
	}
	
	/**
	 * Computes P(Z), the distribution over the labels.
	 * @param labels The current labels.
	 * @return The distribution.
	 */
	public static double prior(int numberOfLabels)
	{
		return 1.0 / numberOfLabels;
	}
	
	/**
	 * Calculates the posterior, P(z_j | L_j, alpha, beta)
	 * @param labels
	 * @param alpha
	 * @param beta
	 * @param trueLabels
	 * @param numberOfLabels
	 * @return
	 */
	public static double[][] posterior(DataContainer labels, double[] alpha, double[] beta, int numberOfLabels)
	{
		// soon to be posterior
		double[][] posterior = new double[beta.length][numberOfLabels];
		
		for (int j = 0; j < beta.length; j++)
		{
			
			for (int k = 0; k < numberOfLabels; ++k) {
			// for all labelers
				double product = 1;
				for (int i = 0; i < alpha.length; i++) 
				{
					if (labels.get(i, j) != -1)
					{
						product *= EM.likelihood(labels.get(i,j), k, alpha[i], beta[j], numberOfLabels);
					}
				}
				posterior[j][k] = product * prior(numberOfLabels);
			}
		}
		
		return posterior;
	}
	
	/**
	 * Calculates P(L_ij = Z_j | alpha_i, beta_j).
	 * @param label The guessed label of the expert.
	 * @param trueLabel The true label of the instance.
	 * @param alpha The expertise of labeler i.
	 * @param beta The difficult of instance j.
	 * @param numberOfLabels The number of possible labels that can be given to an instance.
	 * @return The probability.
	 */
	private static double likelihood(int observedLabel, int k, double alpha, double beta, int numberOfLabels)
	{
		if (k == observedLabel)
			return EM.sigmoid(alpha * beta);
		else
			return (1.0 / (numberOfLabels - 1)) * (1 - EM.sigmoid(alpha * beta));
	}
	
	public static double likelihoodOfData(DataContainer labels, double[][] posterior_z, double[] alpha, double beta[]) {
		double prob = 0.0;
		
		int[] decodedLabels = decode(posterior_z);
		for (int i = 0; i < labels.getNumberOfExperts(); ++i) {
			for (int j = 0; j < labels.getNumberOfInstances(); ++j) {
				if (labels.get(i,j) != -1) {
					System.out.print(i + "\t" + j + "\t" + decodedLabels[j] + "\t");
					System.out.println(EM.likelihood(labels.get(i,j), decodedLabels[j], alpha[i], beta[j], labels.getNumberOfLabels()));
					prob += Math.log(EM.likelihood(labels.get(i,j), decodedLabels[j], alpha[i], beta[j], labels.getNumberOfLabels()));
				}
			}
		}
		
		
		return prob;
	}
	
	public static int[] decode(double[][] posterior_z) {
		int[] z = new int[posterior_z.length];
		for (int j = 0; j < posterior_z.length; ++j) {
			double maximum = 0.0;
			for (int k = 0; k < posterior_z[0].length; ++k) {
				if (posterior_z[j][k] > maximum) {
					maximum = posterior_z[j][k];
					z[j] = k;
				}
			}
		}
		
		return z;
	}
	
	
	/**
	 * Computes the sigmoid function at the given parameter.
	 * @param z The parameter.
	 * @return The value.
	 */
	public static double sigmoid(double z)
	{
		return (1 / (1 + Math.exp(-z)));
	}
	
	/**
	 * Computes the Kronecker function, which indicates whether the two
	 * parameter values are the same.
	 * @param i The first value.
	 * @param j The second value.
	 * @return 1 if they are the same, 0 if they are different.
	 */
	public static int kronecker(int i, int j)
	{
		if (i == j)
			return 1;
		return 0;
	}
}
