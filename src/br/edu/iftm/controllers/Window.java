package br.edu.iftm.controllers;

import org.newdawn.slick.geom.Point;

/**
 * Contem as informacoes da resolucao da tela e do FPS. Tambem possui a funcao estatica que verifica se um ponto esta dentro dos limites da tela.
 *
 */
public class Window {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int FPS = 59;
	
	
	/** Verifica se um objeto com a sua possível posição, não está chegando
	 * nos limites da tela.
	 * @param position Posição a ser testada
	 * @param widthObj Largura da iamgem
	 * @param heightObj Altura da imagem
	 * @return True - caso a poisção testada não ultrapasse os limites da tela; False - caso a posição testada ultrapasse os limites da tela
	 */
	public static boolean isInside(Point position, int widthObj, int heightObj)
	{
		if(position.getX() >= 0 && position.getX() < WIDTH - widthObj)
		{
			return(position.getY() >= 0 && position.getY() < HEIGHT - heightObj);
		}
		else
		{
			return false;
		}
	}
}
