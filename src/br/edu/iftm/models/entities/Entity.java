package br.edu.iftm.models.entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

public abstract class Entity {
	protected float speed;
	protected Point position;
	protected int dir;
	protected Image image;
	
	// Retângulos para as colisões
	private Rectangle me, him;
	
	abstract boolean move(float movX, float movY);
	
	public Entity(Point position, Image image, int dir)
	{
		this.position = position;
		this.image = image;
		this.dir = dir;
		me = new Rectangle((int)this.getX(), (int)this.getY(), image.getWidth(), image.getHeight());
		him = new Rectangle(0, 0, 0, 0);
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
	
	public void setX(int x)
	{
		position.setX((int)x);
	}
	
	public void setY(int y)
	{
		position.setY((int)y);
	}
	
	 /**
	 * Check if this entity collised with another.
	 *
	 * @param other The other entity to check collision against
	 * @return True if the entities collide with each other
	 */
	public boolean collidesWith(Entity other)
	{
		me.setBounds((int)this.getX(), (int)this.getY(), image.getWidth(), image.getHeight());
		him.setBounds((int)other.getX(), (int)other.getY(), other.getImage().getWidth(), other.getImage().getHeight());
		return me.intersects(him);
	}
	
    /**
     * Notification that this entity collided with another.
     *
     * @param other The entity with which this entity collided.
     */
	public abstract void collidedWith(Entity other);

	public Image getImage() {
		return image;
		
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	
	
}
