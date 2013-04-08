package com.uestc.se.basic.data;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BasicModelData 
{
	private static BasicModelData dataModel=null;
	private List<ModelDataStruct> dataList;
	private ModelDataStruct tempDataStruct;
	
	private List<Float> energyLev;
	private int particleFlag;//1��ʾ���ӣ�2��ʾ���� 
	private float badvalue;
	
	//property
	public List<Float> getEneryLev()
	{
		return energyLev;
	}
	public int getParticleFlag()
	{
		return particleFlag;
	}
	public float getBadValue()
	{
		return badvalue;
	}
	public List<ModelDataStruct> getDataList()
	{
		return dataList;
	}
   
    public BasicModelData()
    {}
    
    public static BasicModelData getInstance(String src)
    {
    	if(dataModel==null)
    	{
    		dataModel=new  BasicModelData(src);
    	}
    	return dataModel;
    }
    
    public BasicModelData(String src)
    {
    	dataList=new ArrayList<ModelDataStruct>();
    	energyLev=new ArrayList<Float>();
    	
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
		
		scanner.next();//skip 1 column
		particleFlag=scanner.nextInt();
		
		//read next 10 lines
		for(int i=0;i<10;i++)
		{
		   String t=scanner.nextLine();
		   scanner.next();//skip 1 column
		   float bvalue=scanner.nextFloat();
		   float avalue=scanner.nextFloat();
		   if(!(bvalue==avalue && avalue==0))
		   {
			   if(energyLev.size()==0)
			   {
				   energyLev.add(bvalue);
			   }
			   float lastvalue=energyLev.get(energyLev.size()-1);
			   if(lastvalue!=bvalue){energyLev.add(bvalue);}
			   if(lastvalue!=avalue){energyLev.add(avalue);}
		   }
			   
		}
		
		//read bad value
		String tempS=scanner.nextLine();
		scanner.next();
		badvalue=scanner.nextFloat();
		
		//skip head line
		tempS=scanner.nextLine();
		tempS=scanner.nextLine();
		tempS=scanner.nextLine();
		tempS=scanner.nextLine();
		
		//read content
		int energycount=energyLev.size()-1;
		int headcolumncount=11;//year,month,head...
		while (scanner.hasNext())
		{
			String LineInfo=scanner.nextLine();
			LineInfo=LineInfo.replaceAll("( )+"," ");
			String[] tempStrList=LineInfo.split(" ");
			tempDataStruct=new ModelDataStruct(energycount*4);
			
			//set value
			tempDataStruct.year=Integer.parseInt(tempStrList[0]);
			tempDataStruct.month=Integer.parseInt(tempStrList[1]);
			tempDataStruct.day=Integer.parseInt(tempStrList[2]);
			tempDataStruct.hour=Integer.parseInt(tempStrList[3]);
			tempDataStruct.minute=Integer.parseInt(tempStrList[4]);
			tempDataStruct.second=Float.parseFloat(tempStrList[5]);
			
			tempDataStruct.lon=Float.parseFloat(tempStrList[6]);
			tempDataStruct.lat=Float.parseFloat(tempStrList[7]);
			tempDataStruct.height=Float.parseFloat(tempStrList[8]);
			
			//set energy value---+x,+y,+z,OM
			for(int i=0;i<energycount;i++)
			{
				tempDataStruct.energylist[i*4]=Float.parseFloat(tempStrList[headcolumncount+i*7]);
				tempDataStruct.energylist[i*4+1]=Float.parseFloat(tempStrList[headcolumncount+i*7+2]);
				tempDataStruct.energylist[i*4+2]=Float.parseFloat(tempStrList[headcolumncount+i*7+4]);
				tempDataStruct.energylist[i*4+3]=Float.parseFloat(tempStrList[headcolumncount+i*7+6]);
			}
			dataList.add(tempDataStruct);
		}
		
		scanner.close();
    }
    
    //data struct
    public class ModelDataStruct {
    	public int year,month,day,hour,minute;
    	public float lat,lon,height,second;
    	public float[] energylist;// E1 +x,+y,+z,om E2.....
    	
    	public ModelDataStruct(int column)
    	{
    		energylist=new float[column];
    	}
    }
    
    public static void main(String[] args) throws FileNotFoundException { 
    	BasicModelData config = null;
		config = BasicModelData.getInstance("src/res/data/8455");
		System.out.println(config);
	}
}
