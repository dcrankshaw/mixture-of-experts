package em;
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
		// precompute the probability of z
		double[] probabilityOfZ = EM.probabilityOfZ(trueLabels, labels.getNumberOfLabels());
		
		int[] newLabels = new int[trueLabels.length];
		
		for (int instance = 0; instance < trueLabels.length; instance++)
		{
			double maximumProbability = Double.NEGATIVE_INFINITY;
			int maximumLabel = -1;
			
			for (int label = 0; label < labels.getNumberOfLabels(); label++)
			{
				double probability = probabilityOfZ[label];
				for (int i = 0; i < alpha.length; i++)
				{
					// we require the expert to have labeled the instance
					if (labels.get(i, instance) != -1)
						probability *= EM.posterior(labels.get(i, instance), trueLabels[instance], alpha[i], beta[instance], labels.getNumberOfLabels());
				}
				
				if (probability > maximumProbability)
				{
					maximumProbability = probability;
					maximumLabel = label;
				}
			}
			
			newLabels[instance] = maximumLabel;
		}
		
		// update the assignments
		trueLabels = newLabels;
	}
	
	/**
	 * Performs the maximization step of EM in place.
	 * @param labels The observed labels.
	 * @param alpha The expertise of each labeler.
	 * @param beta The difficulty of each instance.
	 * @param trueLabels The currently proposed true labels.
	 */
	public static void performMaximizationStep(DataContainer labels, double[] alpha, double[] beta, int[] trueLabels)
	{
		EM.optimizer.optimize(labels, alpha, beta, trueLabels, labels.getNumberOfLabels());
	}
	
	/**
	 * Computes P(Z), the distribution over the labels.
	 * @param labels The current labels.
	 * @return The distribution.
	 */
	public static double[] probabilityOfZ(int[] trueLabels, int numberOfLabels)
	{
		double[] counts = new double[numberOfLabels];
		
		// count the occurrences
		for (int index = 0; index < trueLabels.length; index++)
			counts[trueLabels[index]]++;
		
		// normalize
		for (int index = 0; index < trueLabels.length; index++)
			counts[index] = counts[index] / trueLabels.length;
		
		return counts;
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
	private static double posterior(int label, int trueLabel, double alpha, double beta, int numberOfLabels)
	{
		if (label == trueLabel)
			return EM.sigmoid(alpha * beta);
		else
			return (1 / (numberOfLabels - 1)) * (1 - EM.sigmoid(alpha * beta));
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
