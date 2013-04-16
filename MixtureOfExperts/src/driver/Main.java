package driver;

import util.StopWatch;
import data.DataContainer;
import data.DataUtils;
import em.EM;


public class Main
{
	// the data input file
	private static final String INPUT_FILE = "data/omar_data_small";
	
	// the number of times to do EM
	private static final int ITERATIONS = 5;
	
	// the labels given by expert i to instance j
	private static DataContainer labels;
	
	// the expertise of each labeler
	private static double alpha[];
	
	// the difficulty of each instance
	private static double beta[];
	
	public static void main(String[] args)
	{
		System.out.println("\n\n\n-----------------------------------------------------------------\n Starting new run.");
		StopWatch fullProgram = new StopWatch();
		fullProgram.start();
		// read in the data
		Main.labels = DataUtils.loadData(Main.INPUT_FILE);
		
		// initialize the parameters
		Main.alpha = DataUtils.initializeAlpha(Main.labels.getNumberOfExperts());
		Main.beta = DataUtils.initializeBeta(Main.labels.getNumberOfInstances());
		//Main.z = DataUtils.initializeTrueLabels(Main.labels.getNumberOfLabels(), Main.beta.length);
		
		// do EM
		for (int i = 0; i < Main.ITERATIONS; i++)
		{
			StopWatch eStep = new StopWatch();
			eStep.start();
			//EM.performExpectationStep(Main.labels, Main.alpha, Main.beta, Main.z);
			System.out.println("E Step Time: " + eStep.stop()/1000.0);
			StopWatch mStep = new StopWatch();
			mStep.start();
			EM.performMaximizationStep(Main.labels, Main.alpha, Main.beta, Main.labels.getNumberOfLabels());
			System.out.println("M Step Time: " + mStep.stop()/1000.0);
			System.out.println("\n-----------Finished step " + i + "-----------\n");
		}
		System.out.println("Time: " + fullProgram.stop()/1000.0);
		
	}
}
