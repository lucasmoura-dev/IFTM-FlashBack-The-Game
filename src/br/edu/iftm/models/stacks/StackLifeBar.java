package br.edu.iftm.models.stacks;

public class StackLifeBar extends Stack {
	public static final int LP_UNIT = 10;
	
	public StackLifeBar(int stackType, int hpMax) {
		super(stackType, hpMax / LP_UNIT);
	}
	
	public StackLifeBar(int stackType, LifePointElement lastElement, int hpMax) // Action, LifePoint
	{
		super(stackType, lastElement, hpMax / LP_UNIT);
	}
	
	public StackLifeBar(int stackType, int hpMax, boolean fillHp) // Action, LifePoint
	{
		this(stackType, hpMax);
		if(fillHp)
			fillHp();
	}
	
	public void fillHp()
	{
		addLifePoint(sizeMax);
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
		return sizeMax * LP_UNIT;
	}

	public void setHpMax(int hpMax) {
		sizeMax = hpMax / LP_UNIT;
	}

	public int getHp() {
		return getSize() * LP_UNIT;
	}

}
