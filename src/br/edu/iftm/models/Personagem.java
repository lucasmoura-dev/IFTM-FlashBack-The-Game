package br.edu.iftm.models;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import br.edu.iftm.view.Game;

public class Personagem {
	private static final float SPEED = 0.5f;
	private Point position;
	private int spriteOffX = 0, spriteOffY = 0;
	private static final int SPRITE_DOWN = 0, SPRITE_LEFT = 1, SPRITE_RIGHT = 2, SPRITE_UP = 3;
	private SpriteSheet ss;
	private int widthSprite, heightSprite;
	
	public Personagem(SpriteSheet ss, int x, int y)
	{
		this.ss = ss;
		position = new Point((float)x, (float)y);
		widthSprite = ss.getSprite(0, 0).getWidth() - 10;
		heightSprite = ss.getSprite(0, 0).getHeight() - 2;
	}
	
	public void draw()
	{
		//System.out.println("Mover para: " + position.getX() + "; " + position.getY());
		Image sprite = ss.getSprite(spriteOffX, spriteOffY);
		sprite.draw((int)position.getX(), (int)position.getY());
	}
	
	public void moveUp(int delta)
	{
		if(spriteOffY == SPRITE_UP)
		{
			spriteOffX++;
			if(spriteOffX >= 3) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			spriteOffY = 3;
		}
		
		move(0, -SPEED * delta);
	}
	
	public void moveDown(int delta)
	{
		if(spriteOffY == SPRITE_DOWN)
		{
			spriteOffX++;
			if(spriteOffX >= 3) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			spriteOffY = 0;
		}
		
		move(0, SPEED * delta);
	}
	
	public void moveRight(int delta)
	{
		if(spriteOffY == SPRITE_RIGHT)
		{
			spriteOffX++;
			if(spriteOffX >= 3) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			spriteOffY = 2;
		}
		
		move(SPEED * delta, 0);
	}
	
	public void moveLeft(int delta)
	{
		if(spriteOffY == SPRITE_LEFT)
		{
			spriteOffX++;
			if(spriteOffX >= 3) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			spriteOffY = 1;
		}
		
		move(-SPEED * delta, 0);
	}
	
	private void move(float movX, float movY)
	{
		if(position.getX() + movX >= 0 && position.getX() + movX < Game.WIDTH - widthSprite)
		{
			if(position.getY() + movY >= 0 && position.getY() + movY < Game.HEIGHT - heightSprite)
			{
				position.setX(position.getX() + movX);
				position.setY(position.getY() + movY);
			}
		}
	}
	
	public void jump(int delta)
	{
		switch(spriteOffY)
		{
			case SPRITE_UP:
				move(0, 10 * -SPEED * delta);
				break;
			case SPRITE_DOWN:
				
				break;
			case SPRITE_LEFT:
				
				break;
			case SPRITE_RIGHT:
				
				break;
		}
	}
	
}
