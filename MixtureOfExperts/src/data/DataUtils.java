package data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;


/**
 * This class is simply a utility class to read in the
 * data from the input file and initialize the parameters.
 */
public class DataUtils
{
	/**
	 * Loads the observed labels that expert i gave to instance j.
	 * @param fileName The name of the input file.
	 * @return A 2D array representing the labels, -1 if expert i did
	 * not give a label to instance j.
	 */
	public static DataContainer loadData(String fileName)
	{
		// the data should be in the form: 
		// 		instance_id, {expert_id}, {label}
		// where {expert_id} is a list of expert IDs and {label} is the set of
		// the respective labels
		// the first line should also contain the number of experts and instances
		try
		{
			Scanner scanner = new Scanner(new FileReader(fileName));
			
			// get the number of experts and instances
			int[] counts = DataUtils.convertToIntegerArray(scanner.nextLine().split(" "));
			
			// initialize the DataContainer
			DataContainer container = new ArrayContainer(counts[0], counts[1], counts[2]);
			
			while (scanner.hasNextLine())
			{
				// the file should be tab separated
				String line = scanner.nextLine();
				
				// set the labels given in the line
				DataUtils.setLabels(line, container);
			}
			
			return container;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static double[] initializeAlpha(int numberOfInstances)
	{
		double[] alpha = new double[numberOfInstances];
		Random rand = new Random();
		for (int i = 0; i < alpha.length; ++i)
		{
			// generates a double in [-10, 10]
			alpha[i] = (rand.nextDouble() - 0.5)*20000.0;
			//System.out.print(alpha[i] + ", ");
		}
		//System.out.print("\n");
		return alpha;
	}
	
	public static double[] initializeBeta(int numberOfExperts)
	{
		double[] beta = new double[numberOfExperts];
		Random rand = new Random();
		for (int i = 0; i < beta.length; ++i)
		{
			// generates a double in [0, 10]
			beta[i] = rand.nextDouble() * 10000.0;
			//System.out.print(beta[i] + ", ");
		}
		//System.out.print("\n");
		return beta;
	}
	
	/**
	 * Converts an array of Strings into an array of Integers.
	 * @param array The array of Strings.
	 * @return The array of Integers.
	 */
	private static int[] convertToIntegerArray(String[] array)
	{
		int[] integers = new int[array.length];
		
		for (int index = 0; index < array.length; index++)
			integers[index] = Integer.parseInt(array[index]);
		
		return integers;
	}
	
	/**
	 * Takes a line from the input file and inputs all of the labels
	 * into the DataContainer.
	 * @param line The line in the format instance_id, {expert_id}, {label}
	 * @param container The DataContainer to put the data in.
	 */
	private static void setLabels(String line, DataContainer container)
	{
		//int[] integers = DataUtils.convertToIntegerArray(line.split("\\s"));
		
		String[] stringReps = line.split("\\s");
		
		int instance = container.instanceToInteger(stringReps[0]);
		
		int numberOfExamples = (stringReps.length - 1) / 2;
		for (int index = 1; index <= numberOfExamples; index++)
		{
			int expert = container.expertToInteger(stringReps[index]);
			int label = container.labelToInteger(stringReps[index + numberOfExamples]);
			
			container.set(expert, instance, label);
		}
	}

	public static int[] initializeTrueLabels(int numberOfLabels) {
		int[] labels = new int[numberOfLabels];
		Random rand = new Random();
		for (int i = 0; i < labels.length; ++i)
		{
			// generates a double in [0, 10]
			labels[i] = rand.nextInt(numberOfLabels);
		}
		return labels;
	}
}
