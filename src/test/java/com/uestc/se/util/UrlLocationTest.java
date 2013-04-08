package com.uestc.se.util;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.junit.Test;

public class UrlLocationTest {

	@Test
	public void test() {
		for(int i = 0; i < 10; i++){
			if(!ResourceLoader.resourceExists("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990519.txt.todzkd.txt")){
				System.out.println("i = " + i);
			}else{
				InputStream ins = ResourceLoader.getResourceAsStream("http://localhost:8080/demo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990519.txt.todzkd.txt");
				assertNotNull(ins);
					
			}
		}
	}

}
