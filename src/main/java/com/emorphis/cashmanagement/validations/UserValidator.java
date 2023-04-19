package com.emorphis.cashmanagement.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.emorphis.cashmanagement.model.User;

@Component
public class UserValidator implements Validator {

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

		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		// ValidationUtils.rejectIfEmpty(errors, "firstName", "user.firstName");
		// ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender",
		// "user.gender");

		if (null != user.getEditable()) {
			if (user.getEditable().equals("false")) {
				if (null != user.getUserPassword()) {
					String password = user.getUserPassword().replaceAll("\\s", "");
					user.setUserPassword(password);
				}
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userPassword", "user.userPassword");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userId", "user.userId");
			}
		}

		if (null != user.getBranchManagement().getId()) {
			String branch_id = user.getBranchManagement().getId();
			System.out.println("Id in validator is : " + branch_id);
			if (branch_id.equals("-1") || branch_id.equals("0")) { 
				System.out.println("Inside if id is less than 1");
				errors.rejectValue("branchManagement.branchName", "branchManagement.id");
			}
		} else {
			errors.rejectValue("branchManagement.branchName", "branchManagement.id");
		}

		if (null != user) {
			if (0 == user.getGender()) {
				errors.rejectValue("gender", "user.gender");
			}
			String designation_id = user.getDesignation().getId();
			if (designation_id.equals("-1") || designation_id.equals("0")) {
				errors.rejectValue("designation.id", "designation.id");
			}
			String department_id = user.getDepartment().getId();
			if (department_id.equals("-1") || department_id.equals("0")) {
				errors.rejectValue("department.id", "department.id");
			}
		}
		
		System.out.println("Phone number : " + user.getContactNo());
		if (null != user.getContactNo()) {
			if (!(user.getContactNo() != null && user.getContactNo().isEmpty())) {
				pattern = Pattern.compile(MOBILE_PATTERN);
				matcher = pattern.matcher(user.getContactNo());
				if (!matcher.matches()) {
					errors.rejectValue("contactNo", "user.incorrect.contactno");
				}
			}
		}		

		System.out.println("Email verification : " + user.getEmail());
		if (null != user.getEmail()) {
			if (!(user.getEmail() != null && user.getEmail().isEmpty())) {
				pattern = Pattern.compile(EMAIL_PATTERN);
				matcher = pattern.matcher(user.getEmail()); 
				if (!matcher.matches()) {
					errors.rejectValue("email", "user.email");
				}
			}
		}
	}

}
