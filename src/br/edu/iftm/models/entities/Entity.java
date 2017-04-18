package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

public abstract class Entity {
	protected float speed;
	protected Point position;
	protected int dir;
	protected Image image;
	
	abstract boolean move(float movX, float movY);
	
	public Entity(Point position, Image image, int dir)
	{
		this.position = position;
		this.image = image;
		this.dir = dir;
	}
	
	public Entity(Point position, int dir)
	{
		this.position = position;
		this.dir = dir;
	}
	
	public void draw()
	{
		image.draw();
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	public int getDir()
	{
		return dir;
	}
	
	public void setDir(int dir)
	{
		this.dir = dir;
	}
	
	public int getX()
	{
		return (int)position.getX();
	}
	
	public int getY()
	{
		return (int)position.getY();
	}
	
}
