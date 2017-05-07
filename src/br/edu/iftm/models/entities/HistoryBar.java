package br.edu.iftm.models.entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class HistoryBar extends Entity{
	private ArrayList<Image> iconset;
	private int type = 1;
	
	public HistoryBar(Point position, Image image, int dir) {
		super(position, image, dir);
		iconset = new ArrayList<Image>();
	}
	
	public void removeIcon()
	{
		if(!iconset.isEmpty())
			iconset.remove(iconset.size()-1);
	}
	
	public void draw()
	{
		image.draw(position.getX(), position.getY()); // Draws the bar
		int posX = (int)position.getX() + 11;
		int posY = (int)position.getY() + image.getHeight() - 12;
		for(Image icon : iconset)
		{
			icon.draw(posX, posY);
			posY -= 12;
		}
	}
	
	public void addCharMovIcon()
	{
		try {
			if(iconset.size() == 50)
				removeFirst();
			
			if(type == 1)
				iconset.add(new Image("images/footsteps.png"));
			else
				iconset.add(new Image("images/footsteps2.png"));
			
			type *= -1;
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void addCharLostHp()
	{
		try {
			if(iconset.size() == 50)
				removeFirst();
			iconset.add(new Image("images/lost_hp.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void removeFirst()
	{
		if(!iconset.isEmpty())
			iconset.remove(0);
	}

	@Override
	boolean move(float movX, float movY) {
		return false;
	}

	@Override
	public void collidedWith(Entity other) {}
	
}
