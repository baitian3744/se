package com.uestc.se.basic.io;

import java.awt.Font;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

public class TextPrinter {
	
//	public static UnicodeFont defaultFont;
	public static TrueTypeFont trueTypeFont;
	
	private TextPrinter() {
		// TODO throw an exception @see: Effective Java, p.19
	}
	
	static{
		
//		// Chinese font
//		defaultFont = new UnicodeFont(new Font("������", Font.PLAIN, 16));
//				
//		defaultFont.addAsciiGlyphs();
//		defaultFont.addGlyphs(0x4E00, 0x9FBB);
//		defaultFont.getEffects().add(new ColorEffect(java.awt.Color.black));
//		try {
//			defaultFont.loadGlyphs();
//		} catch (SlickException e1) {
//			e1.printStackTrace();
//		}
//		
		// English true type font
		Font awtFont = new Font("Times New Roman", Font.PLAIN, 16);
		trueTypeFont = new TrueTypeFont(awtFont, true);
		
	}
	
//	public static UnicodeFont getDefaultFont(){
//		return defaultFont;
//	}
//	
//	public static void print(float x, float y, String str){
//		GL11.glEnable(GL11.GL_BLEND);
//		defaultFont.drawString(x, y, str);
//	}
//	
//	public static void printCenter(float x, float y, String str){
//		GL11.glPushMatrix();
//			GL11.glEnable(GL11.GL_TEXTURE_2D);
//			GL11.glEnable(GL11.GL_BLEND);
//			GL11.glTranslatef(-(float)defaultFont.getWidth(str)/2, -defaultFont.getHeight(str)/2, 0);
//			defaultFont.drawString(x, y, str);
//		GL11.glPopMatrix();
//	}
	
//	public static void printCenter(float x, float y, String str, Color color){
//		GL11.glPushMatrix();
//			GL11.glEnable(GL11.GL_TEXTURE_2D);
//			GL11.glEnable(GL11.GL_BLEND);
//			GL11.glTranslatef(-(float)defaultFont.getWidth(str)/2, -defaultFont.getHeight(str)/2, 0);
//			defaultFont.drawString(x, y, str, color);
//		GL11.glPopMatrix();
//	}
	
	public static void eprint(float x, float y, String str){
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			trueTypeFont.drawString(x, y, str);
		GL11.glPopMatrix();
	}
	
	public static void eprint(float x, float y, String str, Color color){
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			trueTypeFont.drawString(x, y, str, color);
		GL11.glPopMatrix();
	}
	
	public static void eprintBtmLeft(float x, float y, String str, Color color){
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glTranslatef(0.0f, -(float)trueTypeFont.getHeight(str), 0);
			trueTypeFont.drawString(x, y, str, color);
		GL11.glPopMatrix();
	}
	
	public static void eprintCentTop(float x, float y, String str, Color color){
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glTranslatef(-(float)trueTypeFont.getWidth(str)/2, 0.0f, 0);
			trueTypeFont.drawString(x, y, str, color);
		GL11.glPopMatrix();
	}
	
	public static void eprintCentRight(float x, float y, String str, Color color){
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glTranslatef(-(float)trueTypeFont.getWidth(str), -(float)trueTypeFont.getHeight(str)/2, 0);
			trueTypeFont.drawString(x, y, str, color);
		GL11.glPopMatrix();
	}
	
	public static void eprintBtmRight(float x, float y, String str, Color color){
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glTranslatef(-(float)trueTypeFont.getWidth(str), -(float)trueTypeFont.getHeight(str), 0);
			trueTypeFont.drawString(x, y, str, color);
		GL11.glPopMatrix();
	}
	
	public static void eprintCenter(float x, float y, String str){
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glTranslatef(-(float)trueTypeFont.getWidth(str)/2, -(float)trueTypeFont.getHeight(str)/2, 0);
			trueTypeFont.drawString(x, y, str, Color.black);
		GL11.glPopMatrix();
	}
	
	public static void eprintCenter(float x, float y, String str, Color color){
		GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glTranslatef(-(float)trueTypeFont.getWidth(str)/2, -(float)trueTypeFont.getHeight(str)/2, 0);
			trueTypeFont.drawString(x, y, str, color);
		GL11.glPopMatrix();
	}
	
	
}
