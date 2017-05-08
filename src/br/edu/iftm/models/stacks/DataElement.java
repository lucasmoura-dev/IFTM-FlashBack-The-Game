package br.edu.iftm.models.stacks;

public abstract class DataElement {
	// O elemento atual aponta para o elemento anterior (de baixo)
	private DataElement previousElement;
	
	public DataElement()
	{
		previousElement = null;
	}
	
	/** 
	 * Define o elemento anterior a ele (de baixo).
	 * @param next Elemento anterior
	 */
	public void addPrevious(DataElement next)
	{
		this.previousElement = next;
	}
	
	/**
	 * Retorna o elemento anterior a ele
	 * @return elemento retornado
	 */
	public DataElement getPrevious()
	{
		return previousElement;
	}
	
	public void clearPrevious()
	{
		previousElement = null;
	}

	
}
