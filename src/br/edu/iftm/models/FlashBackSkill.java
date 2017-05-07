package br.edu.iftm.models;

import org.newdawn.slick.geom.Point;

import br.edu.iftm.models.entities.Character;
import br.edu.iftm.models.entities.HistoryBar;
import br.edu.iftm.models.entities.LifeBar;
import br.edu.iftm.models.stacks.StackLifeBar;
import br.edu.iftm.models.stacks.flashback.ActionElement;
import br.edu.iftm.models.stacks.flashback.StackFlashback;

public class FlashBackSkill {
	private StackFlashback[] stacks;
	private Character hero;
	private LifeBar lifebar;
	private HistoryBar histBar;
	private int contActions, maxActionsReturn;
	private int stackActive, otherStack;

	public FlashBackSkill(int stackType, Character hero, LifeBar lifebar, HistoryBar histBar)
	{
		stacks = new StackFlashback[2];
		stacks[0] = new StackFlashback(stackType, 50);
		stacks[1] = new StackFlashback(stackType, 50);
		this.hero = hero;
		this.lifebar = lifebar;
		this.histBar = histBar;
		contActions = 0;
		stackActive = 0;
		otherStack = 1;
		maxActionsReturn = 50;
	}
	
	private void swapStackActive()
	{
		if(stackActive == 0)
		{
			stackActive = 1;
			otherStack = 0;
		}
		else
		{
			stackActive = 0;
			otherStack = 1;
		}
	}
	
	private void clearOrChangeStacks()
	{
		System.out.println("Stack #" + (stackActive+1) + " " + stacks[stackActive].getSize() + "/" + stacks[stackActive].getSizeMax() + "; maxActionsReturn = " + maxActionsReturn);
		
		if(stacks[stackActive].getSize() >= maxActionsReturn && !stacks[otherStack].isEmpty())
			stacks[otherStack].clear();
		
		if(stacks[stackActive].isFull())
			swapStackActive();
	}
	
	public void addBackup(int hp)
	{
		stacks[stackActive].addCharBackup(hp);
		histBar.addCharLostHp();
		clearOrChangeStacks();	
	}
	
	public void addBackup(Character character)
	{
		stacks[stackActive].addCharBackup(character.getSpriteOffX(), character.getDir(), new Point(character.getX(), character.getY()), character.getSpeed());
		histBar.addCharMovIcon();
		clearOrChangeStacks();
	}
	
	public void use()
	{
		contActions = 0;
	}
	
	public boolean restore()
	{
		if(contActions >= maxActionsReturn) return false;
		
		ActionElement actionElement;
		
		if(stacks[stackActive].isEmpty())
		{
			if(stacks[otherStack].isEmpty())
				return false;
			try {
				System.out.println("POP Stack #" + (otherStack+1) + " " + stacks[otherStack].getSize() + "/" + stacks[otherStack].getSizeMax() + "; maxActionsReturn = " + maxActionsReturn);
				actionElement = (ActionElement) stacks[otherStack].pop();
				histBar.removeIcon();
				swapStackActive(); // Stack Active is empty, but the other stack have a free slot
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		else
		{
			try {
				System.out.println("POP Stack #" + (stackActive+1) + " " + stacks[stackActive].getSize() + "/" + stacks[stackActive].getSizeMax() + "; maxActionsReturn = " + maxActionsReturn);
				actionElement = (ActionElement) stacks[stackActive].pop();
				histBar.removeIcon();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			
		}
		
		//System.out.println("[sizeStack="+stacks[stackActive].getSize()+"]" + actionElement);
		if(actionElement.isCharBackup())
			restoreChar(actionElement);
		else
			restoreHp(actionElement);
		contActions++;
		return true;
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
