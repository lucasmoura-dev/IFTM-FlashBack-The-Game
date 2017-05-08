package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import br.edu.iftm.models.stacks.StackLifeBar;

/** 
 * Classe responsavel por tudo relacionado a barra de vida. Ela tambem e responsavel em mudar o icone da habilidade
 * caso ela esteja disponivel ou nao. Ela tambem e responsavel em gerenciar a pilha de vida do personagem.
 * As imagens da barra de vida (com X azul ou cinza) esta com a barra cheia, para dar o efeito que o personagem
 * perdeu vida, e utilizado uma imagem preta que fica sobre a barra de vida, escondendo pontos de vida, dando
 * o efeito que o personagem perdeu pontos. O tamanho da imagem preta e proporcional a quantidade de pontos
 * de vida perdidos.
 */
public class LifeBar extends Entity{
	// Imagem preta que ficara sobre a barra de vida, de tamanho proporcional a quantidade de pontos de vida perdidos
	private Image blank;
	// Pilha que representa a vida do personagem
	private StackLifeBar stackHp;
	// Posicao da barra de vida
	private Point position;
	// Flag que define se o icone da habilidade especial esta disponivel
	private boolean skillEnabled = true;
	// Imagens da barra de vida cheia com os icones da habilidade especial ativado e desativado
	private Image barFullSkillOn, barFullSkillOff;
	
	/**
	 * Cria a barra de vida, definindo a posicao, imagem e tipo de pilha.
	 * @param position Posicao da barra de vida
	 * @param image Imagem da barra de vida cheia
	 * @param stackType Tipo da pilha da barra de vida
	 */
	private LifeBar(Point position, Image image, int stackType)
	{
		super(position, image);
		try {
			blank = new Image("/images/bar_blank.png");
		} catch (SlickException e) {
			e.printStackTrace();
		} 
		stackHp = new StackLifeBar(stackType, 160, true);
		this.position = position;
	}
	
	/**
	 * Cria a barra de vida definindo o tipo de pilha.
	 * @param stackType Tipo da pilha da barra de vida
	 * @throws SlickException Caso nao consiga criar as imagens
	 */
	public LifeBar(int stackType) throws SlickException
	{
		this(new Point(10, 10), new Image("/images/bar_full.png"), stackType);
		barFullSkillOn = new Image("/images/bar_full.png");
		barFullSkillOff = new Image("/images/bar_full_no_special.png");
	}
	
	/**
	 * Ativa o icone da habilidade especial
	 */
	public void enableSkillIcon()
	{
		if(!skillEnabled)
		{
			skillEnabled = true;
			image = barFullSkillOn;
		}
	}
	
	/**
	 * Desativa o icone da habilidade especial
	 */
	public void disableSkillIcon()
	{
		if(skillEnabled)
		{
			skillEnabled = false;
			image = barFullSkillOff;
		}
	}
	
	/**
	 * Desenha a barra de vida no jogo
	 */
	public void draw()
	{
		// Desenha a imagem de fundo da barra de vida cheia (a que contem a habilidade ativada ou desativada)
		image.draw((int)position.getX(), (int)position.getY());
		// Desenha a imagem preta sobre a barra de vida cheia, escondendo os pontos de vida perdidos
		drawBlank();
	}
	
	/**
	 * Desenha a imagem preta(em branco) sobre a barra de vida cheia. A sua altura e proporcional ao HP atual do personagem.
	 */
	public void drawBlank()
	{
		// Verifica a porcentagem da vida do persoangem
		float pcLife = (float)getHp() / (float)getHpMax();
		// Descobre a quantidade de linhas que sera mostrada. Considerando que 16 e a quantidade total de linhas na barra cheia.
		int amountLines = (int)(16 * pcLife);
		// Descobre a nova altura da imagem preta (em branco)
		int newHeight = (int)blank.getHeight()-(blank.getHeight() * amountLines / 16);
		// Desenha a imagem em branco sobre a barra de vida cheia. A barra em branco fica sobre a barra de vida pois foi desenhada depois da barra de vida cheia
		blank.draw((int)position.getX()+7, (int)position.getY()+7, blank.getWidth(), newHeight);
	}

	
	/**
	 * Metodo herdado, e irrelevante para essa entidade.
	 */
	@Override
	boolean move(float movX, float movY)
	{
		return false;
	}
	
	public int getHp()
	{
		return stackHp.getHp();
	}
	
	public int getHpMax()
	{
		return stackHp.getHpMax();
	}
	
	public void addHp(int amount)
	{
		stackHp.addLifePoint(amount);
	}
	
	public boolean isEmpty()
	{
		return stackHp.isEmpty();
	}
	
	/**
	 * Remove uma certa quantidade de pontos de vida da pilha. Ele nao consegue remover pontos de uma pilha vazia.
	 * @param amount Quantidade de pontos de vida que serao removidos (desempilhados)
	 */
	public void removeHp(int amount)
	{
		try {
			stackHp.removeLifePoint(amount);
		} catch (Exception e) {
			System.out.println("Morreu");;
		}
	}

}
