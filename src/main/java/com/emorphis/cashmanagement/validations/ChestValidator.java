package com.emorphis.cashmanagement.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.emorphis.cashmanagement.model.BranchManagement;

@Component
public class ChestValidator implements Validator {

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
		return BranchManagement.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		BranchManagement branchManagement = (BranchManagement) target;
		/*int id = branchManagement.getHierarchyControl().getId();
		System.out.println("Id in branch validator is : " + id);
		if (id < 1) {
			System.out.println("Inside if of hierarchyControl validator id is less than 1");
			errors.rejectValue("hierarchyControl.id", "hierarchyControl.id");
		}*/

		if (null != branchManagement.getBranchControl()&&!"-1".equals(branchManagement.getBranchControl())) {
			String branch_id = branchManagement.getBranchControl();
			System.out.println("Id in validator is : " + branch_id);
			if (branch_id.equals("-1")) {
				System.out.println("Inside if id is less than 1");
				errors.rejectValue("branchControlName", "branchManagement.id");
			}
		} else {
			System.out.println("Inside if id is less than 1 no its null ");
			errors.rejectValue("branchControlName", "branchManagement.id");
		}

		System.out.println("Phone number : " + branchManagement.getBranchContactNo());
		if (null != branchManagement.getBranchContactNo()) {
			if (!(branchManagement.getBranchContactNo() != null && branchManagement.getBranchContactNo().isEmpty())) {
				pattern = Pattern.compile(MOBILE_PATTERN);
				matcher = pattern.matcher(branchManagement.getBranchContactNo());
				if (!matcher.matches()) {
					errors.rejectValue("branchContactNo", "branch.incorrect.contactno");
				}
			}
		}
		
		System.out.println("Email verification : " + branchManagement.getBranchEmail());
		if (null != branchManagement.getBranchEmail()) {
			if (!(branchManagement.getBranchEmail()!= null && branchManagement.getBranchEmail().isEmpty())) {
				pattern = Pattern.compile(EMAIL_PATTERN);
				matcher = pattern.matcher(branchManagement.getBranchEmail()); 
				if (!matcher.matches()) {
					errors.rejectValue("branchEmail", "branch.email");
				}
			}
		}

	}

}
