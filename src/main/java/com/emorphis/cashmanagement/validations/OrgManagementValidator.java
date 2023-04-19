package com.emorphis.cashmanagement.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.emorphis.cashmanagement.model.OrgManagement;

@Component
public class OrgManagementValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	String ID_PATTERN = "[0-9]+";
	String STRING_PATTERN = "[a-zA-Z]+";
	String MOBILE_PATTERN = "[0-9]{10}";

	String WhiteSpace = "^[_A-Za-z0-9-\\+]+";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return OrgManagement.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		OrgManagement orgManagement = (OrgManagement) target;
		
		if (null != orgManagement.getOrgType()) {
			String id = orgManagement.getOrgType().getId();
			System.out.println("Id in validator is : " + id);
			if (id.equals("-1")) { 
				System.out.println("Inside if id is less than 1");
				errors.rejectValue("orgType.id", "orgType.id");
			}
		}
		
		
		System.out.println("Phone number : " + orgManagement.getContactNo());
		if (null != orgManagement.getContactNo()) {
			if (!(orgManagement.getContactNo() != null && orgManagement.getContactNo().isEmpty())) {
				pattern = Pattern.compile(MOBILE_PATTERN);
				matcher = pattern.matcher(orgManagement.getContactNo());
				if (!matcher.matches()) {
					errors.rejectValue("contactNo", "organization.incorrect.contactno");
				}
			}
		}
		
		System.out.println("Email verification : " + orgManagement.getEmail());
		if (null != orgManagement.getEmail()) {
			if (!(orgManagement.getEmail()!= null && orgManagement.getEmail().isEmpty())) {
				pattern = Pattern.compile(EMAIL_PATTERN);
				matcher = pattern.matcher(orgManagement.getEmail()); 
				if (!matcher.matches()) {
					errors.rejectValue("email", "orgmanagement.email");
				}
			}
		}

		
	}

}
