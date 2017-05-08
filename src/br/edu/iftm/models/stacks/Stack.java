package br.edu.iftm.models.stacks;

public class Stack {
	// Na pilha dinâmica, contém um elemento que aponta para o elemento do topo da pilha
	protected DataElement lastElement;
	public static final int TYPE_DYNAMIC = 1, TYPE_STATIC = 2;
	private int size; // Tamanho atual da pilha
	protected int sizeMax; // Tamanho máximo da pilha
	private int stackType; 
	private DataElement[] staticStack; // Na pilha estática, contém um vetor de elementos
	
	/**
	 * Constrói uma pilha de um tipo e tamanho máximo.
	 * @param stackType Define o tipo da pilha (estática ou dinâmica)
	 * @param sizeMax Define o tamanho máximo da pilha
	 */
	public Stack(int stackType, int sizeMax)
	{
		size = 0;
		this.stackType = stackType;
		this.sizeMax = sizeMax;
		lastElement = null;
		if(stackType == TYPE_STATIC)
			staticStack = new DataElement[sizeMax];
	}
	
	/**
	 * Constrói uma pilha de um tipo e tamanho máximo.
	 * @param stackType Define o tipo da pilha (estática ou dinâmica)
	 * @param lastElement Define o elemento do topo
	 * @param sizeMax Define o tamanho máximo da pilha
	 */
	public Stack(int stackType, DataElement lastElement, int sizeMax) // Action, LifePoint
	{
		this(stackType, sizeMax);
		//this.lastElement = lastElement;
		//size++;
		try {
			push(lastElement);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adiciona um elemento no topo da pilha estática
	 * @param dataElement Elemento a ser empilhado
	 */
	private void pushStaticStack(DataElement dataElement)
	{
		staticStack[size] = dataElement;
	}
	
	/**
	 * Adiciona um elemento no topo da pilha dinâmica
	 * @param dataElement Elemento a ser empilhado
	 */
	private void pushDynamicStack(DataElement dataElement)
	{
		dataElement.addPrevious(lastElement);
		lastElement = dataElement;
	}
	
	/** 
	 * Adiciona um elemento no topo da fila. Ele chama o método de empilhar
	 * correspondente para cada tipo de pilha.
	 * @param dataElement Elemento a ser empilhado
	 * @throws Exception Caso a pilha esteja cheia
	 */
	public void push(DataElement dataElement) throws Exception
	{
		if(isFull())
			throw new Exception("Stackk full");
		if(isDynamicStack())
			pushDynamicStack(dataElement);
		else
			pushStaticStack(dataElement);
		size++;
	}
	
	/**
	 * Desempinha um elemento do topo da pilha estática
	 * @return Elemento desempinhado
	 */
	private DataElement popStaticStack()
	{
		DataElement returnElement = staticStack[size-1];
		staticStack[size-1] = null;
		return returnElement;
	}
	
	/**
	 * Desempinha um elemento do topo da pilha dinâmica
	 * @return Elemento desempinhado
	 */
	private DataElement popDynamicStack()
	{
		DataElement returnElement = lastElement;
		lastElement = lastElement.getPrevious();
		return returnElement;
	}
	
	/** 
	 * Desempinha um elemento do topo da fila. Ele chama o método de desempilhar
	 * correspondente para cada tipo de pilha.
	 * @return Elemento desempinhado
	 * @throws Exception Caso a pilha esteja cheia
	 */
	public DataElement pop() throws Exception
	{
		if(isEmpty())
			throw new Exception("Empty stack");
		
		DataElement returnElement;
		if(isDynamicStack())
			returnElement = popDynamicStack();
		else
			returnElement = popStaticStack();
		size--;
		return returnElement;
	}
	
	/**
	 * Retorna o elemento do topo da pilha estática sem desempinhar.
	 * @return Elemento do topo
	 */
	private DataElement peekStaticStack()
	{
		return staticStack[size-1];
	}
	
	/**
	 * Retorna o elemento do topo da pilha dinâmica sem desempinhar.
	 * @return Elemento do topo
	 */
	private DataElement peekDynamicStack()
	{
		return lastElement;
	}
	
	
	/**
	 * Retorna o elemento do topo da pilha sem desempinhar. Chama o método
	 * correspondente para cada tipo de pilha.
	 * @return Elemento do topo
	 * @throws Exception Caso a pilha esteja vazia
	 */
	public DataElement peek() throws Exception
	{
		if(isEmpty())
			throw new Exception("Empty stack");
		if(isDynamicStack())
			return peekDynamicStack();
		else
			return peekStaticStack();
	}
	
	/**
	 * Limpa a pilha (estática ou dinâmica), desempilha todos os elementos.
	 */
	public void clear()
	{
		while(!isEmpty())
		{
			try {
				pop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Verifica se a pilha está cheia.
	 * @return True - caso a pilha esteja cheia; False - caso a pilha não esteja cheia.
	 */
	public boolean isFull()
	{
		return size == sizeMax;
	}
	
	public int getSize()
	{
		return size;
	}
	
	/**
	 * Verifica se a pilha está vazia.
	 * @return True - caso a pilha esteja vazia; False - caso a pilha não esteja vazia.
	 */
	
	public boolean isEmpty()
	{
		return (size == 0);
	}
	
	/**
	 * Verifica se a pilha é dinâmica
	 * @return True - caso a pilha seja dinâmica; False - caso a pilha seja dinâmica (estática)
	 */
	public boolean isDynamicStack()
	{
		return stackType == TYPE_DYNAMIC;
	}
	
	/**
	 * Verifica se a pilha é estática
	 * @return True - caso a pilha seja estática; False - caso a pilha seja estática (dinâmica)
	 */
	public boolean isStaticStack()
	{
		return stackType == TYPE_STATIC;
	}

	public int getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(int sizeMax) {
		this.sizeMax = sizeMax;
	}
	
	
}
