package br.edu.iftm.models.stacks.flashback;

import br.edu.iftm.models.stacks.DataElement;

public class ActionElement extends DataElement{
	public static final int TYPE_HERO_MOVS = 0, TYPE_HERO_HP = 1;
	private int type;
	private CharacterBackup charBackup; // salva a pos, dir, image, speed
	private int hp; // salva o hp
	// movimento heroi
	// barra de vida
	
	public ActionElement(CharacterBackup hero)
	{
		this.charBackup = hero;
		type = TYPE_HERO_MOVS;
	}
	
	public ActionElement(int hp)
	{
		this.hp = hp;
		type = TYPE_HERO_HP;
	}
	
	public int getHp() throws Exception
	{
		if(type != TYPE_HERO_HP)
			throw new Exception("The action element isn't the type TYPE_HERO_HP");
		return hp;
	}
	
	public int getDir() throws Exception
	{
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return charBackup.getDir();
	}
	
	public int getX() throws Exception
	{
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return charBackup.getX();
	}
	
	public int getY() throws Exception
	{
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return charBackup.getY();
	}
	
	public float getSpeed() throws Exception
	{
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return charBackup.getSpeed();
	}
	
	public int getSpriteOffX() throws Exception {
		if(type != TYPE_HERO_MOVS)
			throw new Exception("The action element isn't the type TYPE_HERO_MOVS");
		return charBackup.getSpriteOffX();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public boolean isCharBackup()
	{
		return type == TYPE_HERO_MOVS;
	}
	
	public boolean isHpBackup()
	{
		return type == TYPE_HERO_HP;
	}
	
	public String toString()
	{
		if(isCharBackup())
			return charBackup.toString();
		else
			return "hp = " + hp;
	}
}
