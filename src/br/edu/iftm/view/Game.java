package br.edu.iftm.view;

import java.util.ArrayList;

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
import br.edu.iftm.models.entities.LifeBar;
import br.edu.iftm.models.entities.Shot;

public class Game extends BasicGame{

	public Image bg;
	private Character hero;
	private ArrayList<Shot> shots;
	private LifeBar lifebar;
	
	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		bg.draw();	
		for (Shot shot : shots) 
		{
			shot.draw();
		}
		hero.draw();
		lifebar.draw();
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		shots = new ArrayList<Shot>();
		bg = new Image("/images/bg.png");
		hero = new Character(100, 100, new SpriteSheet("/images/char.png", 48, 48));
		hero.defineLimWidthSprite(10);
		hero.defineLimHeightSprite(2);
		lifebar = new LifeBar();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		System.out.println("HP: " + lifebar.getHp() + "/" + lifebar.getHpMax());
		
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
			shots.add(shot);
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
