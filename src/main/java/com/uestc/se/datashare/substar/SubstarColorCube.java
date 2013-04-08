package com.uestc.se.datashare.substar;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.uestc.se.basic.ui.CompositGraphic;
import com.uestc.se.basic.ui.Point;

public class SubstarColorCube extends CompositGraphic{

	private static final float CUBE_TEXT_GAP = 5.0f;
	
	private Point bottomLeft, topRight;
	private Color bottomColor, topColor;
	
	static float getCubeTextGap(){
		return CUBE_TEXT_GAP;
	}
	
	private SubstarColorCube(Builder builder) {
		bottomLeft = builder.bottomLeft;
		topRight = builder.topRight;
		bottomColor = builder.bottomColor;
		topColor = builder.topColor;
	}
	
	public static class Builder{
		
		private Point bottomLeft, topRight;
		private Color bottomColor, topColor;
		
		public Builder(Point bottomLeft, Point topRight) {
			this.bottomLeft = bottomLeft;
			this.topRight = topRight;
		}
		
		public Builder color(Color bottomColor, Color topColor){
			this.bottomColor = bottomColor;
			this.topColor = topColor;
			return this;
		}
		
		public SubstarColorCube build(){
			return new SubstarColorCube(this);
		}
		
	}
	
	@Override
	public void draw() {
		super.draw();
		GL11.glBegin(GL11.GL_QUADS);
			// Bottom left
			GL11.glColor3f(bottomColor.r, bottomColor.g, bottomColor.b);	GL11.glVertex2d(bottomLeft.x, bottomLeft.y);			
			// Top left
			GL11.glColor3f(topColor.r, topColor.g, topColor.b);				GL11.glVertex2d(bottomLeft.x, topRight.y);			
			// Top right
			GL11.glColor3f(topColor.r, topColor.g, topColor.b);				GL11.glVertex2d(topRight.x, topRight.y);			
			// Bottom right
			GL11.glColor3f(bottomColor.r, bottomColor.g, bottomColor.b);	GL11.glVertex2d(topRight.x, bottomLeft.y);			
		GL11.glEnd();
		
	}

}
