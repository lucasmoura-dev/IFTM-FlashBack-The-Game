package br.edu.iftm.models;

import org.newdawn.slick.geom.Point;

import br.edu.iftm.models.entities.Character;
import br.edu.iftm.models.entities.LifeBar;
import br.edu.iftm.models.stacks.StackLifeBar;
import br.edu.iftm.models.stacks.flashback.ActionElement;
import br.edu.iftm.models.stacks.flashback.StackFlashback;

public class FlashBackSkill {
	private StackFlashback stack1, stack2;
	private Character hero;
	private LifeBar lifebar;
	private int cont = 0, contMax = 200;
	private int maxActionsReturn;
	
	public FlashBackSkill(int stackType, Character hero, LifeBar lifebar)
	{
		stack1 = new StackFlashback(stackType);
		stack2 = new StackFlashback(stackType);
		this.hero = hero;
		this.lifebar = lifebar;
	}
	
	public void addBackup(int hp)
	{
		stack1.addCharBackup(hp);
	}
	
	public void addBackup(Character character)
	{
		stack1.addCharBackup(character.getSpriteOffX(), character.getDir(), new Point(character.getX(), character.getY()), character.getSpeed());
	}
	
	public void use(int contMax)
	{
		cont = 0;
		this.contMax = contMax;
		
		restore();
	}
	
	public boolean restore()
	{
		if(cont >= contMax || stack1.isEmpty())
			return false;
		
		try {
			ActionElement actionElement = (ActionElement) stack1.pop();
			System.out.println("[sizeStack="+stack1.getSize()+"]" + actionElement);
			if(actionElement.isCharBackup())
				restoreChar(actionElement);
			else
				restoreHp(actionElement);
			cont++;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void restoreChar(ActionElement actionElement)
	{
		try {
			hero.setX(actionElement.getX());
			hero.setY(actionElement.getY());
			hero.setSpriteOffX(actionElement.getSpriteOffX());
			//hero.setSpeed(actionElement.getSpeed());
			hero.setDir(actionElement.getDir());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void restoreHp(ActionElement actionElement)
	{
		try {
			int diff = actionElement.getHp() - lifebar.getHp() + 10;
			lifebar.addHp(diff/StackLifeBar.LP_UNIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
