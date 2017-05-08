package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import br.edu.iftm.controllers.Window;

/**
 * Responsavel por criar as janelas com o guia das teclas e suas acoes.
 */
public class KeysBars{
	private Image barLeft, barRight;
	private Image keyW, keyWOn, keyA, keyAOn, keyS, keySOn, keyD, keyDOn, keySpace, keySpaceOn, keyUp, keyUpOn;
	
	
	/**
	 * Constroi a classe instanciando as imagens das teclas e barras(esquerda e direita).
	 */
	public KeysBars() {
		try {
			barLeft = new Image("images/bar_keys.png");
			barRight = new Image("images/bar_keys_right.png");
			
			keyW = new Image("images/key_w.png");
			keyWOn = new Image("images/key_w_on.png");	
			
			keyA = new Image("images/key_a.png");
			keyAOn = new Image("images/key_a_on.png");	

			keyS = new Image("images/key_s.png");
			keySOn = new Image("images/key_s_on.png");
			
			keyD = new Image("images/key_d.png");
			keyDOn = new Image("images/key_d_on.png");
			
			keySpace = new Image("images/key_space.png");
			keySpaceOn = new Image("images/key_space_on.png");
			
			keyUp = new Image("images/key_up.png");
			keyUpOn = new Image("images/key_up_on.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Desenha as barras(janelas) esquerda e direita e as imagas das teclas
	 */
	public void draw()
	{
		barLeft.draw(0, Window.HEIGHT-barLeft.getHeight());
		barRight.draw(Window.WIDTH-barRight.getWidth()-50, Window.HEIGHT-barRight.getHeight());
		drawKeys();
	}
	
	/**
	 * Desenha as teclas
	 */
	private void drawKeys()
	{
		keyW.draw(27, Window.HEIGHT-100+8);
		keyA.draw(12, Window.HEIGHT-100+35);
		keyS.draw(38, Window.HEIGHT-100+35);
		keyD.draw(66, Window.HEIGHT-100+35);
		keySpace.draw(48, Window.HEIGHT-100+72);
		
		keyUp.draw(Window.WIDTH-90+8, Window.HEIGHT-50+8);
	}

}
