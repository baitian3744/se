package com.uestc.se.basic.ui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class SolidLineGraphic implements Graphic{

	private Point start, end;
	private Color color;
	
	public SolidLineGraphic(Point start, Point end) {
		this(start, end, Color.white);
	}
	
	public SolidLineGraphic(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
	}
	
	@Override
	public void draw() {
		
		// Draw the line
		GL11.glColor3f(color.r, color.g, color.b);
		GL11.glBegin(GL11.GL_LINE_STRIP);
			GL11.glVertex3f(start.x, start.y, start.z);
			GL11.glVertex3f(end.x, end.y, end.z);
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
