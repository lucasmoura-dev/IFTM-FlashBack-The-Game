package br.edu.iftm.controllers;

import org.newdawn.slick.geom.Point;

import br.edu.iftm.view.Game;

public class Window {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int FPS = 59;
	
	public static boolean isInside(Point position, int widthObj, int heightObj)
	{
		if(position.getX() >= 0 && position.getX() < WIDTH - widthObj)
		{
			return(position.getY() >= 0 && position.getY() < HEIGHT - heightObj);
		}
		else
		{
			return false;
		}
	}
}
