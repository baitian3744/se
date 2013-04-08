package com.uestc.se.basic.ui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.uestc.se.tool.MathTool;

public class DashLineGraphic implements Graphic{

	private Point start, end;
	private Color color;
	private float density = 1.0f;
	
	public DashLineGraphic(Point start, Point end, Color color, float density) {
		this.start = start;
		this.end = end;
		this.color = color;
		this.density = density;
	}
	
	@Override
	public void draw() {

		GL11.glColor3f(color.r, color.g, color.b);
		
		// Render the line with the color
		float[] v = {end.x-start.x, end.y-start.y, end.z-start.z};
		float length = MathTool.modulo(v);
		int n = (int) (length/density);
		float xStep = (end.x - start.x)/n;
		float yStep = (end.y - start.y)/n;
		float zStep = (end.z - start.z)/n;
		for(int i = 0; i < n-1; i+=2){
			GL11.glBegin(GL11.GL_LINE_STRIP);
				GL11.glVertex3f(start.x+xStep*i, start.y+yStep*i, start.z+zStep*i);
				GL11.glVertex3f(start.x+xStep*(i+1), start.y+yStep*(i+1), start.z+zStep*(i+1));
			GL11.glEnd();
		}
		
	}

	@Override
	public void add(Graphic graphic) {
		
	}

	@Override
	public void remove(Graphic grahpic) {
		
	}

	@Override
	public Graphic getChild(int index) {
		return null;
	}

}
