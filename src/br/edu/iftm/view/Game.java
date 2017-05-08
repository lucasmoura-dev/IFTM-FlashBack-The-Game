package br.edu.iftm.view;

import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import br.edu.iftm.controllers.FlashBackSkill;
import br.edu.iftm.controllers.Window;
import br.edu.iftm.models.entities.Character;
import br.edu.iftm.models.entities.HistoryBar;
import br.edu.iftm.models.entities.KeysBars;
import br.edu.iftm.models.entities.LifeBar;
import br.edu.iftm.models.entities.Projectile;
import br.edu.iftm.models.entities.Shot;
import br.edu.iftm.models.stacks.Stack;


public class Game extends BasicGame{

	public Image bg;
	private Character hero;
	private ArrayList<Shot> shots;
	private ArrayList<Character> enemies;
	private LifeBar lifebar;
	private int timerShots = 0, timerMonstersRespawn = 0, timerSkillCd;
	private Random rand;
	private FlashBackSkill flashBack;
	private boolean skillOn;
	private SpriteSheet ss_hero, ss_hero_skill;
	private HistoryBar histBar;
	private static final int SKILL_COLDOWN = 5000;
	private KeysBars keysBar;
	
	
	/* Defino se o jogo usará pilhas estatica ou dinamica */
	private static final int STACK_TYPE = Stack.TYPE_STATIC;
	
	/**
	 * Construtor que inicia o jogo com o titulo definido do metodo main
	 * @param title
	 */
	public Game(String title) {
		super(title);
	}

	/**
	 * Metodo responsavel por desenhar os graficos na tela. Nele, e desenhado o mapa, 
	 * os projeteis, o rastro dos herois (trajeto) quando ele usa a habilidade, 
	 * os inimigos e a interface.
	 */
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		bg.draw();	
		drawShots(g);
		
		if(skillOn)
			flashBack.drawOldHeroes();
		hero.draw();		
		drawEnemies(g);
		drawInterface();
		
