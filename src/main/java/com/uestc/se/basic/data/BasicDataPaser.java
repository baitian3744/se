package com.uestc.se.basic.data;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.uestc.se.basic.io.DOMParser;

public class BasicDataPaser {

    public static final String[] TREE_PATH_SATELLITE_NAME 				= 		{"SatelliteName"};
	public static final String[] TREE_PATH_CONTENT_ROW 					= 		{"ContentRow"};
	public static final String[] TREE_PATH_SUM_COLUMN					=		{"SumColumn"};
	public static final String[] TREE_PATH_UTCTIME_HEAD					=		{"TimeColumn", "UTCHead"};
	public static final String[] TREE_PATH_UTCTIME_TAIL					=		{"TimeColumn", "UTCTail"};
	public static final String[] TREE_PATH_LOCALTIME_HEAD				=		{"TimeColumn", "LocalHead"};
	public static final String[] TREE_PATH_LOCALTIME_TAIL				=		{"TimeColumn", "LocalTail"};
	public static final String[] TREE_PATH_POSITION_LONGITUDE			=		{"PositionColumn", "LonColumn"};
	public static final String[] TREE_PATH_POSITION_LATITUDE			=		{"PositionColumn", "LatColumn"};
	public static final String[] TREE_PATH_POSITION_ALTITUDE			=		{"PositionColumn", "AltitudeColumn"};
	public static final String[] TREE_PATH_PROTON						=		{"ParticleColumn", "Protons"};
	public static final String[] TREE_PATH_PROTON_FIRST_ITEM_COLUMN		=		{"ParticleColumn", "Protons", "item", "column"};
	public static final String[] TREE_PATH_ELECTRON						=		{"ParticleColumn", "Electrons"};
	public static final String[] TREE_PATH_ELECTRON_FIRST_ITEM_COLUMN	=		{"ParticleColumn", "Electrons", "item", "column"};
	public static final String[] TREE_PATH_OTHER						=		{"ParticleColumn", "Other"};
	public static final String[] TREE_PATH_OTHER_FIRST_ITEM_COLUMN		=		{"ParticleColumn", "Other", "item", "column"};
	public static final String[] TREE_PATH_BAD_DATA						=		{"BadData"};
	
	private Document document;
	private Element rootElement;
	
	public BasicDataPaser(String configFile) throws FileNotFoundException{
		if(configFile == null){
			throw new FileNotFoundException("config file = null");
		}
		if("".equals(configFile)){
			throw new FileNotFoundException("config file : <empty>");
		}
		// Parser
		DOMParser parser = new DOMParser(); 
		this.document = parser.parse(configFile);
		if(this.document == null){
			throw new FileNotFoundException(configFile + ", or " + configFile + " is not xml file");
		}else{
			this.rootElement = document.getDocumentElement();
			if( !"Config".equals(rootElement.getNodeName()) ){
				throw new FileNotFoundException("not a config file");
			}
		}
	}
	
	public BasicDataPaser(URL configFile) throws FileNotFoundException{
		if(configFile == null){
			throw new FileNotFoundException("config file = null");
		}
		// Parser
		DOMParser parser = new DOMParser(); 
		this.document = parser.parse(configFile);
		if(this.document == null){
			throw new FileNotFoundException(configFile + ", or " + configFile + " is not xml file");
		}else{
			this.rootElement = document.getDocumentElement();
			if( !"Config".equals(rootElement.getNodeName()) ){
				throw new FileNotFoundException("not a config file");
			}
		}
	}
	
	private BasicDataPaser(InputStream configFileInputStream) throws FileNotFoundException{
		if(configFileInputStream == null){
			throw new FileNotFoundException("config file = null");
		}
		DOMParser parser = new DOMParser();
		this.document = parser.parse(configFileInputStream);
		if(this.document == null){
			throw new FileNotFoundException(configFileInputStream.toString() + " is not xml file");
		}else{
			this.rootElement = document.getDocumentElement();
			if( !"Config".equals(rootElement.getNodeName()) ){
				throw new FileNotFoundException("not a config file");
			}
		}
	}
	
	public static BasicDataPaser create(InputStream configFileInputStream){
		BasicDataPaser parser;
		try {
			parser = new BasicDataPaser(configFileInputStream);
			return parser;
		} catch (Exception e) {
			return null;
		}
	}
	
	// Interface method
	//********************************************************************************************	
	public String parseToString(String[] treePath){
		return this.parseNode(treePath);
	}
	
	public int parseToInt(String[] treePath){
		return Integer.parseInt(this.parseNode(treePath));
	}
	
	public float parseToFloat(String[] treePath){
		return Float.parseFloat(this.parseNode(treePath));
	}
	
	public int getNumOfChild(String[] treePath){
		Element ele = digElement(treePath);
		if(ele == null){
			return 0;
		
		}else{
			int i = 0;			
			for(Node item = ele.getFirstChild(); item != null; item = item.getNextSibling()){
				if(item.getNodeType() == Node.ELEMENT_NODE){
					if(item.getNodeName().equals("item")){
						i++;
					}
				}
			}
			return i;
		}
	}
	
	// TODO test
	public List<String> getLabelChildren(String[] treePath){
		List<String> children = new ArrayList<String>();
		Element ele = digElement(treePath);
		if(ele == null){
			return null;
		
		}else{
			for(Node item = ele.getFirstChild(); item != null; item = item.getNextSibling()){
				if(item.getNodeType() == Node.ELEMENT_NODE){
					if(item.getNodeName().equals("item")){
						children.add(getChild(item, "label").getFirstChild().getNodeValue());
					}
				}
			}
			return children;
		}
	}

	
	// Private methods
	//-------------------------------------------------------------------------------
	private String parseNode(String[] tree){
		try {
			return digElement(tree).getFirstChild().getNodeValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method dig an element via the path of tree, and returns null if can not find it.
	 * @param tree
	 * @return
	 */
	private Element digElement(String[] tree){
		if(tree == null){
			return null;
		}
		Element ele = rootElement;
		for(String nextNode : tree){
			ele = getChild(ele, nextNode);
			if(ele == null){
				return null;
			}
		}
		return ele.equals(rootElement) ? null : ele;
	}
	
	/**
	 * 
	 * @param parent
	 * @param name
	 * @return
	 */
	private Element getChild(Element parent, String name){
		for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
		      if (child instanceof Element && name.equals(child.getNodeName())) {
		        return (Element) child;
		      }
		}
		return null;
	}
	
	private Node getChild(Node parent, String name){
		for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
		      if (child instanceof Element && name.equals(child.getNodeName())) {
		        return child;
		      }
		}
		return null;
	}
}
