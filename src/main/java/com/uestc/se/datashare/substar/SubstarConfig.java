package com.uestc.se.datashare.substar;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import org.newdawn.slick.Color;

public class SubstarConfig {
	// TODO singleton?
	
	// Attributes
	//*******************************************************************
	
	// Render related
	//-------------------------------------------------------------------
	// Window
	private int windowHeight;
	private int windowWidth;
	private Color backgoundColor = Color.white;
	private boolean isBackgoundTextureEnabled;
	private String backgroundTexture;
	
	// Plot
	private float plotPointSize;
	
	private SubstarConfig() {
		
	}
	
	public static SubstarConfig create(String src){
		SubstarConfigPaser parser;
		SubstarConfig config = new SubstarConfig();
		
		try {
			parser = new SubstarConfigPaser(src);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		config.windowWidth = parser.parseToInt(SubstarConfigPaser.TREE_PATH_WIDTH);
		config.windowHeight = parser.parseToInt(SubstarConfigPaser.TREE_PATH_HEIGHT);
		config.backgoundColor = parser.parseToColor(SubstarConfigPaser.TREE_PATH_BACKGROUND_COLOR);
		config.isBackgoundTextureEnabled = parser.parseEanbled(SubstarConfigPaser.TREE_PATH_BACKGROUND_TEXTURE);
		config.backgroundTexture = parser.parseToString(SubstarConfigPaser.TREE_PATH_BACKGROUND_TEXTURE_PATH);
		
		config.plotPointSize = parser.parseToFloat(SubstarConfigPaser.TREE_PATH_PLOT_POINTSIZE);
		return config;
	}
	
	public static SubstarConfig create(URL src){
		SubstarConfigPaser parser;
		SubstarConfig config = new SubstarConfig();
		
		try {
			parser = new SubstarConfigPaser(src);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		config.windowWidth = parser.parseToInt(SubstarConfigPaser.TREE_PATH_WIDTH);
		config.windowHeight = parser.parseToInt(SubstarConfigPaser.TREE_PATH_HEIGHT);
		config.backgoundColor = parser.parseToColor(SubstarConfigPaser.TREE_PATH_BACKGROUND_COLOR);
		config.isBackgoundTextureEnabled = parser.parseEanbled(SubstarConfigPaser.TREE_PATH_BACKGROUND_TEXTURE);
		config.backgroundTexture = parser.parseToString(SubstarConfigPaser.TREE_PATH_BACKGROUND_TEXTURE_PATH);
		
		config.plotPointSize = parser.parseToFloat(SubstarConfigPaser.TREE_PATH_PLOT_POINTSIZE);
		return config;
	}

	public static SubstarConfig create(InputStream in){
		SubstarConfigPaser parser;
		SubstarConfig config = new SubstarConfig();
		
		try {
			parser = new SubstarConfigPaser(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		
		config.windowWidth = parser.parseToInt(SubstarConfigPaser.TREE_PATH_WIDTH);
		config.windowHeight = parser.parseToInt(SubstarConfigPaser.TREE_PATH_HEIGHT);
		config.backgoundColor = parser.parseToColor(SubstarConfigPaser.TREE_PATH_BACKGROUND_COLOR);
		config.isBackgoundTextureEnabled = parser.parseEanbled(SubstarConfigPaser.TREE_PATH_BACKGROUND_TEXTURE);
		config.backgroundTexture = parser.parseToString(SubstarConfigPaser.TREE_PATH_BACKGROUND_TEXTURE_PATH);
		
		config.plotPointSize = parser.parseToFloat(SubstarConfigPaser.TREE_PATH_PLOT_POINTSIZE);
		return config;
	}
	
	public String getFileName(int index) {
		return null;
	}
	
	public int getWindowHeight(){
		return this.windowHeight;
	}
	
	public int getWindowWidth(){
		return this.windowWidth;
	}
	
	public Color getBackgoundColor(){
		return this.backgoundColor;
	}
	
	public boolean isBackgroundTextureEnable(){
		return this.isBackgoundTextureEnabled;
	}
	
	public String getBackgroundTexture(){
		return this.backgroundTexture;
	}

	public float getPlotPointSize() {
		return this.plotPointSize;
	}
	
}

