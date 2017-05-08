package br.edu.iftm.models.stacks;

public class StackLifeBar extends Stack {
	// Representa quanto de vida cada elemento da pilha representa. Ex: 1 traço na pilha de vida representa
	// 10 de vida no HP, ou seja, 160HP / 10 = 16 traços.
	public static final int LP_UNIT = 10;
	
	/**
	 * Controi a pilha de vida com a vida maxima
	 * @param stackType Tipo da pilha (estatica ou dinamica)
	 * @param hpMax Vida maxima do jogador
	 */
	public StackLifeBar(int stackType, int hpMax) {
		super(stackType, hpMax / LP_UNIT); // Inicio a pilha com o tamanho máximo sendo a quantidade de traços, 160HP / 10 = 16 traços. 
	}
	
	/**
	 * Contrói a pilha de vida com a vida maxima
	 * @param stackType Tipo da pilha (estatica ou dinamica)
	 * @param lastElement Elemento do topo da pilha
	 * @param hpMax Vida maxima do jogador
	 */
	public StackLifeBar(int stackType, LifePointElement lastElement, int hpMax) // Action, LifePoint
	{
		super(stackType, lastElement, hpMax / LP_UNIT); // Inicio a pilha com o tamanho máximo sendo a quantidade de traços, 160HP / 10 = 16 traços.
	}
	
	/**
	 * Controi a pilha de vida com a vida maxima e com a pilha de vida cheia (HP = HPMAX)
	 * @param stackType Tipo da pilha (estatica ou dinamica)
	 * @param hpMax Vida maxima do jogador
	 * @param fillHp Se a barra de vida começara cheia
	 */
	public StackLifeBar(int stackType, int hpMax, boolean fillHp) // Action, LifePoint
	{
		this(stackType, hpMax);
		if(fillHp)
			fillHp();
	}
	
	/**
	 * Preenche a barra de vida (HP = HPMAX)
	 */
	public void fillHp()
	{
		addLifePoint(sizeMax);
	}
	
	/**
	 * Adicioan um ponto de vida na pilha (barra de vida). Um ponto de vida equivale
	 * a um LP_UNIT = 10
	 * @param amount Quantidade de pontos de vida(LP_UNIT) a ser empilhado
	 */
	public void addLifePoint(int amount)
	{
		for(int i=0; i < amount && !isFull(); i++)
		{
			LifePointElement newLP = new LifePointElement();
			try {
				push(newLP);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Remove(desempilha) uma certa quantidade de pontos de vida(LP_UNIT) da pilha
	 * @param amount Quantidade de pontos de vida(LP_UNIT) a ser desempilhado
	 * @throws Exception Caso não consiga desempilhar (pilha vazia)
	 */
	public void removeLifePoint(int amount) throws Exception
	{
		for(int i=0; i < amount; i++)
		{
			pop();
		}
	}

	public int getHpMax() {
		return sizeMax * LP_UNIT;
	}

	public void setHpMax(int hpMax) {
		sizeMax = hpMax / LP_UNIT;
	}

	public int getHp() {
		return getSize() * LP_UNIT;
	}

}
