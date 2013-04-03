package driver;
import data.DataContainer;
import data.DataUtils;
import em.EM;


public class Main
{
	// the data input file
	private static final String INPUT_FILE = "data/omar_data";
	
	// the number of times to do EM
	private static final int ITERATIONS = 10;
	
	// the labels given by expert i to instance j
	private static DataContainer labels;
	
	// the expertise of each labeler
	private static double alpha[];
	
	// the difficulty of each instance
	private static double beta[];
	
	// the true labels for the data
	private static int trueLabels[];
	
	public static void main(String[] args)
	{
		// read in the data
		Main.labels = DataUtils.loadData(Main.INPUT_FILE);
		
		// initialize the parameters
		Main.alpha = DataUtils.initializeAlpha(Main.labels.getNumberOfExperts());
		Main.beta = DataUtils.initializeBeta(Main.labels.getNumberOfInstances());
		Main.trueLabels = DataUtils.initializeTrueLabels(Main.labels.getNumberOfLabels());
		
		// do EM
		for (int i = 0; i < Main.ITERATIONS; i++)
		{
			// TODO what is Main.trueLabels????
			EM.performExpectationStep(Main.labels, Main.alpha, Main.beta, Main.trueLabels);
			EM.performMaximizationStep(Main.labels, Main.alpha, Main.beta, Main.trueLabels);
			System.out.println("Finished step " + i);
		}
		
	}
}
