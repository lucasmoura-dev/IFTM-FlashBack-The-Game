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
	
	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		bg.draw();	
		drawShots(g);
		
		if(skillOn)
			flashBack.drawOldHeroes();
		hero.draw();		
		drawEnemies(g);
		drawInterface();
		
		// Retangulos para fisica
		g.drawRect(hero.getX(), hero.getY(), hero.getImage().getWidth(), hero.getImage().getHeight());
		
		keysBar.draw();
		histBar.draw();
		
	}
	
	private void drawInterface()
	{
		lifebar.draw();
	}
	
	private void drawEnemies(Graphics g)
	{
		for(Character enemy : enemies)
		{
			enemy.draw();
			
			g.drawRect(enemy.getX(), enemy.getY(), enemy.getImage().getWidth(), enemy.getImage().getHeight());
		}
	}
	
	private void drawShots(Graphics g)
	{
		for (Shot shot : shots) 
		{
			shot.draw();
			
			g.drawRect(shot.getX(), shot.getY(), shot.getImage().getWidth(), shot.getImage().getHeight());
		}
	}

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
		hero.defineLimWidthSprite(10);
		hero.defineLimHeightSprite(1);
		lifebar = new LifeBar(Stack.TYPE_STATIC);
		histBar = new HistoryBar(new Point(Window.WIDTH-35, 0), new Image("images/bar.png"), 0);
		flashBack = new FlashBackSkill(Stack.TYPE_STATIC, hero, lifebar, histBar);
		flashBack.addBackup(hero); // Add the hero start position
		keysBar = new KeysBars();
		createEnemy();	
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if(lifebar.isEmpty())
		{
			// Game Over
			container.exit();
		}
		
		timerShots += delta;
		timerMonstersRespawn += delta;
		if(timerSkillCd <= SKILL_COLDOWN)
			timerSkillCd += delta;
		
		Input input = container.getInput();
		input.enableKeyRepeat();
		
		for(int i=0; i < shots.size(); i++)
		{
			if(!shots.get(i).moveShot(delta))
			{
				shots.remove(i);
			}
		}
		
		detectProjectilesCollisions();
		
		
		if(timerShots >= 1000)
			createShotsForMonsters();
		
		if(timerMonstersRespawn >= 3000)
		{
			createEnemy();
			timerMonstersRespawn = 0;
		}
		
		if(timerSkillCd >= SKILL_COLDOWN)
		{
			lifebar.enableSkillIcon();
			//timerSkillCd = 0;
		}
		
		// If the FlashBack's on, disable keys detection
		if(skillOn)
		{
			skillOn = flashBack.restore();
			if(!skillOn)
			{
				hero.setSpriteSheet(ss_hero);
				lifebar.disableSkillIcon();
				timerSkillCd = 0;
			}
			return;
		}	
		
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
		
		if(input.isKeyPressed(Input.KEY_SPACE) && timerSkillCd >= SKILL_COLDOWN)
		{
			System.out.println("Especial");
			flashBack.clearOldHeroes();
			flashBack.use();
			hero.setSpriteSheet(ss_hero_skill);
			skillOn = true;
		}
		
		if(input.isKeyPressed(Input.KEY_UP))
		{
			Shot shot = new Shot(hero.getPosition(), hero.getDir());
			shot.setShooterType(Projectile.TYPE_HERO);
			shots.add(shot);
		}
	}
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		timerShots = 0;
	}
	
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
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer ap = new AppGameContainer(new Game("Teste"));
		ap.setShowFPS(false);
		ap.setDisplayMode(Window.WIDTH, Window.HEIGHT, false);
		ap.setTargetFrameRate(Window.FPS);
		ap.start();
	}

}
