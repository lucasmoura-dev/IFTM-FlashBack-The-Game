package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import br.edu.iftm.models.stacks.Stack;
import br.edu.iftm.models.stacks.StackLifeBar;

public class LifeBar extends Entity{
	private Image blank;
	private StackLifeBar stackHp;
	private Point position;
	
	public LifeBar(Point position, Image image, int dir)
	{
		super(position, image, dir);
		try {
			blank = new Image("/images/bar_blank.png");
		} catch (SlickException e) {
			e.printStackTrace();
		} 
		stackHp = new StackLifeBar(Stack.TYPE_DYNAMIC, 160, true);
		this.position = position;
	}
	
	public LifeBar() throws SlickException
	{
		this(new Point(10, 10), new Image("/images/bar_full.png"), 0);
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
		float pc = getHp() / getHpMax();
		int height = (int)(blank.getHeight() * pc);
		blank.draw((int)position.getX()+7, (int)position.getY()+7, blank.getWidth(), height);
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
	
	public void removeHp(int amount)
	{
		try {
			stackHp.removeLifePoint(amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
