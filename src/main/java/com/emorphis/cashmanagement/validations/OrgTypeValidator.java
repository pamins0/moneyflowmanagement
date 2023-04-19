package com.emorphis.cashmanagement.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.emorphis.cashmanagement.model.OrgType;

@Component
public class OrgTypeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("Inside support.....");
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {
		OrgType orgType = (OrgType) target;
		String id  = orgType.getId();
		System.out.println("Id in validator is : "+id); 
		if(id.equals("-1")){
			System.out.println("Inside if id is less than 1");
			errors.rejectValue("orgType.id", "orgType.id"); 
		}
	}
	
}
