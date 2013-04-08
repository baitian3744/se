package com.uestc.se.datashare.substar;

import java.io.InputStream;
import com.uestc.se.applet.ModelAbs;
import com.uestc.se.basic.ui.Point;
import com.uestc.se.util.ResourceLoader;

public class SubstarModel extends ModelAbs{
	
	private SubstarConfig config;
	private SubstarData data;
	private SubstarFigure figure;
	private String configSource;

	@Override
	public void init() {
		
		InputStream in = ResourceLoader.getResourceAsStream("http://localhost:8080/demo/config_default.xml");
		if(in != null){
			this.config = SubstarConfig.create(in);
		}
		System.out.println("this.config.getFileName:" + this.config.getFileName(0));
		System.out.println("this.config.getPlotPointSize:" + this.config.getPlotPointSize());
		System.out.println("this.config.getWindowHeight:" + this.config.getWindowHeight());
		System.out.println("this.config.getWindowWidth:" + this.config.getWindowWidth());
		
		this.data = SubstarData.create("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990519.txt.todzkd.txt", config);
		this.figure = new SubstarFigure(data, new Point(20.0f, 30.0f, 0.0f));
		
	}

	@Override
	public void render() {
		this.figure.draw();
		
	}
	
	public SubstarModel(String configSource) {
		this.configSource = configSource;
	}
	
	public SubstarModel(InputStream in){
		
	}
	
	

}
