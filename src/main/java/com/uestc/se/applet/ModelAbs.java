package com.uestc.se.applet;

public abstract class ModelAbs implements Model{
	
	protected int height = 240, width = 440;
	
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}
}
