package de.gebit.businessapplication.vaadin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.TableElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class VaadinBusinessApplicationUITest extends TestBenchTestCase {

	@Before
	public void setUp() throws Exception {
		setDriver(new FirefoxDriver());
	}

	@After
	public void tearDown() throws Exception {
		getDriver().quit();
	}

	@Test
	public void testAddingAddress() throws Exception {
		getDriver().get("http://localhost:8080/vaadin-business-application");
		ElementQuery<TextFieldElement> firstNameTextFieldElement = $(TextFieldElement.class).caption("Vorname");
		assertTrue(firstNameTextFieldElement.exists());
		firstNameTextFieldElement.first().setValue("Hans");
		ElementQuery<TextFieldElement> zipcodeTextFieldElement = $(TextFieldElement.class).caption("PLZ");
		zipcodeTextFieldElement.first().setValue("12345");
		$(ButtonElement.class).caption("Speichern").first().click();
		assertEquals("Hans", $(TableElement.class).first().getCell(0, 0).getText());
		assertEquals("12345", $(TableElement.class).first().getCell(0, 3).getText());
		
	}

}
