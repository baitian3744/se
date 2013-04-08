package com.uestc.se.basic.data;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

import com.uestc.se.basic.data.BasicData;
import com.uestc.se.basic.data.BasicDataPaser;
import com.uestc.se.util.ResourceLoader;

public class BasicDataTest {

	@Test
	public void test() {
		
	}
	
	public void testCreate(){
		
		InputStream sourceIn, parserIn;
		BasicData data;
		BasicDataPaser parser;
		
		/* Test the right condition */
		//******************************************************************************************************
		// Source and parser file are both available?
		sourceIn = ResourceLoader.getResourceAsStream("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990514.txt");
		parserIn = ResourceLoader.getResourceAsStream("http://localhost:8080/demo/BASIC-DATA-XML/FY1C.xml");
		assertNotNull(sourceIn);
		assertNotNull(parserIn);
		
		// Create parser
		parser = BasicDataPaser.create(parserIn);
		assertNotNull(parser);
		
		// Create data
		data = BasicData.create(sourceIn, parser);
		assertNotNull(data);
		
		// Check data
		assertTrue("FY1C".equals(data.getSatelliteName()));
		
	}

}
