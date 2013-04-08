package com.uestc.se.basic.ui.coordinate;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import com.uestc.se.basic.ui.CompositGraphic;
import com.uestc.se.basic.ui.Graphic;
import com.uestc.se.basic.ui.Point;
import com.uestc.se.basic.ui.SolidLineGraphic;

/**
 * @author luchen
 *
 */
class YAxis extends CompositGraphic{

	
	// Constructor
	//*****************************************************************
	private YAxis(Builder builder) {
		this.add(builder.axisLine);
		for (Graphic scaleMark : builder.scaleMarks) {
			this.add(scaleMark);
		}
		
	}
	
	// Builder
	//-----------------------------------------------------------------
	public static class Builder{
		private Point start, end;
		private int startValue, endValue;
		private int valueStep;
		private Graphic axisLine;
		private List<Graphic> scaleMarks;
		private Color color;
		
		public Builder(Point start, Point end){
			this.start = start;
			this.end = end;
			this.scaleMarks = new ArrayList<Graphic>();
		}
		
		public Builder color(Color color){
			this.color = color;
			return this;
		}
		
		public Builder valueRange(int startValue, int endValue, int valueStep){
			this.startValue = startValue;
			this.endValue = endValue;
			this.valueStep = valueStep;
			return this;
		}
		
		public YAxis build(){
			axisLine = new SolidLineGraphic(start, end, color);
			
			float yStep = valueStep * (end.y - start.y)/(endValue - startValue);
			YScaleMark currentMark = new YScaleMark(this.start, startValue, this.color);
			do {
				this.scaleMarks.add(currentMark);
				currentMark = currentMark.nextMark(yStep, valueStep);
				
			} while (currentMark.isBetween(this.start, this.end));
			this.scaleMarks.add(new YScaleMark(this.end, endValue, this.color));
			
			return new YAxis(this);
		}
		
	}
	
}
