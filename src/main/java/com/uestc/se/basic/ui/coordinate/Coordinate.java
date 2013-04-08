/**
 * 
 */
package com.uestc.se.basic.ui.coordinate;

import org.newdawn.slick.Color;

import com.uestc.se.basic.ui.CompositGraphic;
import com.uestc.se.basic.ui.Point;
/**
 * @author luchen
 *
 */
public class Coordinate extends CompositGraphic{
	
	private YAxis leftyAxis;
	private XAxis bottomxAxis;

	private Coordinate(Builder builder){
		this.bottomxAxis = builder.bottomxAxis;
		this.leftyAxis = builder.leftyAxis;
		this.add(this.bottomxAxis);
		this.add(this.leftyAxis);
	}

	public static class Builder{

		private YAxis leftyAxis;
		private XAxis bottomxAxis;

		private Point origin;
		private Point xEnd, yEnd;
		private int xStartValue, xEndValue, xStep;
		private int yStartValue, yEndValue, yStep;
		private Color color;

		public Builder(Point origin, Point xEnd, Point yEnd){
			this.origin = origin;
			this.xEnd = xEnd;
			this.yEnd = yEnd;
		}

		public Builder xValueRange(int startValue, int endValue, int step){
			this.xStartValue = startValue;
			this.xEndValue = endValue;
			this.xStep = step;
			return this;
		}

		public Builder yValueRange(int startValue, int endValue, int step){
			this.yStartValue = startValue;
			this.yEndValue = endValue;
			this.yStep = step;
			return this;
		}

		public Builder color(Color color){
			this.color = color;
			return this;
		}

		public Coordinate build(){
			this.bottomxAxis = new XAxis.Builder(this.origin, this.xEnd)
					.color(this.color)
					.valueRange(this.xStartValue, this.xEndValue, this.xStep)
					.build();
			this.leftyAxis = new YAxis.Builder(this.origin, this.yEnd)
					.color(this.color)
					.valueRange(this.yStartValue, this.yEndValue, this.yStep)
					.build();
			return new Coordinate(this);
		}
	}
}
