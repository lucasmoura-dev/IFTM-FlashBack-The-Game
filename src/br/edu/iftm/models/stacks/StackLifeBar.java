package br.edu.iftm.models.stacks;

public class StackLifeBar extends Stack {
	private int hpMax;
	private final int lpUnit = 10;
	
	public StackLifeBar(int stackType, int hpMax) {
		super(stackType);
		this.hpMax = hpMax;
	}
	
	public StackLifeBar(int stackType, LifePointElement lastElement, int hpMax) // Action, LifePoint
	{
		super(stackType, lastElement);
		this.hpMax = hpMax;
	}
	
	public StackLifeBar(int stackType, int hpMax, boolean fillHp) // Action, LifePoint
	{
		this(stackType, hpMax);
		this.hpMax = hpMax;
		if(fillHp)
			fillHp();
	}
	
	public void fillHp()
	{
		addLifePoint(hpMax / lpUnit);
	}
	
	public void addLifePoint(int amount)
	{
		for(int i=0; i < amount && !isFullHp(); i++)
		{
			LifePointElement newLP = new LifePointElement();
			push(newLP);
		}
	}
	
	public void removeLifePoint(int amount) throws Exception
	{
		for(int i=0; i < amount; i++)
		{
			pop();
		}
	}
	
	
	public boolean isFullHp()
	{
		return (getHp() == hpMax);
	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	public int getHp() {
		return getSize() * 10;
	}

}
