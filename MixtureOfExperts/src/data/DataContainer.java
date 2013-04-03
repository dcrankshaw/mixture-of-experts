package data;

/**
 * An interface to provide a layer of abstraction over how
 * we decided to store our data structures so we can swap
 * implementations in and out quickly.
 */
public interface DataContainer
{
	public int get(int expert, int instance);
	
	public void set(int expert, int instance, int label);
	
	public int getNumberOfExperts();
	
	public int getNumberOfInstances();
	
	public int getNumberOfLabels();
	
	public String expertToString(int expert) throws Exception;
	
	public String instanceToString(int instance) throws Exception;
	
	public String labelToString(int label) throws Exception;
	
	public int expertToInteger(String expert);
	
	public int instanceToInteger(String instance);
	
	public int labelToInteger(String label);
	
}
