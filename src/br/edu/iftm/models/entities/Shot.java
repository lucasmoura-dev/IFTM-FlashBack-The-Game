package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

public class Shot extends Projectile{
	
	/**
	 * Cria um Shot com o a posicao do heroi, direcao e imagem do projetil
	 * @param heroPos Posicao do heroi
	 * @param dir Direcao do heroi
	 * @param image Imagem do projetil
	 */
	public Shot(Point heroPos, int dir, Image image){
		super(heroPos, image, dir);
	}
	
	/**
	 * Cria um shot com a posicao do heroi e direcao. A imagem da municao e definida por padrao.
	 * @param heroPos Posicao do heroi
	 * @param dir Direcao do heroi
	 * @throws SlickException Caso nao seja possivel criar a imagem
	 */
	public Shot(Point heroPos, int dir) throws SlickException {
		super(heroPos, new Image("/images/shot.png"), dir);
	}
	
	
	
}
