package br.edu.iftm.models.entities;

import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;
import br.edu.iftm.controllers.Window;

/**
 * Classe responsavel por criar personagens, sejam eles heroi ou inimigos. Ela e responsavel por desenhar
 * o personagem na tela seguindo as sua respectivas coordenadas e de mover o personagem no mapa.
 */
public class Character extends Entity{
	// Identificadores que representam a direcao da sprite na spritesheet
	public static final int SPRITE_DOWN = 0, SPRITE_LEFT = 1, SPRITE_RIGHT = 2, SPRITE_UP = 3;
	// Contador responsavel em percorrer as diferentes sprites de uma uma spritesheet em uma mesma direcao. 
	// Para dar o efeito de animacao. Se a spritesheet e uma matriz AxB personagens, esse contador serve
	// para percorrer as colunas em A.
	private int spriteOffX = 0;
	// Tamanho maximo do offset X da spritesheet. Tamanho maximo de A em uma matriz(spritesheet) AxB.
	private int maxOffsetX = 3;
	private SpriteSheet ss;
	// Limites em largura e altura, que servem para calibrar o personagem ao chegar perto dos limites da tela.
	// Elas servem para melhorar o tamanho da "caixa de colisao" SOMENTE quando esta tentando ultrapassar
	// os limites da tela.
	private int limWidthSprite, limHeightSprite;
	
	/**
	 * Crio o personagem em uma posicao inicial e com a spritesheet
	 * @param x Coordenada x inicial
	 * @param y Coordenada y inicial
	 * @param ss Spritesheet do personagem
	 */
	public Character(int x, int y, SpriteSheet ss){
		super(new Point(x, y), 3);
		this.dir = 3;
		this.ss = ss;
		speed = 0.5f;
		limWidthSprite = 0;
		limHeightSprite = 0;
	}
	
	/**
	 * Crio o personagem em uma posicao inicial, com uma spritesheet definida e direcao inicial.
	 * @param x Coordenada x inicial
	 * @param y Coordenada y inicial
	 * @param ss Spritesheet do personagem
	 * @param dir Direcao inicial do personagem
	 */
	public Character(int x, int y, SpriteSheet ss, int dir){
		super(new Point(x, y), dir);
		this.ss = ss;
		this.dir = dir;
		speed = 0.5f;
		limWidthSprite = 0;
		limHeightSprite = 0;
	}
	
	/**
	 * Seleciona a sprite no conjunto de sprites(spritesheet) e desenha na tela.
	 * Para selecionar o sprite na matriz de sprites AxB, onde A e a variavel spriteOffX
	 * e B e a variavel dir.
	 */
	public void draw()
	{		
		image = ss.getSprite(spriteOffX, dir);
		image.draw((int)position.getX(), (int)position.getY());
	}

	/**
	 * Caso o personagem nao ultrapasse os limites da tela, move o personagem para as coordenadas informadas.
	 * @param movX Nova coordenada X
	 * @param movY Nova coordenada Y
	 * @return True Caso o personagem tenha conseguido mover; False - Caso nao foi possivel mover o personagem
	 */
	@Override
	boolean move(float movX, float movY)
	{
		if(Window.isInside(new Point(position.getX() + movX, position.getY() + movY),
				limWidthSprite, limHeightSprite))
		{
			position.setX(position.getX() + movX);
			position.setY(position.getY() + movY);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Move o personagem para cima, valor negativo em Y; o tamanho do deslocamento do personagem sera a multiplicacao da velocidade
	 * e do deltaTime. O deltaTime e necessario para deixar o deslocamento igual para todos os tipos de hardware.
	 * @param delta E a diferenca entre o tempo de quando comeca o loop do metodo Update e de quando termina. Com esse deltaTime, voce consegue fazer com que um objeto tenha o mesmo deslocamento em hardwares diferentes
	 */
	public void moveUp(int delta)
	{
		if(dir == SPRITE_UP)
		{
			spriteOffX++;
			if(spriteOffX >= maxOffsetX) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			dir = 3;
		}
		
		move(0, -speed * delta);
	}
	
	/**
	 * Move o personagem para baixo, valor positivo em Y; o tamanho do deslocamento do personagem sera a multiplicacao da velocidade
	 * e do deltaTime. O deltaTime e necessario para deixar o deslocamento igual para todos os tipos de hardware.
	 * @param delta E a diferenca entre o tempo de quando comeca o loop do metodo Update e de quando termina. Com esse deltaTime, voce consegue fazer com que um objeto tenha o mesmo deslocamento em hardwares diferentes
	 */
	public void moveDown(int delta)
	{
		if(dir == SPRITE_DOWN)
		{
			spriteOffX++;
			if(spriteOffX >= maxOffsetX) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			dir = 0;
		}
		
		move(0, speed * delta);
	}
	
	/**
	 * Move o personagem para direita, valor positivo em X; o tamanho do deslocamento do personagem sera a multiplicacao da velocidade
	 * e do deltaTime. O deltaTime e necessario para deixar o deslocamento igual para todos os tipos de hardware.
	 * @param delta E a diferenca entre o tempo de quando comeca o loop do metodo Update e de quando termina. Com esse deltaTime, voce consegue fazer com que um objeto tenha o mesmo deslocamento em hardwares diferentes
	 */
	public void moveRight(int delta)
	{
		if(dir == SPRITE_RIGHT)
		{
			spriteOffX++;
			if(spriteOffX >= maxOffsetX) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			dir = 2;
		}
		
		move(speed * delta, 0);
	}
	
	/**
	 * Move o personagem para esquerda, valor negativo em X; o tamanho do deslocamento do personagem sera a multiplicacao da velocidade
	 * e do deltaTime. O deltaTime e necessario para deixar o deslocamento igual para todos os tipos de hardware.
	 * @param delta E a diferenca entre o tempo de quando comeca o loop do metodo Update e de quando termina. Com esse deltaTime, voce consegue fazer com que um objeto tenha o mesmo deslocamento em hardwares diferente.
	 */
	public void moveLeft(int delta)
	{
		if(dir == SPRITE_LEFT)
		{
			spriteOffX++;
			if(spriteOffX >= maxOffsetX) spriteOffX = 0;
		}
		else
		{
			spriteOffX = 0;
			dir = 1;
		}
		
		move(-speed * delta, 0);
	}
	

	public int getLimWidthSprite() {
		return limWidthSprite;
	}

	public void defineLimWidthSprite(int limWidthSprite) {
		this.limWidthSprite = ss.getSprite(0, 0).getWidth() - limWidthSprite;
	}


	public void defineLimHeightSprite(int limHeightSprite) {
		this.limHeightSprite = ss.getSprite(0, 0).getHeight() - limHeightSprite;
	}

	public void setMaxOffsetX(int maxOffsetX) {
		this.maxOffsetX = maxOffsetX;
	}

	public int getSpriteOffX() {
		return spriteOffX;
	}


	public void setSpriteOffX(int spriteOffX) {
		this.spriteOffX = spriteOffX;
	}


	public SpriteSheet getSpriteSheet() {
		return ss;
	}


	public void setSpriteSheet(SpriteSheet ss) {
		this.ss = ss;
	}
	
	
}
