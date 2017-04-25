package br.edu.iftm.models.stacks;

public abstract class DataElement {
	private DataElement previousElement;
	
	public DataElement()
	{
		previousElement = null;
	}
	
	public void addPrevious(DataElement next)
	{
		this.previousElement = next;
	}
	
	public DataElement getPrevious()
	{
		return previousElement;
	}
	
	public void clearPrevious()
	{
		previousElement = null;
	}

	
}
