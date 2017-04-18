package br.edu.iftm.models;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import br.edu.iftm.controllers.Window;
import br.edu.iftm.view.Game;

public class Tiro {
	private static final float SPEED = 0.8f;
	private Point origin, position;
	private Image image;
	private boolean isOver;
	private int dir, stepY = 38;
	
	public Tiro(Point heroPos, int dir)
	{
		origin = new Point(heroPos.getX()+20, heroPos.getY());
		position = new Point(heroPos.getX()+20, heroPos.getY()+20);
		//calculatePosOrigin(heroPos);
		isOver = false;
		this.dir = dir;
		try {
			generateImage();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	private void generateImage() throws SlickException
	{
		image = new Image("/images/shot.png");
		switch(dir)
		{
			case Personagem.SPRITE_DOWN:
				image.rotate(180);
				break;
			case Personagem.SPRITE_LEFT:
				image.rotate(270);
				break;
			case Personagem.SPRITE_RIGHT:
				image.rotate(90);
				break;
		}
	}
	
	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	public void draw()
	{
		image.draw(position.getX(), position.getY());
	}
	
	public boolean moveShot(int delta)
	{
		switch(dir)
		{
			case Personagem.SPRITE_UP:
				return move(0, -SPEED * delta);
			case Personagem.SPRITE_DOWN:
				return move(0, SPEED * delta);
			case Personagem.SPRITE_LEFT:
				return move(-SPEED * delta, 0);
			case Personagem.SPRITE_RIGHT:
				return move(SPEED * delta, 0);
		}
		return false;
	}
	
	private boolean move(float movX, float movY)
	{
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
