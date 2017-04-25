package br.edu.iftm.models.stacks;

public class LifePointElement extends DataElement{
	private int value;
	
	public LifePointElement(int value)
	{
		this.value = value;
	}
	
	public LifePointElement()
	{
		value = 0;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}
