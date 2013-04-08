package com.uestc.se.basic.data;

import static org.junit.Assert.*;

import org.junit.Test;

import com.uestc.se.basic.data.BasicData;
import com.uestc.se.basic.data.BasicDataFactory;

public class BasicDataFactoryTest {

	@Test
	public void testCreateData() {
		BasicDataFactory dataFactory = new BasicDataFactory();
		BasicData data;
		
//		// Shut down server and test
//		data = dataFactory.createData("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990514.txt");
//		assertNull(data);
		
		// Right type of data
		data = dataFactory.createData("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990514.txt");
		assertNotNull(data);
		
		// Wrong type of data
		data = dataFactory.createData("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990519.txt.todzkd.txt");
		assertNull(data);
		
		// Null
		data = dataFactory.createData(null);
		assertNull(data);
		
		// ""
		data = dataFactory.createData("");
		assertNull(data);
		
	}
	
	private void p(String msg){
		System.out.println(msg);
	}

}
