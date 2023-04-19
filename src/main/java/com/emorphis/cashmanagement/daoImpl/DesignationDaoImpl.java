package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.DesignationDao;
import com.emorphis.cashmanagement.model.Designation;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.RequestScopedPermissions;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class DesignationDaoImpl extends AbstractDao<String, Designation> implements DesignationDao {

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	RequestScopedPermissions requestScopedPermissions;

	@Autowired
	CommonFilters commonFilters;

	@SuppressWarnings("unchecked")
	public List<Designation> getAllDesignationList() {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getDesignationManagementlevelAliasForDelete(criteria);

		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		List<Designation> designations = (List<Designation>) criteria.list();
		return designations;
	}

	public void save(Designation designation) throws Exception{
		persist(designation);
	}

	public Designation findById(String id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<Designation> getByHierarchyId(String hId) {
		Criteria criteria = createEntityCriteria();
		criteria = this.commonFilters.getDesignationManagementlevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("hierarchyControl.id", hId));
		List<Designation> designations = criteria.list();
		return designations;
	}

	public List<Designation> getAllDesignationListbyhierarchy(HierarchyControl hierarchyControl) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getDesignationManagementlevelAliasForDelete(criteria);

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
		List<Designation> designations = (List<Designation>) criteria.list();
		return designations;
	}

	public List<Designation> getAllDesignationListByLowerHierarchy(int hierarchyLevel) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getDesignationManagementlevelAliasForDelete(criteria);

		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));
			criteria.add(Restrictions.ge("hier.hierarchyLevel", hierarchyLevel));
		}
		List<Designation> designations = (List<Designation>) criteria.list();
		return designations;
	}

	public List<Designation> getAllDesignationListAccToHierarchyOrganization(Designation designation) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getDesignationManagementlevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {

		} else {
			System.out.println("Inside super admin......" + designation.getHierarchyControl().getId());
			if (null != designation.getHierarchyControl() && !"-1".equals(designation.getHierarchyControl().getId())) {
				System.out.println(
						"Inside super admin hierarchy id is ......" + designation.getHierarchyControl().getId());
				criteria.add(Restrictions.eq("hier.id", designation.getHierarchyControl().getId()));
			}
			if (null != designation.getHierarchyControl().getOrgManagement()
					&& !"-1".equals(designation.getHierarchyControl().getOrgManagement().getId())) {
				System.out.println("Inside super admin organization id is ......"
						+ designation.getHierarchyControl().getOrgManagement().getId());
				criteria.add(
						Restrictions.eq("orgManage.id", designation.getHierarchyControl().getOrgManagement().getId()));
			}
			if (null != designation.getHierarchyControl().getOrgManagement().getOrgType()
					&& !"-1".equals(designation.getHierarchyControl().getOrgManagement().getOrgType().getId())) {
				System.out.println("Inside super org type id admin......"
						+ designation.getHierarchyControl().getOrgManagement().getOrgType().getId());
				criteria.add(Restrictions.eq("orgType.id",
						designation.getHierarchyControl().getOrgManagement().getOrgType().getId()));
			}
		}
		List<Designation> designationList = criteria.list();

		return designationList;
	}

	@Override
	public Designation findByUUID(String uuids) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Designation designation = (Designation) criteria.uniqueResult();
		return designation;
	}
}
