package com.uestc.se.datashare.substar;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import com.uestc.se.basic.ui.Graphic;
import com.uestc.se.basic.ui.Point;

public class TextureGraphic implements Graphic{

	private Point bottomLeft, topRight;
	private Texture texture;
	
	public TextureGraphic(Point btmLeft, Point topRight, String src) {
		this.bottomLeft = btmLeft;
		this.topRight = topRight;
		
		try {
			this.texture = TextureLoader.getTexture("JPG", ResourceLoader.getResourceAsStream(src),false);
		} catch (IOException e) {
			e.printStackTrace();
			this.texture = null;
		}
	}
	
	@Override
	public void draw() {
		
		if(this.texture != null){
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			Color.white.bind();
			this.texture.bind();
	    	GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex2f(this.bottomLeft.x, this.bottomLeft.y);
				// GL11.glVertex2f(0.0f, 0.0f);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex2f(this.bottomLeft.x, this.topRight.y);
				// GL11.glVertex2f(1.0f, 0.0f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex2f(this.topRight.x, this.topRight.y);
				// GL11.glVertex2f(1.0f, 1.0f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex2f(this.topRight.x, this.bottomLeft.y);
				//GL11.glVertex2f(0.0f, 1.0f);
	    	GL11.glEnd();
		}
		
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
