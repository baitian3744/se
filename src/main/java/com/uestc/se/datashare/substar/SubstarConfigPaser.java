package com.uestc.se.datashare.substar;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.uestc.se.basic.io.DOMParser;
import com.uestc.se.basic.ui.Point;

public class SubstarConfigPaser {

	public static final String[] TREE_PATH_WIDTH 						=		{"render", "window", "width"};
	public static final String[] TREE_PATH_HEIGHT 						=		{"render", "window", "height"};
	public static final String[] TREE_PATH_BACKGROUND_COLOR				=		{"render", "window", "background", "color"};
	public static final String[] TREE_PATH_BACKGROUND_TEXTURE			=		{"render", "window", "background", "texture"};
	public static final String[] TREE_PATH_BACKGROUND_TEXTURE_PATH		=		{"render", "window", "background", "texture", "src"};
	public static final String[] TREE_PATH_PLOT_POINTSIZE				=		{"render", "plot", "point", "size"};
	
//	public static final String[] TREE_PATH_COORDINATE_TOPAXIS_VISIABLE	=		{"render", "coordinate", "item", "size"};
	
	
	private Document document;
	private Element rootElement;
	
	public SubstarConfigPaser(String configFile) throws FileNotFoundException{
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
	
	public SubstarConfigPaser(URL configFile) throws FileNotFoundException{
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
	
	public SubstarConfigPaser(InputStream in) throws FileNotFoundException{
		if(in == null){
			throw new FileNotFoundException("config file = null");
		}
		if("".equals(in)){
			throw new FileNotFoundException("config file : <empty>");
		}
		// Parser
		DOMParser parser = new DOMParser(); 
		this.document = parser.parse(in);
		if(this.document == null){
			throw new FileNotFoundException(in + ", or " + in + " is not xml file");
		}else{
			this.rootElement = document.getDocumentElement();
			if( !"Config".equals(rootElement.getNodeName()) ){
				throw new FileNotFoundException("not a config file");
			}
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
	
	public boolean parseEanbled(String[] treePath){
		String enable = this.digElement(treePath).getAttribute("enable");
		if("true".equals(enable)){
			return true;
		}
		return false;	
	}
	
	public Point parseToPoint(String[] treePath){
		Element eleOfPoint = this.digElement(treePath);
		float x = Float.parseFloat(this.getChild(eleOfPoint, "x").getFirstChild().getNodeValue());
		float y = Float.parseFloat(this.getChild(eleOfPoint, "y").getFirstChild().getNodeValue());
		float z = Float.parseFloat(this.getChild(eleOfPoint, "z").getFirstChild().getNodeValue());
		return new Point(x, y, z);
	}
	
	public Color parseToColor(String[] treePath){
		
		Element eleOfColor = this.digElement(treePath);
		float r = Float.parseFloat(this.getChild(eleOfColor, "r").getFirstChild().getNodeValue());
		float g = Float.parseFloat(this.getChild(eleOfColor, "g").getFirstChild().getNodeValue());
		float b = Float.parseFloat(this.getChild(eleOfColor, "b").getFirstChild().getNodeValue());
		float a = Float.parseFloat(this.getChild(eleOfColor, "a").getFirstChild().getNodeValue());
		
		return new Color(r, g, b, a);
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
