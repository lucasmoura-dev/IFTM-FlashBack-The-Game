package br.edu.iftm.models.stacks;

import org.newdawn.slick.Image;

import br.edu.iftm.models.entities.Entity;

public class ActionElement extends DataElement{
	public static final int TYPE_HERO_MOVS = 0, TYPE_HERO_HP = 1;
	private int type;
	private Entity entity; // salva a pos, dir, image, speed
	private int hp; // salva o hp
	// movimento heroi
	// barra de vida
	
	public ActionElement(Entity hero)
	{
		this.entity = hero;
		type = TYPE_HERO_MOVS;
	}
	
	public ActionElement(int hp)
	{
		this.hp = hp;
		type = TYPE_HERO_HP;
	}
	
	public int getHP() throws Exception
	{
		if(type != TYPE_HERO_HP)
			throw new Exception("The action element isn't the type TYPE_HERO_HP");
		return hp;
	}
	
	public int getDir() throws Exception
	{
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return entity.getDir();
	}
	
	public int getX() throws Exception
	{
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return entity.getX();
	}
	
	public int getY() throws Exception
	{
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return entity.getY();
	}
	
	public float getSpeed() throws Exception
	{
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return entity.getSpeed();
	}
	
	public Image getImage() throws Exception
	{
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return entity.getImage();
	}
	
	public Entity getEntity() throws Exception
	{
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return entity;
	}
}
