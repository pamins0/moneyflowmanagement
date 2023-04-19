package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.Designation;
import com.emorphis.cashmanagement.model.HierarchyControl;

public interface DesignationDao {

	List<Designation> getAllDesignationList();

	void save(Designation designation)throws Exception;

	Designation findById(String id);
	
	List<Designation> getByHierarchyId (String hId);

	List<Designation> getAllDesignationListbyhierarchy(HierarchyControl hierarchyControl);

	List<Designation> getAllDesignationListByLowerHierarchy(int hierarchyLevel);

	List<Designation> getAllDesignationListAccToHierarchyOrganization(Designation designation);

	Designation findByUUID(String uuids); 

} 
