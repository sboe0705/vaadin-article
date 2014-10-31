package de.gebit.businessapplication.vaadin;

import java.util.regex.Pattern;

import com.vaadin.data.validator.AbstractValidator;

public class ZipcodeValidator extends AbstractValidator<String> {

	public ZipcodeValidator() {
		super("PLZ ist ungültig");
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected boolean isValidValue(String value) {

		if (value == null || (String.class.equals(getType()) && "".equals(value))) {
			return true;
		}

		if (!Pattern.compile("\\d{5}").matcher((String) value).matches()) {
			return false;
		}

		int intValue = Integer.parseInt((String) value);
		if (intValue < 1000) {
			return false;
		}

		return true;
	}

	@Override
	public Class<String> getType() {
		return String.class;
	}

}
