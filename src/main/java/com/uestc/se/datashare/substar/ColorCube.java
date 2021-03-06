package com.uestc.se.datashare.substar;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.uestc.se.basic.ui.CompositGraphic;
import com.uestc.se.basic.ui.Point;
import com.uestc.se.basic.ui.TextGraphic;

public class ColorCube extends CompositGraphic{

	private static final float CUBE_TEXT_GAP = 5.0f;
	private Point bottomLeft, topRight;
	private Color bottomColor, topColor;
	
	static float getCubeTextGap(){
		return CUBE_TEXT_GAP;
	}
	
	private ColorCube(Builder builder) {
		bottomLeft = builder.bottomLeft;
		topRight = builder.topRight;
		bottomColor = builder.bottomColor;
		topColor = builder.topColor;
		this.add(new TextGraphic(builder.comment, new Point(topRight.x + CUBE_TEXT_GAP, bottomLeft.y, bottomLeft.z), builder.textColor));
//		this.add(graphic)
	}
	
	public static class Builder{
		
		private String comment;
		private Point bottomLeft, topRight;
		private Color bottomColor, topColor;
		private Color textColor;
		
		public Builder(Point bottomLeft, Point topRight) {
			this.bottomLeft = bottomLeft;
			this.topRight = topRight;
		}
		
		public Builder comment(String text, Color color){
			this.comment = text;
			this.textColor = color;
			return this;
		}
		
		public Builder color(Color bottomColor, Color topColor){
			this.bottomColor = bottomColor;
			this.topColor = topColor;
			return this;
		}
		
		public ColorCube build(){
			return new ColorCube(this);
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
