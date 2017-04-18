package br.edu.iftm.view;

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
import br.edu.iftm.models.Personagem;

public class Game extends BasicGame{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int FPS = 60;
	private Command attack = new BasicCommand("attack");
	private Command special = new BasicCommand("special");
	private Command jump = new BasicCommand("jump");
	private Command up = new BasicCommand("up");
	private Command down = new BasicCommand("down");
	private Command left = new BasicCommand("left");
	private Command right = new BasicCommand("right");
	
	public Image fundo;
	
	private Personagem hero;
	
	public Game(String title) {
		super(title);
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		//hero.getSprite((int)coordHero.getX(), (int)coordHero.getY()).draw();
		hero.draw();
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		fundo = new Image("/images/bg.png");
		hero = new Personagem(new SpriteSheet("/images/char.png", 48, 48), 0, 0);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		// 48 
		Input input = container.getInput();
		input.enableKeyRepeat();
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
			
		}
		
		if(input.isKeyPressed(Input.MOUSE_LEFT_BUTTON))
		{
			
		}
		
		
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer ap = new AppGameContainer(new Game("Teste"));
		ap.setDisplayMode(WIDTH, HEIGHT, false);
		ap.setTargetFrameRate(FPS);
		ap.start();
	}

}
