package br.edu.iftm.models.stacks.flashback;

import org.newdawn.slick.geom.Point;

public class CharacterBackup {
	private int spriteOffX = 0;
	private int dir;
	private Point pos;
	private float speed;
	
	/**
	 * @param spriteOffX
	 * @param dir
	 * @param pos
	 */
	public CharacterBackup(int spriteOffX, int dir, Point pos, float speed) {
		this.spriteOffX = spriteOffX;
		this.dir = dir;
		this.pos = pos;
		this.speed = speed;
	}

	public int getSpriteOffX() {
		return spriteOffX;
	}

	public void setSpriteOffX(int spriteOffX) {
		this.spriteOffX = spriteOffX;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
	
	public int getX()
	{
		return (int)pos.getX();
	}
	
	public int getY()
	{
		return (int)pos.getY();
	}
	
	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public String toString()
	{
		return  "spriteOffX = " + spriteOffX + "; dir = " + dir + "; pos = (" + getX() + "," + getY() + ") speed = " + speed; 
	}
	
	
}
