package br.edu.iftm.models.entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;

import br.edu.iftm.controllers.Window;

public class Shot extends Projectile{
	
	public Shot(Point heroPos, int dir, Image image) throws SlickException {
		super(heroPos, image, dir);
	}
	
	
	public Shot(Point heroPos, int dir) throws SlickException {
		super(heroPos, new Image("/images/shot.png"), dir);
	}
	
	
	
}
