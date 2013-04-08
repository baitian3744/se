/**
 * 
 */
package com.uestc.se.datashare.substar;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;

import com.uestc.se.basic.ui.CompositGraphic;
import com.uestc.se.basic.ui.Point;
import com.uestc.se.basic.ui.TextGraphic;

/**
 * @author luchen
 *
 */
public class Colorbar extends CompositGraphic{
	
	private Point bottomLeft, topRight;
	private List<Color> colors;
	private List<String> comments;
	
	private Colorbar(Builder builder){
		this.bottomLeft = builder.bottomLeft;
		this.topRight = builder.topRight;
		this.colors = builder.colors;
		this.comments = builder.comments;
		
		float cubeHeight = (this.topRight.y - this.bottomLeft.y)/(this.colors.size()-1);	// TODO colors.size must > 1
		for(int i = 0; i < colors.size()-1; i++ ){
			Point newCubeBtmleft = new Point(bottomLeft.x, bottomLeft.y + i*cubeHeight);
			Point newCubeTopRight = new Point(topRight.x, bottomLeft.y + (i+1)*cubeHeight);
			ColorCube newColorCube = new ColorCube.Builder(newCubeBtmleft, newCubeTopRight)
					.color(this.colors.get(i), this.colors.get(i+1))
					.comment(comments.get(i), Color.black)
					.build();
			this.add(newColorCube);				
		}
		
		// Add the last comment
		this.add(new TextGraphic(
				comments.get(comments.size()-1), 
				new Point(topRight.x + ColorCube.getCubeTextGap(), topRight.y, topRight.z), 
				Color.black
				)
		);
	}
	
	public static class Builder{
		
		private Point bottomLeft, topRight;
		private List<Color> colors;
		private List<String> comments;
		
		public Builder(Point bottomLeft, Point topRight){
			this.bottomLeft = bottomLeft;
			this.topRight = topRight;
			colors = new ArrayList<Color>();
			comments = new ArrayList<String>();
		}
		
		public Builder addColorComment(Color color, String comment){
			this.colors.add(color);
			this.comments.add(comment);
			return this;
		}
		
		public Colorbar build(){
			return new Colorbar(this);
		}
	}
}
