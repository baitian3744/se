package com.uestc.se.basic.ui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class StripLineGraphic implements Graphic{
	
	
	private Point start, end, pointToDraw;
	private Color color;
	private ArrayList<Float> pointList;
	private float xVal, yVal;
	
	public StripLineGraphic(Point start, Point end) {
		this(start, end, Color.white);
	}
	
	public StripLineGraphic(Point start, Point end, Color color) {
		this.start = start;
		this.end = end;
		this.color = color;
	}
	
	public StripLineGraphic(ArrayList<Float> pointList, Color color) {
		this.pointList = pointList;
		this.color = color;
		this.xVal = 440.0f/pointList.size();
		this.yVal = 240.0f/(7.0f - (-7.0f));
	}
	
	private Point getpointToDraw(int index){
		
		Point tempPoint = new Point(index*xVal , ((float) Math.log10(pointList.get(index))+7.0f)*yVal, 0);
		return tempPoint;
	}
	
//	@Override
//	public void draw() {
//		// Draw the line
//		GL11.glColor3f(color.r, color.g, color.b);
//		GL11.glBegin(GL11.GL_LINE_STRIP);
//			GL11.glVertex3f(start.x, start.y, start.z);
//			GL11.glVertex3f(end.x, end.y, end.z);
//		GL11.glEnd();
//		
//	}

	@Override
	public void draw() {
		// Draw the line
		GL11.glColor3f(color.r, color.g, color.b);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		for(int i =0; i<pointList.size(); i++)
			GL11.glVertex3f(getpointToDraw(i).x, getpointToDraw(i).y, getpointToDraw(i).z);
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
