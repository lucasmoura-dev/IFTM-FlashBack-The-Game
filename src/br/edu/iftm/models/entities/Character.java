package br.edu.iftm.models.entities;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

import br.edu.iftm.controllers.Window;
import br.edu.iftm.models.stacks.StackLifeBar;

public class Character extends Entity{
	public static final int SPRITE_DOWN = 0, SPRITE_LEFT = 1, SPRITE_RIGHT = 2, SPRITE_UP = 3;
	private int spriteOffX = 0;
	private int maxOffsetX = 3;
	private SpriteSheet ss;
	private int limWidthSprite, limHeightSprite;
	
	public Character(int x, int y, Image image, int dir) {
		super(new Point(x, y), image, dir);
		this.dir = dir;
	}
	

	public Character(int x, int y, SpriteSheet ss){
		super(new Point(x, y), 3);
		this.dir = 3;
		this.ss = ss;
		speed = 0.5f;
		limWidthSprite = 0;
		limHeightSprite = 0;
	}
	
	public Character(int x, int y, SpriteSheet ss, int dir){
		super(new Point(x, y), dir);
		this.ss = ss;
		this.dir = dir;
		speed = 0.5f;
		limWidthSprite = 0;
		limHeightSprite = 0;
	}
	

	public void draw()
	{		
		image = ss.getSprite(spriteOffX, dir);
		image.draw((int)position.getX(), (int)position.getY());
	}
	

	@Override
	boolean move(float movX, float movY)
	{
		if(Window.isInside(new Point(position.getX() + movX, position.getY() + movY),
				limWidthSprite, limHeightSprite))
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
	
	public void moveUp(int delta)
	{
		if(dir == SPRITE_UP)
		{
			spriteOffX++;
			if(spriteOffX >= maxOffsetX) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			dir = 3;
		}
		
		move(0, -speed * delta);
	}
	
	public void moveDown(int delta)
	{
		if(dir == SPRITE_DOWN)
		{
			spriteOffX++;
			if(spriteOffX >= maxOffsetX) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			dir = 0;
		}
		
		move(0, speed * delta);
	}
	
	public void moveRight(int delta)
	{
		if(dir == SPRITE_RIGHT)
		{
			spriteOffX++;
			if(spriteOffX >= maxOffsetX) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			dir = 2;
		}
		
		move(speed * delta, 0);
	}
	
	public void moveLeft(int delta)
	{
		if(dir == SPRITE_LEFT)
		{
			spriteOffX++;
			if(spriteOffX >= maxOffsetX) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			dir = 1;
		}
		
		move(-speed * delta, 0);
	}
	
	public void jump(int delta)
	{
		switch(dir)
		{
			case SPRITE_UP:
				move(0, 10 * -speed * delta);
				break;
			case SPRITE_DOWN:
				move(0, 10 * speed * delta);
				break;
			case SPRITE_LEFT:
				move(10 * -speed * delta, 0);
				break;
			case SPRITE_RIGHT:
				move(10 * speed * delta, 0);
				break;
		}
	}

	public int getLimWidthSprite() {
		return limWidthSprite;
	}

	public void defineLimWidthSprite(int limWidthSprite) {
		this.limWidthSprite = ss.getSprite(0, 0).getWidth() - limWidthSprite;
	}


	public void defineLimHeightSprite(int limHeightSprite) {
		this.limHeightSprite = ss.getSprite(0, 0).getHeight() - limHeightSprite;
	}

	public void setMaxOffsetX(int maxOffsetX) {
		this.maxOffsetX = maxOffsetX;
	}

	@Override
	public void collidedWith(Entity other){
		
	}
	

}
