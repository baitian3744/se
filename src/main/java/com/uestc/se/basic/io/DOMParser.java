
package com.uestc.se.basic.io;

import java.io.File; 
import java.io.IOException; 
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.ParserConfigurationException; 
import org.w3c.dom.Document; 
import org.xml.sax.SAXException; 

 public class DOMParser { 
   DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance(); 
  
   //Load and parse XML file into DOM 
   public Document parse(String filePath) { 
      Document document = null; 
      try { 
         //DOM parser instance 
         DocumentBuilder builder = builderFactory.newDocumentBuilder(); 
         //parse an XML file into a DOM tree 
         document = builder.parse(new File(filePath)); 
      } catch (ParserConfigurationException e) { 
    	 document = null;
         e.printStackTrace();  
      } catch (SAXException e) { 
    	  document = null;
    	  e.printStackTrace(); 
      } catch (IOException e) { 
    	  document = null;
    	  e.printStackTrace(); 
      } 
      return document; 
   } 
   
   public Document parse(URL filePath) { 
      Document document = null; 
      try { 
         //DOM parser instance 
         DocumentBuilder builder = builderFactory.newDocumentBuilder(); 
         //parse an XML file into a DOM tree 
         InputStream is = filePath.openStream();
         document = builder.parse(is);
         is.close();
      } catch (ParserConfigurationException e) { 
    	 document = null;
         e.printStackTrace();  
      } catch (SAXException e) { 
    	  document = null;
    	  e.printStackTrace(); 
      } catch (IOException e) { 
    	  document = null;
    	  e.printStackTrace(); 
      } 
      return document; 
   } 
   
   public Document parse(InputStream in){
	   Document document = null;
	   try {
		   DocumentBuilder builder = builderFactory.newDocumentBuilder(); 
		   document = builder.parse(in);
		   in.close();
		} catch (Exception e) {
			return null;
		}
	   return document;
   }
 } 
 
 
