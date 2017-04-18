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
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;

import br.edu.iftm.controllers.Window;
import br.edu.iftm.models.Personagem;
import br.edu.iftm.models.Tiro;

public class Game extends BasicGame{
	
	private Command attack = new BasicCommand("attack");
	private Command special = new BasicCommand("special");
	private Command jump = new BasicCommand("jump");
	private Command up = new BasicCommand("up");
	private Command down = new BasicCommand("down");
	private Command left = new BasicCommand("left");
	private Command right = new BasicCommand("right");
	
	public Image bg;
	private Personagem hero;
	//private Tiro shot;
	private ArrayList<Tiro> shots;
	
	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		bg.draw();	
		for (Tiro shot : shots) 
		{
			shot.draw();
		}
		hero.draw();
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		shots = new ArrayList<Tiro>();
		bg = new Image("/images/bg.png");
		hero = new Personagem(new SpriteSheet("/images/char.png", 48, 48), 100, 100);
		//shot = new Tiro(hero.getPosition(), hero.getDir());
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// 48 
		Input input = container.getInput();
		input.enableKeyRepeat();
		
		for (Tiro shot : shots) 
		{
			shot.moveShot(delta);
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
			System.out.println("Atirou");
			Tiro shot = new Tiro(hero.getPosition(), hero.getDir());
			shots.add(shot);
		}
		
		
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer ap = new AppGameContainer(new Game("Teste"));
		ap.setDisplayMode(Window.WIDTH, Window.HEIGHT, false);
		ap.setTargetFrameRate(Window.FPS);
		ap.start();
	}

}
