package com.uestc.se.basic.ui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class QuadGraphic implements Graphic{

	private Point bottomLeft, topRight;
	private Color color;
	
	public QuadGraphic(Point btmLeft, Point topRight, Color color) {
		this.bottomLeft = btmLeft;
		this.topRight = topRight;
		this.color = color;
	}
	
	@Override
	public void draw() {
		
		GL11.glColor3f(color.r, color.g, color.b);
		GL11.glBegin(GL11.GL_QUADS);
		// Bottom left
		GL11.glVertex2f(bottomLeft.x, bottomLeft.y);			
		// Top left
		GL11.glVertex2f(bottomLeft.x, topRight.y);			
		// Top right
		GL11.glVertex2f(topRight.x, topRight.y);			
		// Bottom right
		GL11.glVertex2f(topRight.x, bottomLeft.y);			
	GL11.glEnd();
	}

	@Override
	public void add(Graphic graphic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Graphic grahpic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Graphic getChild(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
