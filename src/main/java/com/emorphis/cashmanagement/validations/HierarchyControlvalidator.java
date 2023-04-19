package com.emorphis.cashmanagement.validations;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.emorphis.cashmanagement.model.HierarchyControl;

@Component
public class HierarchyControlvalidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		System.out.println("Inside support.....");
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {
		HierarchyControl hierarchyControl = (HierarchyControl) target;
		String id  = hierarchyControl.getId();
		System.out.println("Id in validator is : "+id); 
		if(id.equals("-1") || id.equals("0")){
			System.out.println("Inside if of hierarchyControl validator id is less than 1");
			errors.rejectValue("hierarchyControl.id", "hierarchyControl.id"); 
		}
	}

}
