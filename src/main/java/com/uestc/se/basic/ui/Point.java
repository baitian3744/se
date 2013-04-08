package com.uestc.se.basic.ui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Point implements Graphic{
	
	public float x, y, z;
	private Color color = Color.black;
	private float size = 1.0f;
	
//	public void setColor(float r, float g, float b, float a){
//		this.color.r = r;
//		this.color.g = g;
//		this.color.b = b;
//		this.color.a = a;
//	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	public void setSize(float size){
		this.size = size;
	}
	
	public Point(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point (float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Point shiftX(float offset){
		return new Point(this.x + offset, this.y, this.z);
	}
	
	public Point shiftY(float offset){
		return new Point(this.x, this.y + offset, this.z);
	}
	
	public boolean xIsBetween(Point start, Point end){
		if(this.x > start.x){
			if(this.x < end.x)
				return true;
		}
		return false;
	}

	public boolean yIsBetween(Point start, Point end) {
		if(this.y > start.y){
			if(this.y < end.y)
				return true;
		}
		return false;
	}

	@Override
	public void draw() {
//		GL11.glPointSize(3);
		GL11.glPointSize(size);
		GL11.glBegin(GL11.GL_POINTS);
			GL11.glColor4f(color.r, color.g, color.b, color.a);
			GL11.glVertex3f(this.x, this.y, this.z);
		GL11.glEnd();
		GL11.glPointSize(1.0f);
		
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
