package com.uestc.se.datashare.substar;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

import com.uestc.se.util.ResourceLoader;

public class SubstarConfigTest {

	@Test
	public void testCreateInputStream() {
//		InputStream in = ResourceLoader.getResourceAsStream("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990519.txt.todzkd.txt");
//		assertNotNull(in);
//		SubstarConfig config = SubstarConfig.create(in);
//		assertNotNull(config);
		
		InputStream in = ResourceLoader.getResourceAsStream("http://localhost:8080/demo/config_default.xml");
		assertNotNull(in);
		SubstarConfig config = SubstarConfig.create(in);
		assertNotNull(config);
		System.out.println(config.getPlotPointSize());
		
	}

}
