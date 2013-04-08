package com.uestc.se.datashare.substar;

import java.io.InputStream;
import java.net.URL;

import org.lwjgl.opengl.GL11;

import com.uestc.se.applet.ModelAbs;
import com.uestc.se.basic.ui.Point;
import com.uestc.se.util.ResourceLoader;

public class SubstarModel extends ModelAbs{
	
	private SubstarConfig config;
	private SubstarData data;
	private SubstarFigure figure;
	private String configSource;
	
	private final String webappString = "demo";

	@Override
	public boolean init() {
		try {
			URL url;
			if((url = createUrl(configSource)) == null){
				return false;
			}
			// eg. http://localhost:8080/demo/config_default.xml
			String configFileString = "http://"
					+ url.getHost()
					+ ":" 
					+ Integer.toString(url.getPort())
					+ "/" 
					+ webappString 
					+ "/" 
					+ "config_default.xml";
			InputStream in = ResourceLoader.getResourceAsStream(configFileString);
			if(in != null){
				this.config = SubstarConfig.create(in);
			}
			this.data = SubstarData.create(configSource, config);
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
	
	private URL createUrl(String urlString){
		try {
			URL url = new URL(urlString);
			return url;
		} catch (Exception e) {
			printErr("Source path is not a url path in createData(), src = " + urlString);
			return null;
		}
	}
	
	private void printErr(String errMsg){
		System.out.println("Err@SubstarModel: " + errMsg);
	}
}
