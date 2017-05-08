package br.edu.iftm.models.stacks.flashback;

import org.newdawn.slick.geom.Point;

/**
 * Contem as informacoes do heroi: id da sprite, direcao, posicao atual e velocidade
 *
 */
public class CharacterBackup {
	private int spriteOffX = 0;
	private int dir;
	private Point pos;
	private float speed;
	
	/** Constroi com as informacoes da id da sprite, direcao, posicao e velocidade.
	 * @param spriteOffX Id da sprite do heroi
	 * @param dir Direcao do heroi
	 * @param pos Posicao do heroi nesse momento
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
