package com.uestc.se.datashare.substar;

import java.io.InputStream;

import org.lwjgl.opengl.GL11;

import com.uestc.se.applet.ModelAbs;
import com.uestc.se.basic.ui.Point;
import com.uestc.se.util.ResourceLoader;

public class SubstarModel extends ModelAbs{
	
	private SubstarConfig config;
	private SubstarData data;
	private SubstarFigure figure;
	private String configSource;

	@Override
	public boolean init() {
		try {
			InputStream in = ResourceLoader.getResourceAsStream("http://localhost:8080/demo/config_default.xml");
			if(in != null){
				this.config = SubstarConfig.create(in);
			}
			this.data = SubstarData.create("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990519.txt.todzkd.txt", config);
			this.figure = new SubstarFigure(data, new Point(20.0f, 30.0f, 0.0f));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void render() {
		this.figure.draw();
		
	}
	
	private SubstarModel(String configSource) {
		this.configSource = configSource;
	}
	
	public static SubstarModel newInstance(String filePath){
		return new SubstarModel(filePath);
	}
}
