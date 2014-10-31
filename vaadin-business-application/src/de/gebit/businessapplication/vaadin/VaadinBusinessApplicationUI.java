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

@SuppressWarnings("serial")
@Theme("gebit")
public class VaadinBusinessApplicationUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinBusinessApplicationUI.class)
	public static class Servlet extends VaadinServlet {
	}

	private BeanFieldGroup<Address> addressFieldGroup = new BeanFieldGroup<>(Address.class);

	private BeanItemContainer<Address> addressContainer = new BeanItemContainer<>(Address.class);

	@Override
	protected void init(VaadinRequest request) {
		TextField streetField = new TextField("Straße");
		streetField.setNullRepresentation("");
		TextField zipcodeField = new TextField("PLZ");
//		zipcodeField.addValidator(new RegexpValidator("\\d{5}", "PLZ ist ungültig"));
		zipcodeField.addValidator(new ZipcodeValidator());
		zipcodeField.setNullRepresentation("");
		TextField cityField = new TextField("Stadt");
		cityField.setNullRepresentation("");
		TextField countryField = new TextField("Land");
		countryField.setNullRepresentation("");

		addressFieldGroup.bind(streetField, "street");
		addressFieldGroup.bind(zipcodeField, "zipcode");
		addressFieldGroup.bind(cityField, "city");
		addressFieldGroup.bind(countryField, "country");
		addressFieldGroup.setItemDataSource(new Address());

		FormLayout formLayout = new FormLayout(streetField, zipcodeField, cityField, countryField);

		Button saveButton = new Button("Speichern", event -> save());

		Table table = new Table();
		table.setContainerDataSource(addressContainer);

		setContent(new VerticalLayout(formLayout, saveButton, table));
	}

	public void save() {

		if (!addressFieldGroup.isValid()) {
			return;
		}

		try {
			addressFieldGroup.commit();
			addressContainer.addBean(addressFieldGroup.getItemDataSource().getBean());
			addressFieldGroup.setItemDataSource(new Address());
		} catch (CommitException e) {
			Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
		}
	}

}