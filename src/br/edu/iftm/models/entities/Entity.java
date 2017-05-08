package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;

/**
 * Clase abstracta que contem os parametros e metodos comuns das entidades. Tambem possui o
 * metodo para verificar se a entidade esta colidindo com alguma outra. 
 */
public abstract class Entity {
	protected float speed;
	protected Point position;
	protected int dir;
	protected Image image;
	
	// Retângulos para as detectar as colisões
	private Rectangle me, him;
	
	/** 
	 * Move a entidade para um ponto.
	 * @param movX Coordenada X
	 * @param movY Coordenada Y
	 * @return True - Caso a consiga mover a Entidade; False - Caso nao consiga mover a entidade
	 */
	abstract boolean move(float movX, float movY);
	
	/**
	 * Cria uma entidade com a posicao, imagem e direcao. Cria os retangulos para deteccao de colisoes.
	 * @param position Posicao inicial
	 * @param image Imagem da entidade
	 * @param dir Direcao da entidade
	 */
	public Entity(Point position, Image image, int dir)
	{
		this.position = position;
		this.image = image;
		this.dir = dir;
		// Cria o retangulo para essa entidade usando as coordenadas e a largura e altura da imagem
		me = new Rectangle((int)this.getX(), (int)this.getY(), image.getWidth(), image.getHeight());
		// Cria uma instancia do Rectangle zerada
		him = new Rectangle(0, 0, 0, 0);
	}
	
	/**
	 * Cria uma entidade com a posicao, imagem e direcao.
	 * @param position Posicao inicial
	 * @param dir Direcao da entidade
	 */
	public Entity(Point position, int dir)
	{
		this.position = position;
		this.dir = dir;
	}
	
	/**
	 * Cria uma entidade com a posicao sem informar a imagem.
	 * @param position Posicao da entidade
	 * @param image Imagem da entidade
	 */
	public Entity(Point position, Image image)
	{
		this.position = position;
		this.image = image;
		dir = 0;
	}
	
	/**
	 * Desenha a imagem na tela na coordenada padrao. Para desenhar na posicao inicial, e preciso informar as coordenadas nos parametros do metodo draw.
	 */
	public void draw()
	{
		image.draw();
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	public int getDir()
	{
		return dir;
	}
	
	public void setDir(int dir)
	{
		this.dir = dir;
	}
	
	public int getX()
	{
		return (int)position.getX();
	}
	
	public int getY()
	{
		return (int)position.getY();
	}
	
	public void setX(int x)
	{
		position.setX((int)x);
	}
	
	public void setY(int y)
	{
		position.setY((int)y);
	}
	
	 /**
	 * Verifica se essa entidade esta colidindo com outra entidade.
	 *
	 * @param other A outra entidade que irá verificar está colidindo contra essa entidade
	 * @return True Caso essa entidade colida com a outra testada
	 */
	public boolean collidesWith(Entity other)
	{
		me.setBounds((int)this.getX(), (int)this.getY(), image.getWidth(), image.getHeight());
		him.setBounds((int)other.getX(), (int)other.getY(), other.getImage().getWidth(), other.getImage().getHeight());
		return me.intersects(him);
	}
	

	public Image getImage() {
		return image;
		
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	
	
}
