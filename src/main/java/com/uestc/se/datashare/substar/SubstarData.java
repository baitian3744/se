package com.uestc.se.datashare.substar;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import com.uestc.se.basic.data.BasicData;
import com.uestc.se.basic.data.BasicData.SingleLine;
import com.uestc.se.basic.data.BasicDataFactory;
import com.uestc.se.basic.ui.CompositGraphic;
import com.uestc.se.basic.ui.Point;

public class SubstarData {
	
	public static final int IS_PROTON	= 1;
	public static final int IS_ELECTRON	= 0;
	public static final float MAX_DATA_WHEN_PROTON = 10000.0f;
	public static final float MAX_DATA_WHEN_ELECTRON = 100000.0f;
	public static final float MIN_DATA	= 20.0f;
	
	private SubstarConfig config;
	private int isProtonOrElectron;		// 1:proton, 0:electron
	private int lineNum;
	private String satelliteName;
	private float maxData, minData = MIN_DATA;
	
	private float getMinData(){
		return this.minData;
	}
	
	private List<String> files = new ArrayList<String>();
	private List<BasicData> data = new ArrayList<BasicData>();
	
	public List<Track> getTracks() {
		int actualIndex = getActualIndex(lineNum);
		return this.getTrack(actualIndex);
		
	}
	
	private int getActualIndex(int index){
		return isProton() ? 
				(this.data.get(0).getProtonStartColumn()-1 + this.lineNum-1)
				: (this.data.get(0).getElectronStartColumn()-1 + this.lineNum-1);
	}
	
	private List<Track> getTrack(int index) {
		
		List<Track> tracks = new ArrayList<SubstarData.Track>();
		for(int i = 0; i < this.data.size(); i++){
			if( index < this.data.get(i).getSumColumn()){
				tracks.add(new Track(index, this.data.get(i)));
			}
		}
		return tracks;
		
	}
	
	public class Track extends CompositGraphic{

		private int index;

		/**
		 * 
		 * @param index from 0 ->
		 * @param srcData
		 */
		public Track(int index, BasicData srcData) {
			super();
			this.index = index;
			SingleLine currentRow;
			float currentValue;
			for(int i = 0; i < srcData.rows(); i++){
				currentRow = srcData.getRow(i);
				currentValue = currentRow.getMember(index);
				if(!isBadData(currentValue, srcData)){
					Point newPoint = new Point(
							currentRow.getMember(srcData.getLongitudeColumn()-1), 
							currentRow.getMember(srcData.getLatitudeColumn()-1), 
							0.0f);
					newPoint.setColor(colorOf(currentValue));
					newPoint.setSize(config.getPlotPointSize());
					this.add(newPoint);
				}
			}			
		}
		
		@Override
		public void draw() {
			GL11.glPushMatrix();
				GL11.glTranslatef(180.0f, 90.0f, 0.0f);		// Translate to center
				super.draw();
			GL11.glPopMatrix();
		}

		public void setPointSize(float size) {
			int i = 0;
			while(this.getChild(i) != null){
				((Point)this.getChild(i)).setSize(size);
				i++;
			}
		}
		
	}
	
	private boolean isBadData(float value, BasicData srcData){
		if(srcData.isBadDat(value)){
			return true;
		}else if(value < getMinData()){
			return true;
		}
		return false;
			
	}
	
	private Color colorOf(float value) {
		float alpha = 1.0f;
		int steps = 4;
		float maxLogValue = (float) Math.log10(this.maxData);
		float minLogValue = (float) Math.log10(this.minData);
		float gap = maxLogValue - minLogValue;
		float stepLen = gap/steps;
		float logValue = (float) Math.log10(value);
		float rate = (logValue - minLogValue)/gap;
		
		if(rate <= 0.25f){
			return new Color(
					0.0f, 
					getRate(logValue - minLogValue, stepLen), 
					1.0f, 
					alpha
					);
			
		}else if(rate <= 0.5f){
			return new Color(
					0.0f, 
					1.0f, 
					1.0f - getRate(logValue - minLogValue - stepLen, stepLen),
					alpha
					);
			
		}else if(rate <= 0.75f){
			return new Color(
					getRate(logValue - minLogValue - 2*stepLen, stepLen),
					1.0f, 
					0.0f, 
					alpha
					);
			
		}else if(rate <= 1.0f){
			return new Color(
					1.0f, 
					1.0f - getRate(logValue - minLogValue - 3*stepLen, stepLen), 
					0.0f, 
					alpha
					);
			
		}else{
			System.out.println("Err @SubstarData.colorOf(): value out of range.");
			return new Color(1.0f, 0.0f, 0.0f, alpha);
			
		}
	
	}
	
