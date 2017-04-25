package br.edu.iftm.models.stacks;

public class Stack {
	protected DataElement lastElement;
	public static final int TYPE_DYNAMIC = 1, TYPE_STATIC = 2;
	private int size;
	private int stackType;
	
	public Stack(int stackType)
	{
		size = 0;
		this.stackType = stackType;
		lastElement = null;
	}
	
	public Stack(int stackType, DataElement lastElement) // Action, LifePoint
	{
		this(stackType);
		//this.lastElement = lastElement;
		//size++;
		push(lastElement);
	}
	
	public void push(DataElement dataElement)
	{
		dataElement.addPrevious(lastElement);
		lastElement = dataElement;
		size++;
	}
	
	public DataElement pop() throws Exception
	{
		if(isEmpty())
			throw new Exception("Empty stack");
		
		DataElement returnElement = lastElement;
		lastElement = lastElement.getPrevious();
		size--;
		return returnElement;
	}
	
	public DataElement peek() throws Exception
	{
		if(isEmpty())
			throw new Exception("Empty stack");
		return lastElement;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		return (size == 0);
	}
}
