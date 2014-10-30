package de.gebit.businessapplication.jsf;

import java.util.ArrayList;
import java.util.List;

public class AddressbookBean {

	private final List<Address> addresses = new ArrayList<>();

	private Address address = new Address();
	
	public List<Address> getAddresses() {
		return addresses;
	}

	public Address getAddress() {
		return address;
	}

	public String save() {
		addresses.add(address);
		address = new Address();
		return "";
	}

}
