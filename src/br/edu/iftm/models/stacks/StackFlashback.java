package br.edu.iftm.models.stacks;

public class StackFlashback extends Stack{
	
	
	public StackFlashback(int stackType) {
		super(stackType, 5);
	}
	
	public StackFlashback(int stackType, ActionElement actionElement) {
		super(stackType, actionElement, 5);
	}
	
	
	
}
