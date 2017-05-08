package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

import br.edu.iftm.controllers.Window;

/**
 * Cria uma entidade do tipo projetil.
 */
public class Projectile extends Entity{
	public static final int TYPE_HERO = 1, TYPE_ENEMY = 2;
	protected Point origin;
	protected int shooterType;
	
	/**
	 * Controi um projetil com posicao inicial (posicao do heroi), image do projetil e direcao
	 * @param heroPos Posicao inicial do heroi
	 * @param image Imagem do projetil
	 * @param dir
	 */
	public Projectile(Point heroPos, Image image, int dir) {
		super(new Point(heroPos.getX()+20, heroPos.getY()), image, dir);
		origin = new Point(heroPos.getX()+20, heroPos.getY());
		position = new Point(heroPos.getX()+20, heroPos.getY()+20);
		speed = 0.8f;
		this.image = image;
		generateImageRotation();
	}

	
	/**
	 * Move o projetil seguindo a direcao definida, verifica se a nova posicao esta nos limites da tela
	 * @param movX Nova coordenada X
	 * @param movY Nova coordenada Y
	 * @return True - caso a nova posicao esteja nos limites da tela; False - caso a nova posicao nao esteja nos limites da tela
	 */
	@Override
	boolean move(float movX, float movY) {
		if(Window.isInside(new Point(position.getX() + movX, position.getY() + movY), image.getWidth(), image.getHeight()))
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
	 * Desenha o projetil na tela nas coordenadas definidas
	 */
	@Override
	public void draw()
	{
		image.draw(position.getX(), position.getY());
	}
	
	
	/**
	 * Como so possui uma imagem para o projetil, verifica a direcao do projetil para rotaciona-la.
	 */
	private void generateImageRotation()
	{
		switch(dir)
		{
			case Character.SPRITE_DOWN:
				image.rotate(180);
				break;
			case Character.SPRITE_LEFT:
				image.rotate(270);
				break;
			case Character.SPRITE_RIGHT:
				image.rotate(90);
				break;
		}
	}
	
	/**
	 * Move o projetil com base na sua direcao
	 * @param delta E a diferenca entre o tempo de quando comeca o loop do metodo Update e de quando termina. Com esse deltaTime, voce consegue fazer com que um objeto tenha o mesmo deslocamento em hardwares diferentes
	 * @return True - caso consiga mover sem atingir os limites da tela; False - caso atinja os limites da tela
	 */
	public boolean moveShot(int delta)
	{
		switch(dir)
		{
			case Character.SPRITE_UP:
				return move(0, -speed * delta);
			case Character.SPRITE_DOWN:
				return move(0, speed * delta);
			case Character.SPRITE_LEFT:
				return move(-speed * delta, 0);
			case Character.SPRITE_RIGHT:
				return move(speed * delta, 0);
		}
		return false;
	}


	public int getShooterType() {
		return shooterType;
	}

	public void setShooterType(int shooterType) {
		this.shooterType = shooterType;
	}

	
	
}
