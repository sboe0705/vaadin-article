package de.gebit.businessapplication.jsf;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.gebit.businessapplication.model.Address;
import de.gebit.businessapplication.model.Person;

@ManagedBean(name = "addressbookBean")
@SessionScoped
public class AddressbookBean {

	private final List<Person> persons = new ArrayList<>();

	private Person person = new Person();

	public Person getPerson() {
		return person;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public String save() {
		persons.add(person);
		person = new Person();
		return "";
	}

}
