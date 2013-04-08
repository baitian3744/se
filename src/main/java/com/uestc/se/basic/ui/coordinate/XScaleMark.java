package com.uestc.se.basic.ui.coordinate;

import org.newdawn.slick.Color;

import com.uestc.se.basic.ui.CompositGraphic;
import com.uestc.se.basic.ui.Graphic;
import com.uestc.se.basic.ui.Point;
import com.uestc.se.basic.ui.SolidLineGraphic;

class XScaleMark extends CompositGraphic{
	
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
	public XScaleMark(Point pos, int value) {
		this(pos, value, Color.white);
	}
	
	public XScaleMark(Point pos, int value, Color color) {
		this.position = pos;
		this.value = value;
		this.color = color;
		this.stick = new SolidLineGraphic(
				new Point(pos.x, pos.y, pos.z), 
				new Point(pos.x, pos.y - STICK_LENGTH, pos.z), 
				color
				);
		this.text = new XMarkText(Integer.toString(value), new Point(pos.x, pos.y - STICK_LENGTH, pos.z), color);
		
		this.add(this.stick);
		this.add(this.text);
	}
	
	// Control method
	//*****************************************************************
	public XScaleMark nextMark(float xStep, int valueStep){
		return new XScaleMark(
				getPosition().shiftX(xStep), 
				getValue() + valueStep, 
				getColor()
				);
	}
	
	public boolean isBetween(Point start, Point end){
		return this.getPosition().xIsBetween(start, end);
	}
}
