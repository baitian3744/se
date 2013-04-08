package com.uestc.se.datashare.substar;

import static org.junit.Assert.*;

import org.junit.Test;

public class SubstarModelTest {

	@Test
	public void test() {
		SubstarModel model = SubstarModel.newInstance("http://localhost:8080/ModelAppletDmo/FY1C/FY1C_SCI_SEM_L04_01M/1999/199905/FY1C_SCI_SEM_L04_01M_A0_19990519.txt.todzkd.txt");
//		assertTrue(model.init());
	}

}