	private float getRate(float dividend, float divisor){
		return dividend/divisor;
	}
	
	
	private SubstarData() {
		
	}
	
	/**
	 * 
	 * @param interfaceFile
	 * @param config
	 */
	public SubstarData(String interfaceFile, SubstarConfig config) {
		
		this.config = config;
		// Local variable
		FileReader fileReader = null;
		BufferedReader bufReader = null;
		Scanner scanner = null;
		
		// Try to open the file
		try {
			fileReader = new FileReader(interfaceFile);
			bufReader = new BufferedReader(fileReader);
			scanner = new Scanner(bufReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Substar.Data.constructor(), File name invalid: " + interfaceFile);
			// TODO throw exception
		}
		
		this.isProtonOrElectron = scanner.nextInt();
		if(this.isProton()){
			this.maxData = MAX_DATA_WHEN_PROTON;
		}else{
			this.maxData = MAX_DATA_WHEN_ELECTRON;
		}
		this.lineNum = scanner.nextInt();
		scanner.nextLine();
		scanner.nextLine();
		
		while(scanner.hasNext()){
			this.files.add(scanner.next());
		}
		scanner.close();
	}
	
	public SubstarData(URL interfaceFile, SubstarConfig config) {
		
		this.config = config;
		// Local variable
		BufferedReader bufReader = null;
		Scanner scanner = null;
		URLConnection con = null;
		InputStreamReader inputStreamReader = null;
		
		try {
			con = interfaceFile.openConnection();
			inputStreamReader = new InputStreamReader(con.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bufReader = new BufferedReader(inputStreamReader);
		scanner = new Scanner(bufReader);
		
		this.isProtonOrElectron = scanner.nextInt();
		if(this.isProton()){
			this.maxData = MAX_DATA_WHEN_PROTON;
		}else{
			this.maxData = MAX_DATA_WHEN_ELECTRON;
		}
		this.lineNum = scanner.nextInt();
		scanner.nextLine();
		scanner.nextLine();
		
		while(scanner.hasNext()){
			this.files.add(scanner.next());
		}
		scanner.close();
	}
	
	public SubstarData(String configSource, SubstarConfig config, int i) {
		this.config = config;
		// Local variable
		FileReader fileReader = null;
		BufferedReader bufReader = null;
		Scanner scanner = null;
		
		// Try to open the file
		try {
			fileReader = new FileReader(configSource);
			bufReader = new BufferedReader(fileReader);
			scanner = new Scanner(bufReader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Substar.Data.constructor(), File name invalid: " + configSource);
			// TODO throw exception
		}
		
		this.isProtonOrElectron = scanner.nextInt();
		if(this.isProton()){
			this.maxData = MAX_DATA_WHEN_PROTON;
		}else{
			this.maxData = MAX_DATA_WHEN_ELECTRON;
		}
		this.lineNum = scanner.nextInt();
		scanner.nextLine();
		scanner.nextLine();
		
		while(scanner.hasNext()){
			this.files.add(scanner.next());//
		}
		scanner.close();
	}

	public boolean loadData(){
		try {
			for(String f : files){
				this.data.add(BasicData.create(new URL(f)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		this.setSatelliteName(this.data.get(0).getSatelliteName());
		return true;
	}
	
	public static SubstarData create(String urlSrc, SubstarConfig config){
		
		SubstarData data = new SubstarData();
		data.config = config;
		
		/* Read interface file */
		//---------------------------------------------------------------------------------
		// Local variable
		BufferedReader bufReader = null;
		Scanner scanner = null;
		
		// Try to open the file
		try {
	        URL oracle = new URL(urlSrc);
	        URLConnection con = oracle.openConnection();
	      	bufReader = new BufferedReader(new InputStreamReader(
	                                    con.getInputStream()));
			scanner = new Scanner(bufReader);
		} catch (IOException e) {
			printErr("Substar.Data.constructor(), File name invalid: " + urlSrc);
			return null;
		}
		
		data.isProtonOrElectron = scanner.nextInt();
		if(data.isProton()){
			data.maxData = MAX_DATA_WHEN_PROTON;
		}else{
			data.maxData = MAX_DATA_WHEN_ELECTRON;
		}
		data.lineNum = scanner.nextInt();
		scanner.nextLine();
		scanner.nextLine();
		
		while(scanner.hasNext()){
			data.files.add(scanner.next());
		}
		scanner.close();
		
		/* Read source data */
		//-----------------------------------------------------------------------------------
		try {
			BasicDataFactory factory = new BasicDataFactory();
			for(String f : data.files){
				BasicData newBasicData = factory.createData(f);
				if(newBasicData != null){
					data.data.add(newBasicData);
				}else{
					printErr("cannot add new BasicData in create(), file = " + f);
				}
				
			}
			
		} catch (Exception e) {
			printErr("cannot create and add BasicDatas to SubstarData in create()");
			for(String f : data.files){
				printErr("please check the existence of file = " + f);
			}
			return null;
		}
		data.setSatelliteName(data.data.get(0).getSatelliteName());
		return data;
		
	}
	
	private void setSatelliteName(String name){
		this.satelliteName = name;
	}

	public String getSatelliteName(){
		return this.satelliteName;
	}
	
	public String getStartTime(){

		String regex = "[0-9]{8}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(this.files.get(0));
		int temp = Integer.MAX_VALUE;
		for(String s : this.files){
			matcher = pattern.matcher(s);
			if(matcher.find()){
				temp = temp<Integer.parseInt(matcher.group(0)) ? temp:Integer.parseInt(matcher.group(0));
			}
		}
		String result = "";
		result = getYear(temp) + "-" 
				+ getMonth(temp) + "-" 
				+ getDay(temp) + "(UT)";
		return result;
	}
	
	private String getYear(int time){
		return Integer.toString(time).substring(0, 4);
	}
	
	private String getMonth(int time){
		return Integer.toString(time).substring(4, 6);
	}
	
	private String getDay(int time){
		return Integer.toString(time).substring(6, 8);
	}
	
	public String getEndTime(){

		String regex = "[0-9]{8}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher;
		int temp = Integer.MIN_VALUE;
		for(String s : this.files){
			matcher = pattern.matcher(s);
			if(matcher.find()){
				temp = temp>Integer.parseInt(matcher.group(0)) ? temp:Integer.parseInt(matcher.group(0));
			}
		}
		String result = "";
		result = getYear(temp) + "-" 
				+ getMonth(temp) + "-" 
				+ getDay(temp) + "(UT)";
		return result;
	}
	
	public String getEnergyLabel(){
		String head = "";
		String tail = "";

		// tail
		if(isProton()){
			head = this.data.get(0).getProtonEnergyLable(this.getLineNumber()-1);
			tail = " Proton";
		}else if(isElectron()){
			head = this.data.get(0).getElectronEnergyLabel(this.getLineNumber()-1);
			tail = " Electron";
		}
		return head + tail;
	}
	
	/**
	 * @eg "FY1C_SCI_SEM_L04_01M_A0_19990703.txt"
	 * ->	"_L04_"
	 * ->	"04"
	 * @return
	 */
	public String getDataLevel(){

		String s = this.files.get(0);
		String regex = "_L[0-9]{2}[a-zA-Z]*_";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(s);
		if(matcher.find()){
			System.out.println(matcher.group(0));
			String s1 = matcher.group(0);
			regex = "[0-9]{2}[a-zA-Z]*";
			pattern = Pattern.compile(regex);
			matcher = pattern.matcher(s1);
			if(matcher.find()){
				return matcher.group(0);
			}
		}
		return "";
	}
	
	public String getBackgroundTexture(){
		return this.config.getBackgroundTexture();
	}
	
	public int getLineNumber(){
		return this.lineNum;
	}
	
	public boolean isProton(){
		if(this.isProtonOrElectron == IS_PROTON){
			return true;
		}
		return false;
	}
	
	public boolean isElectron(){
		if(this.isProtonOrElectron == IS_ELECTRON){
			return true;
		}
		return false;
	}
	
	private static void printErr(String errMsg){
		System.out.println("Err@SubstarData: " + errMsg);
	}
}
	