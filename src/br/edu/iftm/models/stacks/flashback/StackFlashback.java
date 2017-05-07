package br.edu.iftm.models.stacks.flashback;

import org.newdawn.slick.geom.Point;

import br.edu.iftm.models.stacks.Stack;

public class StackFlashback extends Stack{
	
	public StackFlashback(int stackType, int size) {
		super(stackType, size);
	}
	
	public StackFlashback(int stackType, ActionElement actionElement, int size) {
		super(stackType, actionElement, size);
	}
	
	public void addCharBackup(int hp)
	{
		ActionElement actionElement = new ActionElement(hp);
		try {
			push(actionElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addCharBackup(int spriteOffX, int dir, Point pos, float speed)
	{
		CharacterBackup newCharBkp = new CharacterBackup(spriteOffX, dir, pos, speed);
		ActionElement actionElement = new ActionElement(newCharBkp);
		try {
			push(actionElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ActionElement restore()
	{
		try {
			ActionElement actionElement = (ActionElement) pop();
			return actionElement;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
}
