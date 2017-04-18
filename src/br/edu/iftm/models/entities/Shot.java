package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import br.edu.iftm.controllers.Window;

public class Shot extends Projectile{

	public Shot(Point heroPos, int dir) throws SlickException {
		super(heroPos, new Image("/images/shot.png"), dir);
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
	
}
