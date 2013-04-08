package com.uestc.se.basic.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Several lines of data is not used but declared // TODO
 * @author luchen
 *
 */
public class BasicData {

	public static final String CONFIG_XML_ROOT_PATH = "res/data/config/";
	public static final String CONFIG_XML_SERVER_ROOT_PATH = "http://localhost:8080/demo/BASIC-DATA-XML";
	
	public static final String SATELLITE_FY1C = "FY1C";
	public static final String SATELLITE_FY2D = "FY2D";
	public static final String SATELLITE_C10C = "C10C";
	
	public class SingleLine{
		String utcTime;
		String localTime;
		float[] dataItem;
		
		private SingleLine(int dataTypes){
			dataItem = new float[dataTypes];
		}
		
		public String getUTCTime(){
			return this.utcTime;
		}
		
		public float getMember(int index){
			return this.dataItem[index];
		}
	}
	
	private String satelliteName;
	public String getSatelliteName() {
		return satelliteName;
	}

	private int contentRow;
	public int getContentRow() {
		return contentRow;
	}

	private int sumColumn;
	public int getSumColumn() {
		return sumColumn;
	}

	private int utcTimeHeadColumn;
	private int getUtcTimeHeadColumn() {
		return utcTimeHeadColumn;
	}


	private int getUtcTimeTailColumn() {
		return utcTimeTailColumn;
	}


	private int getLocalTimeHeadColumn() {
		return localTimeHeadColumn;
	}


	private int getLocalTimeTailColumn() {
		return localTimeTailColumn;
	}
	

	private int utcTimeTailColumn;
	private int localTimeHeadColumn;
	private int localTimeTailColumn;
	private int longitudeColumn;
	private int latitudeColumn;
	public int getLongitudeColumn() {
		return longitudeColumn;
	}


	public int getLatitudeColumn() {
		return latitudeColumn;
	}


	public int getAltitudeColumn() {
		return altitudeColumn;
	}

	private int altitudeColumn;
	private int protonStartColumn;
	public int getProtonStartColumn() {
		return protonStartColumn;
	}


	public int getElectronStartColumn() {
		return electronStartColumn;
	}

	private int protonColumns;
	private int electronStartColumn;
	private int electronColumns;
	private int otherStartColumn;
	private int otherColumns;
	private float badData;
	
	private List<String> protonEnergyLabels = new ArrayList<String>();
	private List<String> electronEnergyLabels = new ArrayList<String>();

	private List<SingleLine> dataLines = new ArrayList<SingleLine>();
	
	public SingleLine getRow(int index){
		return this.dataLines.get(index);
	}
	
