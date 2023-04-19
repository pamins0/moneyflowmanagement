package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.HierarchyManagementDao;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.OrgManagement;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class HierarchyManagementdaoImpl extends AbstractDao<Integer, HierarchyControl>
		implements HierarchyManagementDao {

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	@Autowired
	CommonFilters commonFilters;

	public int save(HierarchyControl hierarchyControl2) {
		int id =0; 
		getSession().save(hierarchyControl2);
		System.out.println("Hierarchical management generaed id after save : " + id);
		return id;
	}

	public List<HierarchyControl> getAllHierarchyControlList() {
		boolean flag = false;  
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("orgManagement.id")).addOrder(Order.asc("hierarchyLevel"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getHierarchicalControllevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManagement.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));		
		}
		List<HierarchyControl> hierarchyControlsList = criteria.list();

		return hierarchyControlsList;
	}

	public HierarchyControl findById(String id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		criteria = this.commonFilters.getHierarchicalControllevelAliasForDelete(criteria);
		HierarchyControl hierarchyControl = (HierarchyControl) criteria.uniqueResult();
		return hierarchyControl;
	}

	public HierarchyControl findByOrgAndLevel(OrgManagement orgManagement, int level) {
		Criteria criteria = createEntityCriteria();
		System.out.println("orgManag id : " + orgManagement.getId() + " and hierarchy level is : " + level);
		criteria.add(Restrictions.eq("orgManagement.id", orgManagement.getId()));
		criteria.add(Restrictions.eq("hierarchyLevel", level));
		HierarchyControl hierarchyControl = (HierarchyControl) criteria.uniqueResult();
		return hierarchyControl;
	}

	public List<HierarchyControl> getHierarchyListAccToUserLevel() {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyLevel"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getHierarchicalControllevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			boolean flagForCC = (utility.isAllowed("can_currencychest_create")||utility.isAllowed("can_currencychest_read")||utility.isAllowed("can_currencychest_update")||utility.isAllowed("can_currencychest_delete"));
			if(!flagForCC){
				criteria.add(Restrictions.eq("hierarchyType", 0));
			}
			criteria.add(Restrictions.eq("orgManagement.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));
			criteria.add(Restrictions.ge("hierarchyLevel", Integer.valueOf(
					this.mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyLevel())));
		}
		
		
		
		List<HierarchyControl> hierarchyControlsList = criteria.list();

		return hierarchyControlsList;
	}
	
	@Override
	public List<HierarchyControl> getHierarchyListAccToBranchLevel() {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyLevel"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getHierarchicalControllevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			boolean flagForCC = (utility.isAllowed("can_currencychest_create")||utility.isAllowed("can_currencychest_read")||utility.isAllowed("can_currencychest_update")||utility.isAllowed("can_currencychest_delete"));
			if(!flagForCC){
				criteria.add(Restrictions.eq("hierarchyType", 0));
			}
			criteria.add(Restrictions.eq("orgManagement.id",
					this.mySession.getUser().getBranchManagement().getOrgManagement().getId()));
			criteria.add(Restrictions.gt("hierarchyLevel", Integer.valueOf(
					this.mySession.getUser().getBranchManagement().getHierarchyControl().getHierarchyLevel())));
		}
		
		
		
		List<HierarchyControl> hierarchyControlsList = criteria.list();

		return hierarchyControlsList;
	}

	public List<HierarchyControl> getAllHierarchyControlListAccToOrganization(HierarchyControl hierarchyControl) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id")).addOrder(Order.asc("hierarchyLevel"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getHierarchicalControllevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {

		} else {
			System.out.println("Inside super admin......" + hierarchyControl);
			if (null != hierarchyControl.getOrgManagement() && !"-1".equals(hierarchyControl.getOrgManagement().getId())) {
				System.out.println("Inside super admin......" + hierarchyControl.getOrgManagement().getId());
				criteria.add(Restrictions.eq("orgManage.id", hierarchyControl.getOrgManagement().getId()));
			}
			if (null != hierarchyControl.getOrgManagement().getOrgType()
					&& !"-1".equals(hierarchyControl.getOrgManagement().getOrgType().getId())) {
				System.out
						.println("Inside super admin......" + hierarchyControl.getOrgManagement().getOrgType().getId());
				criteria.add(Restrictions.eq("orgType.id", hierarchyControl.getOrgManagement().getOrgType().getId()));
			}
 
		}
		List<HierarchyControl> hierarchyControlsList = criteria.list();

		return hierarchyControlsList;
	}

	public List<HierarchyControl> getAllHierarchyListByOrgmanagemet(String orgManagementid) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyLevel"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getHierarchicalControllevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {

		} else {
			System.out.println("Inside super admin......" + orgManagementid);
			criteria.add(Restrictions.eq("orgManage.id", orgManagementid));
		}
		List<HierarchyControl> hierarchyControlsList = criteria.list();

		return hierarchyControlsList;
	}
	
	@Override
	public List<HierarchyControl> getAllHierarchyControlListByOrgId(String id) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyLevel"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getHierarchicalControllevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id", id));
		} else {
			System.out.println("Inside super admin......" + id);
			criteria.add(Restrictions.eq("orgManage.id", id));
		}
		List<HierarchyControl> hierarchyControlsList = criteria.list();

		return hierarchyControlsList;
	}

	/**
	 * UUID Concept begins
	 */

	@Override
	public HierarchyControl findByUUID(String uuid) {
		Criteria criteria = createEntityCriteria();
	//	criteria = this.commonFilters.getHierarchicalControllevelAliasForDelete(criteria);
		criteria.add(Restrictions.eq("id", uuid));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		HierarchyControl hierarchyControl = (HierarchyControl) criteria.uniqueResult();
		return hierarchyControl;
	}

}
