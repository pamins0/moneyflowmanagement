package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.Designation;
import com.emorphis.cashmanagement.model.HierarchyControl;

public interface DesignationService {

	boolean save(Designation designation) throws Exception;

	List<Designation> getAllDesignationList();

	Designation findById(String id);

	void updatebyId(Designation designation);

	void deleteById(String id);
	
	List<Designation> getByHierarchyId(String hId); 

	List<Designation> getAllDesignationListbyhierarchy(HierarchyControl hierarchyControl);

	List<Designation> getAllDesignationListByLowerHierarchy(int hierarchyLevel);

	List<Designation> getAllDesignationListAccToHierarchyOrganization(Designation designation); 

}