	public int rows(){
		return this.dataLines.size();
	}
	
	
	private BasicData() {
		
	}
	
	
	public static BasicData create(String src){
		
		BasicDataPaser parser;
		BasicData data = new BasicData();
		
		try {
			parser = new BasicDataPaser(data.getXmlFileName(src));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		data.satelliteName = parser.parseToString(BasicDataPaser.TREE_PATH_SATELLITE_NAME);
		data.contentRow = parser.parseToInt(BasicDataPaser.TREE_PATH_CONTENT_ROW);
		data.sumColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_SUM_COLUMN);
		data.utcTimeHeadColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_UTCTIME_HEAD);
		data.utcTimeTailColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_UTCTIME_TAIL);
		data.localTimeHeadColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_LOCALTIME_HEAD);
		data.localTimeTailColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_LOCALTIME_TAIL);
		data.longitudeColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_POSITION_LONGITUDE);
		data.latitudeColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_POSITION_LATITUDE);
		data.altitudeColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_POSITION_ALTITUDE);
		data.protonStartColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_PROTON_FIRST_ITEM_COLUMN);
		data.protonColumns = parser.getNumOfChild(BasicDataPaser.TREE_PATH_PROTON);
		data.electronStartColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_ELECTRON_FIRST_ITEM_COLUMN);
		data.electronColumns = parser.getNumOfChild(BasicDataPaser.TREE_PATH_ELECTRON);
		data.otherStartColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_OTHER_FIRST_ITEM_COLUMN);
		data.otherColumns = parser.getNumOfChild(BasicDataPaser.TREE_PATH_OTHER);
		data.badData = parser.parseToFloat(BasicDataPaser.TREE_PATH_BAD_DATA);
		data.protonEnergyLabels = parser.getLabelChildren(BasicDataPaser.TREE_PATH_PROTON);
		data.electronEnergyLabels = parser.getLabelChildren(BasicDataPaser.TREE_PATH_ELECTRON);
		
		
		// Read data
		//--------------------------------------------------------------------------------
		FileReader fileReader = null;
		BufferedReader bufReader = null;
		Scanner scanner = null;
		
		// Try to open the file
		try {
			fileReader = new FileReader(src);
			bufReader = new BufferedReader(fileReader);
			scanner = new Scanner(bufReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Substar.Data.constructor(), File name invalid: " + src);
			// TODO throw exception
		}
		
		// Go to the first data row
		for(int i = 0; i < data.getContentRow()-1; i++){
			scanner.nextLine();
		}
		while (scanner.hasNext()) {

			String srcLine = scanner.nextLine().replaceAll(" +", " ").trim();
			SingleLine newLine = data.new SingleLine(data.getSumColumn());
			
			// Get utc time
			if(data.utcTimeHeadColumn > 0){
				newLine.utcTime = srcLine.split(" ")[data.getUtcTimeHeadColumn()-1] 
						+ " "
						+ srcLine.split(" ")[data.getUtcTimeTailColumn()-1];
			}
			
			if(data.localTimeHeadColumn > 0){
				newLine.localTime = srcLine.split(" ")[data.getLocalTimeHeadColumn()-1]
						+ " "
						+ srcLine.split(" ")[data.getLocalTimeTailColumn()-1];
			}
			int firstDataColumn = 
					(data.utcTimeHeadColumn > 0 ? 1 : 0)
					+ (data.utcTimeTailColumn > 0 ? 1 : 0)
					+ (data.localTimeHeadColumn > 0 ? 1 : 0)
					+ (data.localTimeTailColumn > 0 ? 1 : 0);
			for(int i = firstDataColumn; i < data.getSumColumn(); i++){
				newLine.dataItem[i] = Float.parseFloat(srcLine.split(" ")[i]);
			}
			data.dataLines.add(newLine);
		}
		scanner.close();
		
		return data;
	}

	
	public static BasicData create(URL src){
		
		BasicDataPaser parser;
		BasicData data = new BasicData();
		
		try {
			URL parserUrl = data.getXmlUrlFileName(src);
			parser = new BasicDataPaser(parserUrl);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
			
		data.satelliteName = parser.parseToString(BasicDataPaser.TREE_PATH_SATELLITE_NAME);
		data.contentRow = parser.parseToInt(BasicDataPaser.TREE_PATH_CONTENT_ROW);
		data.sumColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_SUM_COLUMN);
		data.utcTimeHeadColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_UTCTIME_HEAD);
		data.utcTimeTailColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_UTCTIME_TAIL);
		data.localTimeHeadColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_LOCALTIME_HEAD);
		data.localTimeTailColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_LOCALTIME_TAIL);
		data.longitudeColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_POSITION_LONGITUDE);
		data.latitudeColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_POSITION_LATITUDE);
		data.altitudeColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_POSITION_ALTITUDE);
		data.protonStartColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_PROTON_FIRST_ITEM_COLUMN);
		data.protonColumns = parser.getNumOfChild(BasicDataPaser.TREE_PATH_PROTON);
		data.electronStartColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_ELECTRON_FIRST_ITEM_COLUMN);
		data.electronColumns = parser.getNumOfChild(BasicDataPaser.TREE_PATH_ELECTRON);
		data.otherStartColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_OTHER_FIRST_ITEM_COLUMN);
		data.otherColumns = parser.getNumOfChild(BasicDataPaser.TREE_PATH_OTHER);
		data.badData = parser.parseToFloat(BasicDataPaser.TREE_PATH_BAD_DATA);
		data.protonEnergyLabels = parser.getLabelChildren(BasicDataPaser.TREE_PATH_PROTON);
		data.electronEnergyLabels = parser.getLabelChildren(BasicDataPaser.TREE_PATH_ELECTRON);
		
		
		// Read data
		//--------------------------------------------------------------------------------
		BufferedReader bufReader = null;
		Scanner scanner = null;
		URLConnection urlConnection = null;
		InputStreamReader inputStreamReader = null;
		
		// Try to open the file
		try {
			urlConnection = src.openConnection();
			inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Substar.Data.constructor(), File name invalid: " + src);
		}

		bufReader = new BufferedReader(inputStreamReader);
		scanner = new Scanner(bufReader);
		
		// Go to the first data row
		for(int i = 0; i < data.getContentRow()-1; i++){
			scanner.nextLine();
		}
		while (scanner.hasNext()) {

			String srcLine = scanner.nextLine().replaceAll(" +", " ").trim();
			SingleLine newLine = data.new SingleLine(data.getSumColumn());
			
			// Get utc time
			if(data.utcTimeHeadColumn > 0){
				newLine.utcTime = srcLine.split(" ")[data.getUtcTimeHeadColumn()-1] 
						+ " "
						+ srcLine.split(" ")[data.getUtcTimeTailColumn()-1];
			}
			
			if(data.localTimeHeadColumn > 0){
				newLine.localTime = srcLine.split(" ")[data.getLocalTimeHeadColumn()-1]
						+ " "
						+ srcLine.split(" ")[data.getLocalTimeTailColumn()-1];
			}
			int firstDataColumn = 
					(data.utcTimeHeadColumn > 0 ? 1 : 0)
					+ (data.utcTimeTailColumn > 0 ? 1 : 0)
					+ (data.localTimeHeadColumn > 0 ? 1 : 0)
					+ (data.localTimeTailColumn > 0 ? 1 : 0);
			for(int i = firstDataColumn; i < data.getSumColumn(); i++){
				newLine.dataItem[i] = Float.parseFloat(srcLine.split(" ")[i]);
			}
			data.dataLines.add(newLine);
		}
		scanner.close();
		
		return data;
	}
	
	public static BasicData create(InputStream src, BasicDataPaser parser){
		
		if(src == null || parser == null){
			return null;
		}
		
		try {
			BasicData data = new BasicData();
			
			data.satelliteName = parser.parseToString(BasicDataPaser.TREE_PATH_SATELLITE_NAME);
			data.contentRow = parser.parseToInt(BasicDataPaser.TREE_PATH_CONTENT_ROW);
			data.sumColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_SUM_COLUMN);
			data.utcTimeHeadColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_UTCTIME_HEAD);
			data.utcTimeTailColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_UTCTIME_TAIL);
			data.localTimeHeadColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_LOCALTIME_HEAD);
			data.localTimeTailColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_LOCALTIME_TAIL);
			data.longitudeColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_POSITION_LONGITUDE);
			data.latitudeColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_POSITION_LATITUDE);
			data.altitudeColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_POSITION_ALTITUDE);
			data.protonStartColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_PROTON_FIRST_ITEM_COLUMN);
			data.protonColumns = parser.getNumOfChild(BasicDataPaser.TREE_PATH_PROTON);
			data.electronStartColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_ELECTRON_FIRST_ITEM_COLUMN);
			data.electronColumns = parser.getNumOfChild(BasicDataPaser.TREE_PATH_ELECTRON);
			data.otherStartColumn = parser.parseToInt(BasicDataPaser.TREE_PATH_OTHER_FIRST_ITEM_COLUMN);
			data.otherColumns = parser.getNumOfChild(BasicDataPaser.TREE_PATH_OTHER);
			data.badData = parser.parseToFloat(BasicDataPaser.TREE_PATH_BAD_DATA);
			data.protonEnergyLabels = parser.getLabelChildren(BasicDataPaser.TREE_PATH_PROTON);
			data.electronEnergyLabels = parser.getLabelChildren(BasicDataPaser.TREE_PATH_ELECTRON);
			
			// Read data
			//--------------------------------------------------------------------------------
			BufferedReader bufReader = null;
			Scanner scanner = null;

			bufReader = new BufferedReader(new InputStreamReader(src));
			scanner = new Scanner(bufReader);
			
			// Go to the first data row
			for(int i = 0; i < data.getContentRow()-1; i++){
				scanner.nextLine();
			}
			while (scanner.hasNext()) {

				String srcLine = scanner.nextLine().replaceAll(" +", " ").trim();
				SingleLine newLine = data.new SingleLine(data.getSumColumn());
				
				// Get utc time
				if(data.utcTimeHeadColumn > 0){
					newLine.utcTime = srcLine.split(" ")[data.getUtcTimeHeadColumn()-1] 
							+ " "
							+ srcLine.split(" ")[data.getUtcTimeTailColumn()-1];
				}
				
				if(data.localTimeHeadColumn > 0){
					newLine.localTime = srcLine.split(" ")[data.getLocalTimeHeadColumn()-1]
							+ " "
							+ srcLine.split(" ")[data.getLocalTimeTailColumn()-1];
				}
				int firstDataColumn = 
						(data.utcTimeHeadColumn > 0 ? 1 : 0)
						+ (data.utcTimeTailColumn > 0 ? 1 : 0)
						+ (data.localTimeHeadColumn > 0 ? 1 : 0)
						+ (data.localTimeTailColumn > 0 ? 1 : 0);
				for(int i = firstDataColumn; i < data.getSumColumn(); i++){
					newLine.dataItem[i] = Float.parseFloat(srcLine.split(" ")[i]);
				}
				data.dataLines.add(newLine);
			}
			scanner.close();
			return data;
			
		} catch (Exception e) {
			return null;
		}
		
	}
	
	private String getXmlFileName(String src) {
		if(src.contains(SATELLITE_FY1C)){
			return CONFIG_XML_ROOT_PATH + SATELLITE_FY1C + ".xml";
			
		}else if(src.contains(SATELLITE_FY2D)){
			return CONFIG_XML_ROOT_PATH + SATELLITE_FY2D + ".xml";
			
		}else if(src.contains(SATELLITE_C10C)){
			return CONFIG_XML_ROOT_PATH + SATELLITE_C10C + ".xml";
		}
		return null;
	}
	
	private URL getXmlUrlFileName(URL src) throws MalformedURLException {
		if(src.toString().contains(SATELLITE_FY1C)){
			return new URL(CONFIG_XML_SERVER_ROOT_PATH + "/" + SATELLITE_FY1C + ".xml");
			
		}else if(src.toString().contains(SATELLITE_FY2D)){
			return new URL(CONFIG_XML_SERVER_ROOT_PATH + "/" + SATELLITE_FY2D + ".xml");
			
		}else if(src.toString().contains(SATELLITE_C10C)){
			return new URL(CONFIG_XML_SERVER_ROOT_PATH + "/" + SATELLITE_C10C + ".xml");
		}
		return null;
	}
	
	// Interface methods
	public String getProtonEnergyLable(int index){
		if(index <= this.protonColumns){
			return this.protonEnergyLabels.get(index);
		}
		return "";
	}
	
	public String getElectronEnergyLabel(int index){
		if(index <= this.electronColumns){
			return this.electronEnergyLabels.get(index);
		}
		return "";
	}
	
	// TODO not all bad data checked
	public boolean isBadDat(float data){
		if(data == this.badData){
			return true;
		}
		return false;
	}
}
