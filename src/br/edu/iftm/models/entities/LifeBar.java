package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import br.edu.iftm.models.stacks.StackLifeBar;

public class LifeBar extends Entity{
	private Image blank;
	private StackLifeBar stackHp;
	private Point position;
	
	public LifeBar(Point position, Image image, int dir, int stackType)
	{
		super(position, image, dir);
		try {
			blank = new Image("/images/bar_blank.png");
		} catch (SlickException e) {
			e.printStackTrace();
		} 
		stackHp = new StackLifeBar(stackType, 160, true);
		this.position = position;
	}
	
	public LifeBar(int stackType) throws SlickException
	{
		this(new Point(10, 10), new Image("/images/bar_full.png"), 0, stackType);
	}

	
	public void draw()
	{
		// Draw the background image of the life bar
		image.draw((int)position.getX(), (int)position.getY());
		drawBlank();
	}
	
	public void drawBlank()
	{
		// Draw the blank image, his height's the size of the HP
		float pcLife = (float)getHp() / (float)getHpMax();
		int amountLines = (int)(16 * pcLife);
		int newHeight = (int)blank.getHeight()-(blank.getHeight() * amountLines / 16);
		blank.draw((int)position.getX()+7, (int)position.getY()+7, blank.getWidth(), newHeight);
	}

	@Override
	boolean move(float movX, float movY)
	{
		return false;
	}
	
	public int getHp()
	{
		return stackHp.getHp();
	}
	
	public int getHpMax()
	{
		return stackHp.getHpMax();
	}
	
	public void addHp(int amount)
	{
		stackHp.addLifePoint(amount);
	}
	
	public boolean isEmpty()
	{
		return stackHp.isEmpty();
	}
	
	public void removeHp(int amount)
	{
		try {
			stackHp.removeLifePoint(amount);
		} catch (Exception e) {
			System.out.println("Morreu");;
		}
	}

	@Override
	public void collidedWith(Entity other) {}
}
