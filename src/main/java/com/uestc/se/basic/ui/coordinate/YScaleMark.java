package com.uestc.se.basic.ui.coordinate;

import org.newdawn.slick.Color;

import com.uestc.se.basic.ui.CompositGraphic;
import com.uestc.se.basic.ui.Graphic;
import com.uestc.se.basic.ui.Point;
import com.uestc.se.basic.ui.SolidLineGraphic;

class YScaleMark extends CompositGraphic{
	
	private static final int STICK_LENGTH = 3;
	
	// Attributes
	//******************************************************************
	private Point position;
	private int value;
	private Color color;
	private Graphic stick;
	private Graphic text;
	
	// Getter & setter
	//------------------------------------------------------------------
	Point getPosition(){
		return this.position;
	}
	
	int getValue(){
		return this.value;
	}
	
	Color getColor(){
		return this.color;
	}
	
	// Constructor
	//*****************************************************************
	public YScaleMark(Point pos, int value) {
		this(pos, value, Color.white);
	}
	
	public YScaleMark(Point pos, int value, Color color) {
		this.position = pos;
		this.value = value;
		this.color = color;
		this.stick = new SolidLineGraphic(
				new Point(pos.x, pos.y, pos.z), 
				new Point(pos.x - STICK_LENGTH, pos.y, pos.z), 
				color
				);
		this.text = new YMarkText(Integer.toString(value), new Point(pos.x - 2*STICK_LENGTH, pos.y, pos.z), color);
		
		this.add(this.stick);
		this.add(this.text);
	}
	
	// Control method
	//*****************************************************************
	public YScaleMark nextMark(float yStep, int valueStep){
		return new YScaleMark(
				getPosition().shiftY(yStep), 
				getValue() + valueStep, 
				getColor()
				);
	}
	
	public boolean isBetween(Point start, Point end){
		return this.getPosition().yIsBetween(start, end);
	}
}
