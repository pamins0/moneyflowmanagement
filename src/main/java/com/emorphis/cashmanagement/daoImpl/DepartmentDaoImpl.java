package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.DepartmentDao;
import com.emorphis.cashmanagement.model.Department;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.RequestScopedPermissions;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class DepartmentDaoImpl extends AbstractDao<String, Department> implements DepartmentDao {


	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	RequestScopedPermissions requestScopedPermissions;

	@Autowired
	CommonFilters commonFilters;

	
	@Override
	public List<Department> getAllDepartmentListByLowerHierarchy(int hierarchyLevel) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getDepartmentManagementlevelAliasForDelete(criteria);

		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));
			criteria.add(Restrictions.ge("hier.hierarchyLevel", hierarchyLevel));
		}
		List<Department> departments = (List<Department>) criteria.list();
		return departments;
	}
	
	@Override
	public List<Department> getAllDepartmentList() {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getDepartmentManagementlevelAliasForDelete(criteria);

		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		List<Department> departments = (List<Department>) criteria.list();
		return departments;
	}
	
	@Override
	public Department findByUUID(String uuids) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Department department = (Department) criteria.uniqueResult();
		return department;
	}
	
	@Override
	public void save(Department department) {
		persist(department);
	}
	
	@Override
	public Department findById(String id) {
		return getByKey(id);
	}
	
	@Override
	public List<Department> getAllDepartmentListbyhierarchy(HierarchyControl hierarchyControl) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getDepartmentManagementlevelAliasForDelete(criteria);

		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));
			criteria.add(Restrictions.ge("hier.hierarchyLevel",
					mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyLevel()));
		}
		if (null != hierarchyControl && !"-1".equals(hierarchyControl.getId())) {
			criteria.add(Restrictions.eq("hierarchyControl.id", hierarchyControl.getId()));
		}
		List<Department> departments = (List<Department>) criteria.list();
		return departments;
	}
	
	@Override
	public List<Department> getAllDepartmentListAccToHierarchyOrganization(Department department) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getDepartmentManagementlevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {

		} else {
			System.out.println("Inside super admin......" + department.getHierarchyControl().getId());
			if (null != department.getHierarchyControl() && !"-1".equals(department.getHierarchyControl().getId())) {
				System.out.println(
						"Inside super admin hierarchy id is ......" + department.getHierarchyControl().getId());
				criteria.add(Restrictions.eq("hier.id", department.getHierarchyControl().getId()));
			}
			if (null != department.getHierarchyControl().getOrgManagement()
					&& !"-1".equals(department.getHierarchyControl().getOrgManagement().getId())) {
				System.out.println("Inside super admin organization id is ......"
						+ department.getHierarchyControl().getOrgManagement().getId());
				criteria.add(
						Restrictions.eq("orgManage.id", department.getHierarchyControl().getOrgManagement().getId()));
			}
			if (null != department.getHierarchyControl().getOrgManagement().getOrgType()
					&& !"-1".equals(department.getHierarchyControl().getOrgManagement().getOrgType().getId())) {
				System.out.println("Inside super org type id admin......"
						+ department.getHierarchyControl().getOrgManagement().getOrgType().getId());
				criteria.add(Restrictions.eq("orgType.id",
						department.getHierarchyControl().getOrgManagement().getOrgType().getId()));
			}
		}
		List<Department> departmentList = criteria.list();

		return departmentList;
	}
	
	@Override
	public List<Department> getByHierarchyId(String hId) {
		Criteria criteria = createEntityCriteria();
		criteria = this.commonFilters.getDepartmentManagementlevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("hierarchyControl.id", hId));
		List<Department> departments = criteria.list();
		return departments;
	}
}
