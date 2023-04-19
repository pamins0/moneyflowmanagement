package com.emorphis.cashmanagement.service;

import java.util.List;

import com.emorphis.cashmanagement.model.Department;
import com.emorphis.cashmanagement.model.HierarchyControl;

public interface DepartmentService {

	List<Department> getAllDepartmentListByLowerHierarchy(int hierarchyLevel);

	List<Department> getAllDepartmentList();

	boolean save(Department department);

	Department findById(String id);

	void updatebyId(Department department);

	void deleteById(String id);

	List<Department> getAllDepartmentListbyhierarchy(HierarchyControl hierarchyControl);

	List<Department> getAllDepartmentListAccToHierarchyOrganization(Department department);

	List<Department> getByHierarchyId(String hId);       

}
