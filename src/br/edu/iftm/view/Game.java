package br.edu.iftm.view;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.HPOcclusionTest;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import br.edu.iftm.controllers.Window;
import br.edu.iftm.models.entities.Character;
import br.edu.iftm.models.entities.Entity;
import br.edu.iftm.models.entities.LifeBar;
import br.edu.iftm.models.entities.Projectile;
import br.edu.iftm.models.entities.Shot;

public class Game extends BasicGame{

	public Image bg;
	private Character hero;
	private ArrayList<Shot> shots;
	private ArrayList<Character> enemies;
	private LifeBar lifebar;
	private Character enemy;
	private int counter = 0;
	private Random rand;
	
	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		bg.draw();	
		drawShots(g);
		
		
		hero.draw();		
		drawEnemies(g);
		drawInterface();
		
		// Retângulos para física
		g.drawRect(hero.getX(), hero.getY(), hero.getImage().getWidth(), hero.getImage().getHeight());
		
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
		rand = new Random();
		shots = new ArrayList<Shot>();
		enemies = new ArrayList<Character>();
		bg = new Image("/images/bg.png");
		hero = new Character(100, 100, new SpriteSheet("/images/char_flashback.png", 48, 48));
		hero.defineLimWidthSprite(10);
		hero.defineLimHeightSprite(1);
		lifebar = new LifeBar();
		createEnemy();
		
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		//System.out.println("HP: " + lifebar.getHp() + "/" + lifebar.getHpMax());
		
		if(lifebar.isEmpty())
		{
			// Game Over
			container.exit();
		}
		
		counter += delta;
		
		
		Input input = container.getInput();
		input.enableKeyRepeat();
		
		for(int i=0; i < shots.size(); i++)
		{
			if(!shots.get(i).moveShot(delta))
			{
				shots.remove(i);
			}
		}
		
		if(input.isKeyPressed(Input.KEY_W))
		{
			hero.moveUp(delta);
		}
		else if(input.isKeyPressed(Input.KEY_S))
		{
			hero.moveDown(delta);
		}
		else if(input.isKeyPressed(Input.KEY_A))
		{
			hero.moveLeft(delta);
		}
		else if(input.isKeyPressed(Input.KEY_D))
		{
			hero.moveRight(delta);
		}
		
		if(input.isKeyPressed(Input.KEY_SPACE))
		{
			hero.jump(delta);
		}
		
		if(input.isKeyPressed(Input.KEY_LSHIFT))
		{
			System.out.println("Especial");
		}
		
		if(input.isKeyPressed(Input.KEY_UP))
		{
			Shot shot = new Shot(hero.getPosition(), hero.getDir());
			shot.setShooterType(Projectile.TYPE_HERO);
			shots.add(shot);
		}
		
		for(int i=0; i < enemies.size(); i++)
		{
			if(counter >= 1000)
			{
				Shot shot = new Shot(enemies.get(i).getPosition(), enemies.get(i).getDir(), new Image("/images/shot2.png"));
				shot.setShooterType(Projectile.TYPE_ENEMY);
				shot.setSpeed(0.5f);
				shots.add(shot);
				counter = 0;
			}
		}
		
		detectCollisions();
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
	
	private void detectCollisions()
	{
		for(int i=0; i < shots.size(); i++)
		{
			if(shots.get(i).getShooterType() == Projectile.TYPE_HERO)
			{
				for(int j=0; j < enemies.size(); j++)
				{
					if(shots.get(i).collidesWith(enemies.get(j)))
					{
						enemies.remove(j--);
						shots.remove(i--);
					}
				}
			}
			else
			{
				if(shots.get(i).collidesWith(hero))
				{
					lifebar.removeHp(1);
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
