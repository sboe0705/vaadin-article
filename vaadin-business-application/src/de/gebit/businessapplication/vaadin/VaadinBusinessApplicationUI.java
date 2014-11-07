package de.gebit.businessapplication.vaadin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.gebit.businessapplication.model.Address;
import de.gebit.businessapplication.model.Person;

@SuppressWarnings("serial")
@Theme("gebit")
public class VaadinBusinessApplicationUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinBusinessApplicationUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private final BeanFieldGroup<Person> personFieldGroup;

	private final BeanItemContainer<Person> personContainer;

	public VaadinBusinessApplicationUI() {
		personFieldGroup = new BeanFieldGroup<>(Person.class);
		personContainer = new BeanItemContainer<>(Person.class);
		personContainer.addNestedContainerBean("address");
	}

	@Override
	protected void init(VaadinRequest request) {
		TextField firstNameField = new TextField("Vorname");
		firstNameField.setNullRepresentation("");
		TextField lastNameField = new TextField("Nachname");
		lastNameField.setNullRepresentation("");
		TextField streetField = new TextField("Straße");
		streetField.setNullRepresentation("");
		TextField zipcodeField = new TextField("PLZ");
		// zipcodeField.addValidator(new RegexpValidator("\\d{5}",
		// "PLZ ist ung�ltig"));
		zipcodeField.addValidator(new ZipcodeValidator());
		zipcodeField.setNullRepresentation("");
		TextField cityField = new TextField("Stadt");
		cityField.setNullRepresentation("");

		personFieldGroup.bind(firstNameField, "firstName");
		personFieldGroup.bind(lastNameField, "lastName");
		personFieldGroup.bind(streetField, "address.street");
		personFieldGroup.bind(zipcodeField, "address.zipcode");
		personFieldGroup.bind(cityField, "address.city");
		personFieldGroup.setItemDataSource(new Person());

		FormLayout formLayout = new FormLayout(firstNameField, lastNameField, streetField, zipcodeField, cityField);

		Button saveButton = new Button("Speichern", event -> save());

		Table table = new Table();
		table.setContainerDataSource(personContainer);
		table.setVisibleColumns("firstName", "lastName", "address.street", "address.zipcode", "address.city");
		table.setColumnHeaders("Vorname", "Nachname", "Straße", "PLZ", "Stadt");

		VerticalLayout content = new VerticalLayout(formLayout, saveButton, table);
		content.setSpacing(true);
		content.setMargin(true);
		setContent(content);
	}

	public void save() {

		if (!personFieldGroup.isValid()) {
			return;
		}

		try {
			personFieldGroup.commit();
			personContainer.addBean(personFieldGroup.getItemDataSource().getBean());
			personFieldGroup.setItemDataSource(new Person());
		} catch (CommitException e) {
			Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
		}
	}

}