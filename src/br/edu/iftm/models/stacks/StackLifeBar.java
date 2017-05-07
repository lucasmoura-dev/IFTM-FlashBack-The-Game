package br.edu.iftm.models.stacks;

public class StackLifeBar extends Stack {
	private static final int lpUnit = 10;
	
	public StackLifeBar(int stackType, int hpMax) {
		super(stackType, hpMax / lpUnit);
	}
	
	public StackLifeBar(int stackType, LifePointElement lastElement, int hpMax) // Action, LifePoint
	{
		super(stackType, lastElement, hpMax / lpUnit);
	}
	
	public StackLifeBar(int stackType, int hpMax, boolean fillHp) // Action, LifePoint
	{
		this(stackType, hpMax);
		if(fillHp)
			fillHp();
	}
	
	public void fillHp()
	{
		addLifePoint(sizeMax/* / lpUnit*/);
	}
	
	public void addLifePoint(int amount)
	{
		for(int i=0; i < amount && !isFull(); i++)
		{
			LifePointElement newLP = new LifePointElement();
			try {
				push(newLP);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void removeLifePoint(int amount) throws Exception
	{
		for(int i=0; i < amount; i++)
		{
			pop();
		}
	}

	public int getHpMax() {
		return sizeMax * lpUnit;
	}

	public void setHpMax(int hpMax) {
		sizeMax = hpMax / lpUnit;
	}

	public int getHp() {
		return getSize() * lpUnit;
	}

}