		// Desenha o retângulo que representa a caixa de colisão do herói
		//g.drawRect(hero.getX(), hero.getY(), hero.getImage().getWidth(), hero.getImage().getHeight());
		
	}
	
	/**
	 * Desenha a interface na tela (barra de vida, barra com as teclas e barra com o historico de açoes
	 * empilhadas.
	 */
	private void drawInterface()
	{
		lifebar.draw();
		keysBar.draw();
		histBar.draw();
	}
	
	/**
	 * Desenha todos os inimigos na tela
	 * @param g Graphics para poder desenhar o retangulo da caixa de colisao do inimigo
	 */
	private void drawEnemies(Graphics g)
	{
		for(Character enemy : enemies)
		{
			enemy.draw();
			
			//g.drawRect(enemy.getX(), enemy.getY(), enemy.getImage().getWidth(), enemy.getImage().getHeight());
		}
	}
	
	/**
	 * Desenha todos os projeteis(tiro e flechas) na tela
	 * @param g Graphics para poder desenhar o retangulo da caixa de colisao no tiro
	 */
	private void drawShots(Graphics g)
	{
		for (Shot shot : shots) 
		{
			shot.draw();
			
			// Cria um retângulo que representa a caixa de colisão do tiro
			//g.drawRect(shot.getX(), shot.getY(), shot.getImage().getWidth(), shot.getImage().getHeight());
		}
	}

	/***
	 *  Esse é o primeiro metodo a ser executado quando o jogo for inicializado no metodo main.
	 *  Nele, sao definidos os valores iniciais para as variaveis e instancia alguns objetos.
	 *  Também, e adicionado a posicao inicial do heroi na pilha de acoes e cria um inimigo 
	 *  inicial.
	 */
	
	@Override
	public void init(GameContainer container) throws SlickException {
		skillOn = false;
		timerSkillCd = SKILL_COLDOWN;
		rand = new Random();
		shots = new ArrayList<Shot>();
		enemies = new ArrayList<Character>();
		bg = new Image("/images/bg.png");
		ss_hero = new SpriteSheet("/images/char.png", 48, 48);
		ss_hero_skill = new SpriteSheet("/images/char_flashback.png", 48, 48);
		hero = new Character(100, 100, ss_hero, Character.SPRITE_DOWN);
		hero.defineLimWidthSprite(10); // Serve para calibrar a caixa de colisao do peronagem quando ele chega perto dos limites da tela
		hero.defineLimHeightSprite(1);// Serve para calibrar a caixa de colisao do peronagem quando ele chega perto dos limites da tela
		lifebar = new LifeBar(STACK_TYPE);
		histBar = new HistoryBar(new Point(Window.WIDTH-35, 0), new Image("images/bar.png"));
		flashBack = new FlashBackSkill(STACK_TYPE, hero, lifebar, histBar);
		flashBack.addBackup(hero); // Adiciona a posição inicial do heroi na lista de ações 
		keysBar = new KeysBars(); // Classe que representa as interfaces com as teclas desenhadas
		createEnemy(); // Cria um inimigo inicial	
	}
	
	
	/**
	 *  Nesse metodo, que funciona como um thread, e responsável por incrementar os
	 *  contadores de tempo. Detectar as teclas pressionadas e ativar as suas 
	 *  devidas funções. Essa funcao é responsavel em fazer um personagem andar
	 *  caso as teclas de movimento forem pressionadas, ativar a habilidade
	 *  Flashback caso a barra de espaco for pressionada, e lançar um
	 *  projetil caso a tecla Seta Cima for pressionada. Tambem verifica se a vida
	 *  do heroi esta vazia, para finalizar o jogo.
	 */
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		// Caso a vida do herói ficar vazia, finaliza o jogo
		if(lifebar.isEmpty())
		{
			// Game Over
			container.exit();
		}
		
		timerShots += delta;
		timerMonstersRespawn += delta;
		if(timerSkillCd <= SKILL_COLDOWN)
			timerSkillCd += delta;
		
		// Pega as teclas pressionadas e ativa a repetição das teclas (o usuário pode segurar as teclas)
		Input input = container.getInput();
		input.enableKeyRepeat();
		
		
		// Verifica cada projetil, caso um deles chegue nos limites do mapa, e destruido (removido)
		for(int i=0; i < shots.size(); i++)
		{
			if(!shots.get(i).moveShot(delta))
			{
				shots.remove(i);
			}
		}
		
		// Chama o método responsavel por verificar se os projeteis acertaram alguem
		detectProjectilesCollisions();
		
		// Se o temporizador de tiros chegar a 1s, cria um tiro para cada monstro do mapa
		if(timerShots >= 1000)
			createShotsForMonsters();
		
		// Se o temporizador de respawn de mosntros chegar a 3s, cria um novo monstro
		if(timerMonstersRespawn >= 3000)
		{
			createEnemy();
			timerMonstersRespawn = 0; // Reseta o temporizador, para recomecar a contagem
		}
		
		// Se o temporizador da habilidade chegar ao tempo gasto para poder usar a habilidade novamente
		// Ativa o icone 'X' na barra de vida (fica azul)
		if(timerSkillCd >= SKILL_COLDOWN)
		{
			lifebar.enableSkillIcon();
		}
		
		// Se a habilidade Flashback estiver sendo usada, disabilita a deteccao das teclas
		if(skillOn)
		{
			skillOn = flashBack.restore(); // Desempilha uma acao da pilha de ações (movimentos ou vida perdida)
			if(!skillOn)
			{
				// Se ele não conseguiu desempilhar, ou seja, acabou a pilha ou chegou no seu limite máximo
				// de movimentos restaurados, volta a spriteSheet original do personagem e disabilita o 
				// ícone 'X' (fica cinza) da barra de vida e reseta o temporizador da skill. Ou seja,
				// o personagem precisará esperar 5 segundos para poder usar novamente.
				hero.setSpriteSheet(ss_hero);
				lifebar.disableSkillIcon();
				timerSkillCd = 0;
			}
			return; // Sai da função antes de detectar as teclas pressionadas
		}	
		
		// Detecta qual tecla responsável por mover o personagem foi acionada. 
		// Após mover o personagem, ele salva a nova posição na pilha de ações
		if(input.isKeyPressed(Input.KEY_W)) 
		{
			hero.moveUp(delta);
			flashBack.addBackup(hero);
		}
		else if(input.isKeyPressed(Input.KEY_S))
		{
			hero.moveDown(delta);
			flashBack.addBackup(hero);
		}
		else if(input.isKeyPressed(Input.KEY_A))
		{
			hero.moveLeft(delta);
			flashBack.addBackup(hero);
		}
		else if(input.isKeyPressed(Input.KEY_D))
		{
			hero.moveRight(delta);
			flashBack.addBackup(hero);
		}
		
		// Se apertou a barra de espaço e ela estiver disponível (temporizador maior ou igual
		// ao tempo necessário para poder usar a habilidade).
		if(input.isKeyPressed(Input.KEY_SPACE) && timerSkillCd >= SKILL_COLDOWN)
		{
			System.out.println("Especial");
			flashBack.clearOldHeroes(); // Limpa a lista que contém a trajetória dele quando ele usou a habilidade anteriormente
			flashBack.use(); // Inicia a habilidade flashback
			hero.setSpriteSheet(ss_hero_skill); // Define a spritesheet do personagem para uma diferente (embaçada)
			skillOn = true; // Flag que representa que a habilidade está ativa
		}
		
		// Se pressionar a tecla Seta Cima, lança um novo tiro, iniciando com a posição e direção do personagem
		if(input.isKeyPressed(Input.KEY_UP))
		{
			Shot shot = new Shot(hero.getPosition(), hero.getDir());
			shot.setShooterType(Projectile.TYPE_HERO); // Define a etiqueta do projétil como do tipo HERÓI
			shots.add(shot);
		}
	}
	
	/**
	 * De 1 em 1 segundo, essa função é chamada. Ela é responsável por criar os projéteis
	 * dos inimigos (flechas). As flechas são criadas na posição do inimigo e na mesma 
	 * direção, com uma velocidade de 0.5f. Os projéteis são etiquetados como do tipo
	 * INIMIGO. O projétil criado é salvado na lista de todos os projéteis. 
	 */
	private void createShotsForMonsters()
	{
		for(int i=0; i < enemies.size(); i++)
		{
			Shot shot;
			try {
				shot = new Shot(enemies.get(i).getPosition(), enemies.get(i).getDir(), new Image("/images/shot2.png"));
				shot.setShooterType(Projectile.TYPE_ENEMY);
				shot.setSpeed(0.5f);
				shots.add(shot);
			} catch (SlickException e) {
				e.printStackTrace();
			}			
		}
		timerShots = 0; // Reseta o temporizador dos tiros, para reiniciar a contagem.
	}
	
	/**
	 * Cria um inimigo em uma posição aleatório mas que atenda algumas restrições de posionamento.
	 * As posições dos inimigos, precisam estar em uma distância mínima de 150px das bordas.
	 * @throws SlickException Não foi possível criar o spritesheet do inimigo.
	 */
	
	private void createEnemy() throws SlickException
	{
		int x = rand.nextInt(Window.WIDTH-300) + 150;
		int y = rand.nextInt(Window.HEIGHT-300) + 150;
		int dir = rand.nextInt(4);
		
		System.out.println("enemy y = " + y);
		Character enemy = new Character(x, y, new SpriteSheet("/images/skeleton.png", 48, 48), dir);
		enemy.defineLimWidthSprite(10);
		enemy.defineLimHeightSprite(5);
		enemies.add(enemy);
	}
	
	/** 
	 * Verifica se algum projétil (flecha ou tiro) acertou alguém.
	 * Para cada projétil, ele verifica se está colidinho com algum personagem(herói ou inimigo).
	 * Caso colida com algum personagem, ele verifica quem atirou o projétil, impedindo
	 * que um inimigo acerte outro inimigo. O herói só pode acertar os inimigos, e os
	 * inimigos só podem acertar o herói.
	 */
	
	private void detectProjectilesCollisions()
	{
		for(int i=0; i < shots.size(); i++)
		{
			if(shots.get(i).getShooterType() == Projectile.TYPE_HERO)
			{
				for(int j=0; j < enemies.size() && !shots.isEmpty(); j++)
				{
					System.out.println("i = " +i + "; j = " + j + " shots.size = " + shots.size() + " enemies.size = " + enemies.size());
					if(shots.get(i).collidesWith(enemies.get(j)))
					{
						enemies.remove(j--);
						shots.remove(i--);
						if(i < 0)
							i = 0;
					}
				}
			}
			else
			{
				if(shots.get(i).collidesWith(hero))
				{
					if(skillOn) // The hero with the active skill becomes invulnerable
						return;
					lifebar.removeHp(1);
					flashBack.addBackup(lifebar.getHp());
					shots.remove(i--);
				}
			}
		}
	}
	
	/*
	 * Responsável por iniciar o jogo
	 * Define configurações do jogo (mostrar FPS, resolução da janela, define o FPS máximo de 59)
	*/
	public static void main(String[] args) throws SlickException {
		AppGameContainer ap = new AppGameContainer(new Game("Jogo Flasback"));
		ap.setShowFPS(false);
		ap.setDisplayMode(Window.WIDTH, Window.HEIGHT, false);
		ap.setTargetFrameRate(Window.FPS);
		ap.start(); // Após configurado, inicia o jogo
	}

}
