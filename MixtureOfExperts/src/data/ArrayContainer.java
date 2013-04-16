package data;

import java.util.HashMap;

/**
 * This class is an implementation of a DataContainer using
 * a 2D array. 
 */
public class ArrayContainer implements DataContainer
{
	// the labels given by expert i to instance j
	private int[][] labels;
	private int numberOfLabels;
	private HashMap<String, Integer> expertIntegerMap;
	private HashMap<String, Integer> instanceIntegerMap;
	private HashMap<String, Integer> labelIntegerMap;
	
	public ArrayContainer(int numberOfExperts, int numberOfInstances, int numberOfLabels)
	{
		this.labels = new int[numberOfExperts][numberOfInstances];
		
		for (int i = 0; i < numberOfExperts; i++)
		{
			for (int j = 0; j < numberOfInstances; j++)
				this.labels[i][j] = -1;
		}
		
		this.numberOfLabels = numberOfLabels;
		this.expertIntegerMap = new HashMap<String, Integer>();
		this.instanceIntegerMap = new HashMap<String, Integer>();
		this.labelIntegerMap = new HashMap<String, Integer>();
	}
	
	public int get(int expert, int instance)
	{
		return this.labels[expert][instance];
	}

	public void set(int expert, int instance, int label)
	{
		this.labels[expert][instance] = label;
	}

	public int getNumberOfExperts()
	{
		return this.labels.length;
	}

	public int getNumberOfInstances()
	{
		return this.labels[0].length;
	}
	
	public int getNumberOfLabels()
	{
		return this.numberOfLabels;
	}

	@Override
	public String expertToString(int expert) throws Exception {
		for (String key : expertIntegerMap.keySet())
		{
			int val = expertIntegerMap.get(key);
			if (val == expert)
				return key;
		}
		throw new Exception("No string corresponds to expert key");
	}

	@Override
	public String instanceToString(int instance) throws Exception {
		for (String key : instanceIntegerMap.keySet())
		{
			int val = expertIntegerMap.get(key);
			if (val == instance)
				return key;
		}
		throw new Exception("No string corresponds to instance key");
	}

	@Override
	public String labelToString(int label) throws Exception {
		for (String key : labelIntegerMap.keySet())
		{
			int val = expertIntegerMap.get(key);
			if (val == label)
				return key;
		}
		throw new Exception("No string corresponds to label key");
	}

	@Override
	public int expertToInteger(String expert) {
		Integer intVal = expertIntegerMap.get(expert);
		if (intVal != null)
			return intVal;
		else
		{
			intVal = expertIntegerMap.size();
			expertIntegerMap.put(expert, intVal);
			return intVal;
		}
	}

	@Override
	public int instanceToInteger(String instance) {
		Integer intVal = instanceIntegerMap.get(instance);
		if (intVal != null)
			return intVal;
		else
		{
			intVal = instanceIntegerMap.size();
			instanceIntegerMap.put(instance, intVal);
			return intVal;
		}
	}

	@Override
	public int labelToInteger(String label) {
		Integer intVal = labelIntegerMap.get(label);
		if (intVal != null)
			return intVal;
		else
		{
			intVal = labelIntegerMap.size();
			labelIntegerMap.put(label, intVal);
			return intVal;
		}
	}
}
