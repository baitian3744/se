package com.uestc.se.datashare.substar;

import static org.junit.Assert.*;

import org.junit.Test;

import com.uestc.se.util.ResourceLoader;

public class SubstarDataTest {

	@Test
	public void test() {
		SubstarData data = SubstarData.create(
				"http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990519.txt.todzkd.txt", 
				SubstarConfig.create(ResourceLoader.getResource("http://localhost:8080/demo/config_default.xml"))
				);
		assertNotNull(data);
	}

}
