package com.emorphis.cashmanagement.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.DepartmentDao;
import com.emorphis.cashmanagement.model.Department;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.service.DepartmentService;
import com.emorphis.cashmanagement.util.Utility;

@Repository
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentDao dao;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Override
	public List<Department> getAllDepartmentListByLowerHierarchy(int hierarchyLevel) {

		return dao.getAllDepartmentListByLowerHierarchy(hierarchyLevel);
	}

	@Override
	public List<Department> getAllDepartmentList() {
		return dao.getAllDepartmentList();
	}

	@Override
	public boolean save(Department department) {
		department.setCreatedBy(mySession.getUser().getId());
		department.setModifiedBy(mySession.getUser().getId());
		department.setCreatedTime(new Date());
		department.setModifiedTime(new Date());
		department.setIp(mySession.getIp());
		String uuid = utility.getDepartmentUUID();
		if (uuid != null) {
			department.setId(uuid);
		}
		dao.save(department);
		return true;
	}

	@Override
	public Department findById(String id) {
		return dao.findById(id);
	}

	@Override
	public void updatebyId(Department department1) {
		Department department = dao.findById(department1.getId());
		department.setModifiedTime(new Date());
		department.setModifiedBy(mySession.getUser().getId());
		department.setHierarchyControl(department1.getHierarchyControl());
		department.setTitle(department1.getTitle());
		department.setIp(mySession.getIp());
	}
	
	@Override
	public void deleteById(String id) {
		boolean flag = false;
		Department department = dao.findById(id);
		flag = true;
		department.setDeleted((byte) 1);
		department.setModifiedBy(this.mySession.getUser().getId());
		department.setModifiedTime(new Date());	
		department.setIp(mySession.getIp());
	}
	

	@Override
	public List<Department> getAllDepartmentListbyhierarchy(HierarchyControl hierarchyControl) {
		return dao.getAllDepartmentListbyhierarchy(hierarchyControl);
	}
	
	@Override
	public List<Department> getAllDepartmentListAccToHierarchyOrganization(Department department) {
		return dao.getAllDepartmentListAccToHierarchyOrganization(department);
	}
	
	@Override
	public List<Department> getByHierarchyId(String hId) {
		
		return dao.getByHierarchyId(hId);
	}
	
}
