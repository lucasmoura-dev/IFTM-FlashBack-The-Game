package br.edu.iftm.models.stacks;

public class Stack {
	protected DataElement lastElement;
	public static final int TYPE_DYNAMIC = 1, TYPE_STATIC = 2;
	private int size;
	protected int sizeMax;
	private int stackType;
	private DataElement[] staticStack;
	
	public Stack(int stackType, int sizeMax)
	{
		size = 0;
		this.stackType = stackType;
		this.sizeMax = sizeMax;
		lastElement = null;
		if(stackType == TYPE_STATIC)
			staticStack = new DataElement[sizeMax];
	}
	
	public Stack(int stackType, DataElement lastElement, int sizeMax) // Action, LifePoint
	{
		this(stackType, sizeMax);
		//this.lastElement = lastElement;
		//size++;
		try {
			push(lastElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void pushStaticStack(DataElement dataElement)
	{
		staticStack[size] = dataElement;
	}
	
	private void pushDynamicStack(DataElement dataElement)
	{
		dataElement.addPrevious(lastElement);
		lastElement = dataElement;
	}
	
	public void push(DataElement dataElement) throws Exception
	{
		if(isFull())
			throw new Exception("Stackk full");
		if(isDynamicStack())
			pushDynamicStack(dataElement);
		else
			pushStaticStack(dataElement);
		size++;
	}
	
	private DataElement popStaticStack()
	{
		DataElement returnElement = staticStack[size-1];
		staticStack[size-1] = null;
		return returnElement;
	}
	
	private DataElement popDynamicStack()
	{
		DataElement returnElement = lastElement;
		lastElement = lastElement.getPrevious();
		return returnElement;
	}
	
	public DataElement pop() throws Exception
	{
		if(isEmpty())
			throw new Exception("Empty stack");
		
		DataElement returnElement;
		if(isDynamicStack())
			returnElement = popDynamicStack();
		else
			returnElement = popStaticStack();
		size--;
		return returnElement;
	}
	
	private DataElement peekStaticStack()
	{
		return staticStack[size-1];
	}
	
	private DataElement peekDynamicStack()
	{
		return lastElement;
	}
	
	/** Return the top element of the stack **/
	public DataElement peek() throws Exception
	{
		if(isEmpty())
			throw new Exception("Empty stack");
		if(isDynamicStack())
			return peekDynamicStack();
		else
			return peekStaticStack();
	}
	
	public void clear()
	{
		while(!isEmpty())
		{
			try {
				pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isFull()
	{
		return size == sizeMax;
	}
	
	public int getSize()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		return (size == 0);
	}
	
	public boolean isDynamicStack()
	{
		return stackType == TYPE_DYNAMIC;
	}
	
	public boolean isStaticStack()
	{
		return stackType == TYPE_STATIC;
	}

	public int getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(int sizeMax) {
		this.sizeMax = sizeMax;
	}
	
	
}
