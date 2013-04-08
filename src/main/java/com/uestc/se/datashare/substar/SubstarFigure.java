package com.uestc.se.datashare.substar;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.uestc.se.basic.ui.CompositGraphic;
import com.uestc.se.basic.ui.DashLineGraphic;
import com.uestc.se.basic.ui.Graphic;
import com.uestc.se.basic.ui.Point;
import com.uestc.se.basic.ui.SolidLineGraphic;
import com.uestc.se.basic.ui.TextGraphic;
import com.uestc.se.basic.ui.coordinate.Coordinate;
import com.uestc.se.datashare.substar.SubstarData.Track;

public class SubstarFigure extends CompositGraphic{
	
	private SubstarData data;
	private Coordinate coordinate;
	private Point origin;
	
	private Graphic mainTitle;
	private Graphic satelliteIDTitle;
	private Graphic dataLevelTitle;
	private Graphic startTimeTitle;
	private Graphic endTimeTitle;
	private List<Track> tracks;
	
	public SubstarFigure(SubstarData data, Point origin) {
		super();
		this.data = data;
		this.origin = origin;
		
		// Construct figure
		this.mainTitle = new TextGraphic(this.data.getEnergyLabel(), new Point(180.0f, 185.0f), Color.black);
		this.add(this.mainTitle);
		this.satelliteIDTitle = new TextGraphic("SatelliteID: " + this.data.getSatelliteName(), new Point(0.0f, 215.0f), Color.black);
		this.add(this.satelliteIDTitle);
		this.dataLevelTitle = new TextGraphic("Data_Level: " + this.data.getDataLevel(), new Point(0.0f, 205.0f), Color.black);
		this.add(this.dataLevelTitle);
		this.startTimeTitle = new TextGraphic("Start_Time: " + this.data.getStartTime(), new Point(0.0f, 195.0f), Color.black);
		this.add(this.startTimeTitle);
		this.endTimeTitle = new TextGraphic("End_Time: " + this.data.getEndTime(), new Point(0.0f, 185.0f), Color.black);
		this.add(this.endTimeTitle);
		
		this.add(getColorBar());
		
		this.tracks = data.getTracks();
		for(Track tr : tracks){
			this.add(tr);
		}

		coordinate = new Coordinate.Builder(
				new Point(0.0f, 0.0f, 0.0f),
				new Point(360.0f, 0.0f, 0.0f),
				new Point(0.0f, 180.0f, 0.0f))
			.xValueRange(-180, 180, 20)
			.yValueRange(-90, 90, 30)
			.color(Color.black)
			.build();
		this.add(coordinate);
		
//		this.add(new SolidLineGraphic(new Point(0.0f, 180.0f, 0.0f), new Point(360.0f, 180.0f, 0.0f), Color.black));
		this.add(new DashLineGraphic(new Point(0.0f, 180.0f, 0.0f), new Point(360.0f, 180.0f, 0.0f), Color.black, 10.0f));
		this.add(new SolidLineGraphic(new Point(360.0f, 0.0f, 0.0f), new Point(360.0f, 180.0f, 0.0f), Color.black));
		
		this.add(new TextureGraphic(new Point(0.0f, 0.0f, 0.0f), 
				new Point(360.0f, 180.0f, 0.0f), 
				this.data.getBackgroundTexture())
				);
		
	}
	
	private Graphic getColorBar(){
		if(this.data.isProton()){
			return new SubstarColorbar.Builder(
					new Point(370.0f, 0.0f), 
					new Point(380.0f, 180.0f))
					.addColor(new Color(0.0f, 0.0f, 1.0f))
					.addColor(new Color(0.0f, 1.0f, 1.0f))
					.addColor(new Color(0.0f, 1.0f, 0.0f))
					.addColor(new Color(1.0f, 1.0f, 0.0f))
					.addColor(new Color(1.0f, 0.0f, 0.0f))
					.addComment("1E1")
					.addComment("1E2")
					.addComment("1E3")
					.addComment("1E4")
					.build();
		}else{
			return new SubstarColorbar.Builder(
					new Point(370.0f, 0.0f), 
					new Point(380.0f, 180.0f))
					.addColor(new Color(0.0f, 0.0f, 1.0f))
					.addColor(new Color(0.0f, 1.0f, 1.0f))
					.addColor(new Color(0.0f, 1.0f, 0.0f))
					.addColor(new Color(1.0f, 1.0f, 0.0f))
					.addColor(new Color(1.0f, 0.0f, 0.0f))
					.addComment("1E1")
					.addComment("1E2")
					.addComment("1E3")
					.addComment("1E4")
					.addComment("1E5")
					.build();
		}
	}
	
	@Override
	public void draw() {
		GL11.glPushMatrix();
			GL11.glTranslatef(origin.x, origin.y, origin.z);
			super.draw();
		GL11.glPopMatrix();
	}
}
