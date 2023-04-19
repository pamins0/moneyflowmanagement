package com.emorphis.cashmanagement.dao;

import java.util.List;

import com.emorphis.cashmanagement.model.Department;
import com.emorphis.cashmanagement.model.HierarchyControl;

public interface DepartmentDao {

	List<Department> getAllDepartmentListByLowerHierarchy(int hierarchyLevel);

	List<Department> getAllDepartmentList();

	Department findByUUID(String uuids);

	void save(Department department);

	Department findById(String id);

	List<Department> getAllDepartmentListbyhierarchy(HierarchyControl hierarchyControl);

	List<Department> getAllDepartmentListAccToHierarchyOrganization(Department department);

	List<Department> getByHierarchyId(String hId);       

}
