package br.edu.iftm.models.stacks.flashback;

import org.newdawn.slick.geom.Point;

import br.edu.iftm.models.stacks.Stack;

/**
 * Pilha que armazena as acoes do heroi que pode ser do tipo movimento ou perda de vida
 */
public class StackFlashback extends Stack{
	
	/**
	 * Cria a pilha com um tipo e tamanho maximo
	 * @param stackType Tipo da pilha
	 * @param size Tamanho maximo da pilha
	 */
	public StackFlashback(int stackType, int size) {
		super(stackType, size);
	}
	
	/**
	 * Cria a pilha com um tipo, elemento no topo e tamanho maximo da pilha
	 * @param stackType Tipo da pilha
	 * @param actionElement Elemento que ficara no topo da pilha
	 * @param size Tamanho maximo da pilha
	 */
	public StackFlashback(int stackType, ActionElement actionElement, int size) {
		super(stackType, actionElement, size);
	}
	
	/**
	 * Adiciona o backup do personagem do tipo vida (HP)
	 * @param hp Vida atual do personagem
	 */
	public void addCharBackup(int hp)
	{
		ActionElement actionElement = new ActionElement(hp);
		try {
			push(actionElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adicioan o backup do personagem do tipo movimento
	 * @param spriteOffX Id da sprite do personagem
	 * @param dir Direcao do personagem
	 * @param pos Posicao do personagem
	 * @param speed Velocidade do personagem
	 */
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
	
	/**
	 * Desempilha uma acao da pilha
	 * @return Retorna o elemento desempilhado da pilha (ActionElement)
	 */
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
