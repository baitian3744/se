package com.uestc.se.basic.data;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import com.uestc.se.util.ResourceLoader;

public class BasicDataFactory {
	
	public static final String[] SATELLITE_IDS = {
		"FY1C",
		"FY2D",
		"C10C"
	};
		
	private final String webappString = "demo";
	private final String dataXmlLocation = "BASIC-DATA-XML";
	
	private HashMap<String, BasicDataPaser> parserMap = new HashMap<String, BasicDataPaser>();
	
	/**
	 * 
	 * @param src currently only "http://" URL path is supported
	 * @return
	 */
	public BasicData createData(String src){
		
		if(!checkArgument(src)){
			return null;
		}
		
		URL url = createUrl(src);
		if(url == null){
			return null;
		}

		String configXmlName = getXmlFileName(src);
		if(configXmlName == null){
			return null;
		}
		
		String configFileString = "http://"
				+ url.getHost()
				+ ":" 
				+ Integer.toString(url.getPort())
				+ "/" 
				+ webappString 
				+ "/" 
				+ dataXmlLocation
				+ "/" 
				+ configXmlName;
		
		// Create parser
		BasicDataPaser parser = getParser(configFileString);
		if(parser == null){
			return null;
		}
		
		// Create data
		BasicData data = getData(src, parser);
		if(data == null){
			return null;
		}

		// Check data
		if(!getXmlFileName(src).split("\\.")[0].equals(data.getSatelliteName())){
			printErr("Data and xml file indicate different satellite");
			return null;
		}
		return data;
	}

	private BasicData getData(String src, BasicDataPaser parser) {
		
		InputStream sourceIn = ResourceLoader.getResourceAsStream(src);

		if(sourceIn != null){
			BasicData data = BasicData.create(sourceIn, parser);
			if(data != null){
				return data;
			}
			printErr("cannot create data with parser in createData(), src = " + src);
			return null;
		}else {
			printErr("cannot find src in createData(), src = " + src);
			return null;
		}
	}

	/**
	 * 
	 * @param configFileString
	 * @return return null if cannot create parser, or a parser
	 */
	private BasicDataPaser getParser(String configFileString) {
		
		if(this.parserMap.containsKey(configFileString)){
			return parserMap.get(configFileString);
			
		}else{
			InputStream parserIn = ResourceLoader.getResourceAsStream(configFileString);
			if(parserIn != null){
				BasicDataPaser parser = BasicDataPaser.create(parserIn);
				if(parser != null){
					this.parserMap.put(configFileString, parser);
					return parser;
				}
			}
			printErr("Cannot create a parser in createData(), configFileString = " + configFileString);
			return null;
		}
	}

	private boolean checkArgument(String arg){
		if(arg == null || "".equals(arg)){
			printErr("Invalid source path in createData(), src = null or \"\"");
			return false;
			
		}else{
			return true;
		}
	}

	private String getXmlFileName(String src) {
		
		for(String id : SATELLITE_IDS){
			if(src.contains(id)){
				return id + ".xml";
			}
		}
		printErr("Relative parser file cannot be found in createData, src = " + src);
		return null;
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
		System.out.println("Err@BasicDataFactory: " + errMsg);
	}
}
