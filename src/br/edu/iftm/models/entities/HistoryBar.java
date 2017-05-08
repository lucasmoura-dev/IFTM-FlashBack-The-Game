package br.edu.iftm.models.entities;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

/**
 * Classe responsavel em gerenciar e desenhar a barra com o historico de acoes realizadas.
 *
 */
public class HistoryBar extends Entity{
	// Lista com os icones que estao no historico de acoes
	private ArrayList<Image> iconset;
	// Variavel responsavel por ficar trocando as imagens dos passos (rotacionadas horizontalmente).
	private int type = 1;
	
	/**
	 * Cria a barra de historico de acoes em uma posicao e com uma imagem de fundo
	 * @param position Posicao da barra de historico de acoes
	 * @param image Imagem de fundo da barra de historico
	 */
	public HistoryBar(Point position, Image image) {
		super(position, image);
		iconset = new ArrayList<Image>();
	}
	
	/**
	 * Remove o primeiro icone da lista de icones, ou seja, remove o icone mais antigo para ser substituido.
	 * O icone precisa ser removido, pois podera ultrapassar o tamanho maximo que e 50, saindo da barra de historico.
	 */
	public void removeIcon()
	{
		if(!iconset.isEmpty())
			iconset.remove(iconset.size()-1);
	}
	
	/**
	 * Desenha a barra de historico de acoes na tela (imagem de fundo e os icones da lista).
	 */
	public void draw()
	{
		image.draw(position.getX(), position.getY()); // Desenha a imagem de fundo da barra
		// Posicao inicial dos icones. A altura inicial do icone fica no final da barra.
		int posX = (int)position.getX() + 11;
		int posY = (int)position.getY() + image.getHeight() - 12;
		for(Image icon : iconset)
		{
			icon.draw(posX, posY);
			posY -= 12; // A altura do proximo icone ficara acima do icone anterior
		}
	}
	
	/**
	 * Adiciona o icone que representa o Movimento do personagem na lista de icones.
	 */
	public void addCharMovIcon()
	{
		try {
			if(iconset.size() == 50) // Caso a lista esteja cheia, remove o primeiro icone (o mais antigo)
				removeFirst();
			
			if(type == 1) // Se o tipo for igual a 1, desenha a imagem da 'pegada'
				iconset.add(new Image("images/footsteps.png"));
			else // Se o tipo for igual a -1, desenha a imagem da 'pegada' invertida horizontalmente
				iconset.add(new Image("images/footsteps2.png"));
			
			type *= -1; // Inverte a variavel
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adiciona o icone que representa Perda de Vida do personagem na lista de icones
	 */
	public void addCharLostHp()
	{
		try {
			if(iconset.size() == 50) // Caso a lista esteja cheia, remove o primeiro icone (o mais antigo)
				removeFirst();
			iconset.add(new Image("images/lost_hp.png"));
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove o primeiro icone, ou seja, o icone mais antigo, que fica no fundo da barra.
	 */
	public void removeFirst()
	{
		if(!iconset.isEmpty())
			iconset.remove(0);
	}

	@Override
	boolean move(float movX, float movY) {
		return false;
	}

}
