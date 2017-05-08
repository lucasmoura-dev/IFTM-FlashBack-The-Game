package br.edu.iftm.models.stacks;

/**
 * Elemento do tipo ponto de vida que e utilizado na pilha de barra de vida.
 *
 */
public class LifePointElement extends DataElement{
	// Valor que representa o elemento na pilha. Ex: 1 de hp
	private int value;
	
	public LifePointElement(int value)
	{
		this.value = value;
	}
	
	public LifePointElement()
	{
		value = 0;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
}
