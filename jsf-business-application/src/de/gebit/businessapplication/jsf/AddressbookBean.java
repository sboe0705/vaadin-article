package de.gebit.businessapplication.jsf;

import java.util.ArrayList;
import java.util.List;

public class AddressbookBean {
	
	private List<Address> addresses = new ArrayList<>();
	
	private Address newAdress;
	
	private String test = "Hallo Welt!";

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
	
}
