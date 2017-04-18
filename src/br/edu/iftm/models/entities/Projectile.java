package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import br.edu.iftm.controllers.Window;

public class Projectile extends Entity{
	protected Point origin, position;
	
	public Projectile(Point heroPos, Image image, int dir) {
		super(new Point(heroPos.getX()+20, heroPos.getY()), image, dir);
		origin = new Point(heroPos.getX()+20, heroPos.getY());
		position = new Point(heroPos.getX()+20, heroPos.getY()+20);
		speed = 0.8f;
		this.image = image;
		generateImageRotation();
	}

	@Override
	boolean move(float movX, float movY) {
		if(Window.isInside(new Point(position.getX() + movX, position.getY() + movY), image.getWidth(), image.getHeight()))
		{
			position.setX(position.getX() + movX);
			position.setY(position.getY() + movY);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public void draw()
	{
		image.draw(position.getX(), position.getY());
	}
	
	private void generateImageRotation()
	{
		switch(dir)
		{
			case Character.SPRITE_DOWN:
				image.rotate(180);
				break;
			case Character.SPRITE_LEFT:
				image.rotate(270);
				break;
			case Character.SPRITE_RIGHT:
				image.rotate(90);
				break;
		}
	}
	
	public boolean moveShot(int delta)
	{
		switch(dir)
		{
			case Character.SPRITE_UP:
				return move(0, -speed * delta);
			case Character.SPRITE_DOWN:
				return move(0, speed * delta);
			case Character.SPRITE_LEFT:
				return move(-speed * delta, 0);
			case Character.SPRITE_RIGHT:
				return move(speed * delta, 0);
		}
		return false;
	}

}
