package com.uestc.se.basic.data;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Test;

import com.uestc.se.basic.data.BasicDataPaser;
import com.uestc.se.util.ResourceLoader;

public class BasicDataPaserTest {

	@Test
	public void test() throws Exception {
		URL url;
		URLConnection con;
		InputStream instream;
		BasicDataPaser parser;
		
		instream = ResourceLoader.getResourceAsStream("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990519.txt");
		parser = BasicDataPaser.create(instream);
		assertNull(parser);
		
		instream = ResourceLoader.getResourceAsStream("");
		parser = BasicDataPaser.create(instream);
		assertNull(parser);
        
		instream = ResourceLoader.getResourceAsStream(null);
		parser = BasicDataPaser.create(instream);
		assertNull(parser);
		
        url = new URL("http://localhost:8080/demo/BASIC-DATA-XML/FY1C.xml");
        con = url.openConnection();
        instream = con.getInputStream();
		parser = BasicDataPaser.create(instream);
		assertNotNull(parser);
	}

}


//url = new URL("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990519.txt");
//con = url.openConnection();
//instream = con.getInputStream();
