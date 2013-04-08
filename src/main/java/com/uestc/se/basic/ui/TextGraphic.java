/**
 * 
 */
package com.uestc.se.basic.ui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.uestc.se.basic.io.TextPrinter;

/**
 * @author luchen
 *
 */
public class TextGraphic implements Graphic{
	
//	private boolean enable;
	private String text;
	private Color color;
	private Point position;
	private float fontSize = 1.0f;
	private float basicZoom = 0.5f;
	
	public String getText(){
		return this.text;
	}
	
	public TextGraphic(String txt) {
		this(txt, new Point(0.0f, 0.0f, 0.0f));
	}
	
	public TextGraphic(String txt, Point position){
		this(txt, position, Color.white);
	}
	
	public TextGraphic(String txt, Point position, Color color){
		this.text = txt;
		this.position = position;
		this.color = color;
	}

	@Override
	public void draw() {
		GL11.glPushMatrix();
	    	GL11.glDisable(GL11.GL_LIGHTING);
		    	GL11.glTranslatef(position.x, position.y, position.z);
		    	GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
		    	GL11.glScalef(basicZoom*fontSize, basicZoom*fontSize, basicZoom*fontSize);
		    	TextPrinter.eprintBtmLeft(0.0f, 0.0f, this.text, color);
	    	GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		
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
