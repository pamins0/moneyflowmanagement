package com.emorphis.cashmanagement.daoImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BranchManagementDao;
import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.HierarchyControl;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.util.CommonFilters;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class BranchManagementDaoImpl extends AbstractDao<String, BranchManagement> implements BranchManagementDao {

	private static final Logger log = LoggerFactory.getLogger(BranchClosedGroupDaoImpl.class);

	@Autowired
	CommonFilters commonFilters;

	@Autowired
	MySession mySession;

	@Autowired
	Utility utility;

	public BranchManagement findById(String id) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("id", id));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		BranchManagement branchManagement = (BranchManagement) criteria.uniqueResult();

		return branchManagement;
		// return getByKey(id);
	}

	@Override
	public BranchManagement findByIdWithDelete(String id) {
		Criteria criteria = createEntityCriteria();
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("id", id));
		// commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		BranchManagement branchManagement = (BranchManagement) criteria.uniqueResult();

		return branchManagement;
	}

	@SuppressWarnings("unchecked")
	public List<BranchManagement> getAllBranch() {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);

		/*
		 * criteria.createAlias("orgManagement", "org");
		 * criteria.createAlias("org.orgType", "orgTy");
		 * criteria.add(Restrictions.eq("org.deleted", (byte) 0));
		 * criteria.add(Restrictions.eq("orgTy.deleted", (byte) 0));
		 * criteria.add(Restrictions.eq("deleted", (byte) 0));
		 */
		// criteria.add(Restrictions.eq("createdBy",
		// mySession.getUser().getId()));

		// List<User> userList = user

		flag = utility.isAllowed("can_view_all_organizationmanagement");
		if (!flag) {
			flag = utility.isAllowed("can_view_all_branchmanagement");
			if (!flag) {
				criteria.add(Restrictions.eq("id", mySession.getUser().getBranchManagement().getId()));
			} else {
				criteria.add(Restrictions.eq("orgManagement.id",
						mySession.getUser().getBranchManagement().getOrgManagement().getId()));
			}
		}

		/**
		 * Alternate of above return code...
		 */

		List<BranchManagement> list = (List<BranchManagement>) criteria.list();
		return list;
	}

	public int save(BranchManagement management) {
		int id = 0;
		getSession().save(management);
		// persist(management);
		return id;
	}

	public void deleteById(Integer id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		BranchManagement branchManagement = (BranchManagement) crit.uniqueResult();
		delete(branchManagement);
	}

	@SuppressWarnings("unchecked")
	public List<BranchManagement> getAllBranchManagementListByOrgmanagemet(String orgManagementid) {
		List<BranchManagement> branchManagementList = new ArrayList<BranchManagement>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("branchName"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.add(Restrictions.eq("orgManagement.id", orgManagementid));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		branchManagementList = criteria.list();
		return branchManagementList;
	}

	public List<BranchManagement> getAllBranchManagementListByHierarchyId(String parentId) {
		boolean flag = false;
		List<BranchManagement> branchManagementList = new ArrayList<BranchManagement>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.add(Restrictions.eq("hierarchyControl.id", parentId));
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManagement.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		branchManagementList = criteria.list();
		return branchManagementList;
	}

	public List<BranchManagement> getAllBranchNotGrouped() {
		boolean flag = false;
		List<BranchManagement> branchManagementList = new ArrayList<BranchManagement>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("isgroup", (byte) 0));
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManagement.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		branchManagementList = criteria.list();
		return branchManagementList;
	}

	public List<BranchManagement> getAllBranchGrouped() {
		boolean flag = false;
		List<BranchManagement> branchManagementList = new ArrayList<BranchManagement>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("isgroup", (byte) 1));
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManagement.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		branchManagementList = criteria.list();
		return branchManagementList;
	}

	public List<BranchManagement> getAllBranchManagementListByHierarchyIdAndTagName(String hierarchyId,
			String tagName) {
		boolean flag = false;
		List<BranchManagement> branchManagementList = new ArrayList<BranchManagement>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.add(Restrictions.eq("hierarchyControl.id", hierarchyId));
		criteria.add(Restrictions.ilike("branchName", tagName, MatchMode.ANYWHERE));
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManagement.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		branchManagementList = criteria.list();
		return branchManagementList;
	}

	public List<BranchManagement> getAllBranchManagementListByHierarchyIdAndAutoComleteNameForBranchControl(
			String hierarchyId, String tagName) {
		boolean flag = false;
		List<BranchManagement> branchManagementList = new ArrayList<BranchManagement>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("hierarchyControl.id", hierarchyId));
		criteria.add(Restrictions.ilike("branchName", tagName.trim(), MatchMode.ANYWHERE));
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManagement.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		branchManagementList = criteria.list();
		System.out.println("branch management list size : " + branchManagementList.size());
		return branchManagementList;
	}

	public Object getAllBranchGroupedAndNotgrouped() {
		boolean flag = false;
		List<BranchManagement> branchManagementList = new ArrayList<BranchManagement>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// criteria.add(Restrictions.eq("isgroup", (byte) 1));
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManagement.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		branchManagementList = criteria.list();
		return branchManagementList;
	}

	public List<BranchManagement> getAllBranchListbyhierarchy(HierarchyControl hierarchyControl) {
		boolean flag = false;
		List<BranchManagement> branchManagementList = new ArrayList<BranchManagement>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("createdTime"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManagement.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		criteria.add(Restrictions.eq("hierarchyControl.id", hierarchyControl.getId()));
		branchManagementList = criteria.list();
		return branchManagementList;
	}

	public List<BranchManagement> getAllBranchListAccToHierarchyOrganization(BranchManagement branchManagement) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("createdTime"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria = this.commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {

		} else {
			if (null != branchManagement.getHierarchyControl()
					&& !"-1".equals(branchManagement.getHierarchyControl().getId())) {
				System.out.println(
						"Inside super admin hierarchy id is ......" + branchManagement.getHierarchyControl().getId());
				criteria.add(Restrictions.eq("hier.id", branchManagement.getHierarchyControl().getId()));
			}
			if (null != branchManagement.getHierarchyControl().getOrgManagement()
					&& !"-1".equals(branchManagement.getHierarchyControl().getOrgManagement().getId())) {
				System.out.println("Inside super admin organization id is ......"
						+ branchManagement.getHierarchyControl().getOrgManagement().getId());
				criteria.add(Restrictions.eq("orgManage.id",
						branchManagement.getHierarchyControl().getOrgManagement().getId()));
			}
			if (null != branchManagement.getHierarchyControl().getOrgManagement().getOrgType()
					&& !"-1".equals(branchManagement.getHierarchyControl().getOrgManagement().getOrgType().getId())) {
				System.out.println("Inside super org type id admin......"
						+ branchManagement.getHierarchyControl().getOrgManagement().getOrgType().getId());
				criteria.add(Restrictions.eq("orgType.id",
						branchManagement.getHierarchyControl().getOrgManagement().getOrgType().getId()));
			}
		}
		List<BranchManagement> branchManagementList = criteria.list();

		return branchManagementList;
	}

	public List<BranchManagement> getAllBranchListbyBranchHierarchyAndLowerLevelBranches(
			BranchManagement branchManagement, List<BranchManagement> branchListLowerLevel) {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("createdTime"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("chest", (byte) 0));
		criteria = this.commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			// System.out.println("Inside not super admin......" +
			// branchManagement.getHierarchyControl().getId());

		} else {

		}

		if (branchListLowerLevel != null) {
			List<String> ids = branchListLowerLevel.stream().map(BranchManagement::getId).collect(Collectors.toList());
			System.out.println("ids:::>>>>" + ids);
			criteria.add(Restrictions.in("id", ids));
		}

		if (null != branchManagement && null != branchManagement.getHierarchyControl()
				&& !"-1".equals(branchManagement.getHierarchyControl().getId())) {
			System.out.println(
					"Inside super admin hierarchy id is ......" + branchManagement.getHierarchyControl().getId());
			criteria.add(Restrictions.eq("hier.id", branchManagement.getHierarchyControl().getId()));

		}

		if (null != branchManagement && null != branchManagement.getHierarchyControl()
				&& null != branchManagement.getHierarchyControl().getOrgManagement()
				&& !"-1".equals(branchManagement.getHierarchyControl().getOrgManagement().getId())) {
			System.out.println("Inside super admin organization id is ......"
					+ branchManagement.getHierarchyControl().getOrgManagement().getId());
			criteria.add(
					Restrictions.eq("orgManage.id", branchManagement.getHierarchyControl().getOrgManagement().getId()));
		}

		if (null != branchManagement && null != branchManagement.getHierarchyControl()
				&& null != branchManagement.getHierarchyControl().getOrgManagement().getOrgType()
				&& !"-1".equals(branchManagement.getHierarchyControl().getOrgManagement().getOrgType().getId())) {
			System.out.println("Inside super org type id admin......"
					+ branchManagement.getHierarchyControl().getOrgManagement().getOrgType().getId());
			criteria.add(Restrictions.eq("orgType.id",
					branchManagement.getHierarchyControl().getOrgManagement().getOrgType().getId()));
		}
		List<BranchManagement> branchManagementList = criteria.list();

		return branchManagementList;
	}

	@Override
	public List<BranchManagement> getAvailableBranchCode(BranchManagement branch) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.ne("id", branch.getId()));
		crit.add(Restrictions.or(Restrictions.eq("branchCode", branch.getBranchCode()),
				Restrictions.eq("branchEmail", branch.getBranchEmail())));
		boolean flag = utility.isAllowed("can_view_everything");
		if (!flag) {
			crit.add(Restrictions.eq("orgManagement.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<BranchManagement> branchManagementList = crit.list();
		return branchManagementList;
	}

	@Override
	public List<BranchManagement> getAllBranchesListForMailUtility() {
		boolean flag = false;
		List<BranchManagement> branchManagementList = new ArrayList<BranchManagement>();
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("hierarchyControl.id"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			/*
			 * criteria.add(Restrictions.eq("orgManagement.id",
			 * mySession.getUser().getBranchManagement().getOrgManagement().
			 * getId()));
			 */

			/**
			 * This is only for yes bank intra biddng request
			 */
			criteria.add(Restrictions.eq("orgManagement.id", 52));
		}
		branchManagementList = criteria.list();
		return branchManagementList;
	}

	@Override
	public List<BranchManagement> getAllBranchListByBranchHierarchy(BranchManagement branchManagement) {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("createdTime"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// criteria.add(Restrictions.eq("branchControl",
		// branchManagement.getBranchControl()));
		criteria.add(Restrictions.ne("id", branchManagement.getId()));
		criteria.add(Restrictions.eq("isgroup", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.id", branchManagement.getOrgManagement().getId()));
		return criteria.list();
	}

	/**
	 * @author gourav is use for getting remaining branch list for closed group
	 */

	@Override
	public List<BranchManagement> getRemainingBranchListByBranchHierarchy(List<BranchGroup> branchGroupList) {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("createdTime"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("isgroup", Byte.valueOf((byte) 0)));
		Set<String> branchIdList = branchGroupList.stream().map(p -> p.getBranchManagement().getId())
				.collect(Collectors.toSet());
		/*
		 * boolean flag =
		 * this.utility.isAllowed("can_view_everything").booleanValue(); if
		 * (!flag) { criteria.add(Restrictions.eq("orgManage.id",
		 * mySession.getUser().getBranchManagement().getOrgManagement().getId())
		 * ); }
		 */
		criteria.add(
				Restrictions.eq("orgManage.id", mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		if (branchIdList != null && branchIdList.size() > 0) {
			criteria.add(Restrictions.not(Restrictions.in("id", branchIdList)));
		}
		// if(branchGroupList!=null && branchGroupList.size()>0){
		criteria.add(Restrictions.or(Restrictions.eq("hier.hierarchyType", 1), Restrictions.isEmpty("branchGroups")));
		// criteria.add(Restrictions.isEmpty("branchGroups"));
		// }
		return criteria.list();
	}

	@Override
	public void deletClosedGroupSetByParentBranch(BranchManagement branchManagement,
			Set<BranchClosedGroup> removeUserRoleSet) throws Exception {
		List<String> closedGroupBranchIdList = new ArrayList<String>();
		/*Iterator<BranchClosedGroup> closedGroupBranchSet = removeUserRoleSet.iterator();		
		while (closedGroupBranchSet.hasNext()) {
			BranchClosedGroup branchClosedGroup = (BranchClosedGroup) closedGroupBranchSet.next();
			closedGroupBranchIdList.add(branchClosedGroup.getClosedGroupBranch().getId());
		}*/
		for (BranchClosedGroup branchClosedGroup : removeUserRoleSet) {
			log.info("closed branches id for parent branch : " + branchManagement.getBranchName() + " is : "
					+ branchClosedGroup.getClosedGroupBranch().getId());
			closedGroupBranchIdList.add(branchClosedGroup.getClosedGroupBranch().getId());
		}
		closedGroupBranchIdList.forEach(p -> log.info("closedBranchList iteration is : " + p));

		Session session = getSession();
		String join = "'" + StringUtils.join(closedGroupBranchIdList, "','") + "'";
		String hql = "delete from BranchClosedGroup where closedGroupBranch.id in (" + join + ") and parentBranch.id='"
				+ branchManagement.getId() + "'";
		System.out.println("HQL QUERY FOR DELETE BRANCHcLOSEDgROUP IS : " + hql);
		Query query = session.createQuery(hql);
		/* System.out.println("Query to delete userRoles is : " + query); */
		System.out.println("Query HQL : " + query.executeUpdate());

	}

	@Override
	public void deletCrossClosedGroupSetByParentBranch(BranchManagement branchManagement,
			Set<BranchClosedGroup> removeUserRoleSet) {
		Iterator<BranchClosedGroup> closedGroupBranchSet = removeUserRoleSet.iterator();
		List<String> closedGroupBranchIdList = new ArrayList<String>();
		while (closedGroupBranchSet.hasNext()) {
			BranchClosedGroup branchClosedGroup = (BranchClosedGroup) closedGroupBranchSet.next();
			closedGroupBranchIdList.add(branchClosedGroup.getClosedGroupBranch().getId());
		}
		System.out.println("closedGroupBranchIdList to delete the closedbranch of parent branch from db is : "
				+ closedGroupBranchIdList.iterator().next());
		Session session = getSession();
		String join = "'" + StringUtils.join(closedGroupBranchIdList, ',') + "'";
		String hql = "delete from BranchClosedGroup where parentBranch.id in (" + join + ") and closedGroupBranch.id='"
				+ branchManagement.getId() + "'";
		Query query = session.createQuery(hql);
		/* System.out.println("Query to delete userRoles is : " + query); */
		System.out.println("Query HQL : " + query.executeUpdate());
	}

	/**
	 * Currency Chest Methods
	 * 
	 */

	@Override
	public List<BranchManagement> getAllCurrencyChestList() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		commonFilters.getCurrencyChestlevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		// criteria.add(Restrictions.eq("branchControl",
		// branchManagement.getBranchControl()));
		criteria.add(Restrictions.eq("hier.hierarchyType", Byte.valueOf((byte) 1)));
		criteria.add(Restrictions.eq("isgroup", Byte.valueOf((byte) 0)));
		boolean flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		return criteria.list();
	}

	@Override
	public List<BranchManagement> getAllCurrencyChestListExcludingBranch(Set<BranchManagement> branchCCListFromGroup) {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("id"));
		commonFilters.getCurrencyChestlevelAliasForDelete(criteria);
		Set<String> branchIdList = new HashSet<>();
		if (branchCCListFromGroup != null && branchCCListFromGroup.size() > 0) {
			branchIdList = branchCCListFromGroup.stream().map(p -> p.getId()).collect(Collectors.toSet());
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("hier.hierarchyType", Byte.valueOf((byte) 1)));
		criteria.add(Restrictions.eq("isgroup", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.in("id", branchIdList));

		boolean flag = this.utility.isAllowed("can_view_everything").booleanValue();
		if (!flag) {
			criteria.add(Restrictions.eq("orgManage.id",
					mySession.getUser().getBranchManagement().getOrgManagement().getId()));
		}
		return criteria.list();
	}

	@Override
	public BranchManagement findByUUID(String uuid) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuid));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		BranchManagement branchManagement = (BranchManagement) criteria.uniqueResult();
		return branchManagement;
	}

	@Override
	public List<BranchManagement> getAllBranchManagementListForClusterMaking(BranchManagement branchManagement) {
		Criteria criteria = createEntityCriteria();
		criteria.createAlias("hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		// criteria.add(Restrictions.not(Restrictions.eq("id",
		// branchManagement.getId())));
		criteria.add(Restrictions.eq("isgroup", Byte.valueOf((byte) 0)));
		criteria.add(
				Restrictions.eq("orgManage.id", branchManagement.getHierarchyControl().getOrgManagement().getId()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List<BranchManagement> branchManagementsList = criteria.list();
		return branchManagementsList;
	}
	
	@Override
	public List<BranchManagement> getCorrespondentBranchList(BranchManagement branchManagement,
			Set<BranchManagement> associatedCorrespondentBranchesList) {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("branchName"));
		commonFilters.getBranchManagementLevelAliasForDelete(criteria);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("isgroup", Byte.valueOf((byte) 0)));
		Set<String> branchIdList = associatedCorrespondentBranchesList.stream().map(p -> p.getId())
				.collect(Collectors.toSet());
		criteria.add(Restrictions.not(Restrictions.eq("orgManage.id", branchManagement.getHierarchyControl().getOrgManagement().getId())));
		criteria.add(Restrictions.eq("orgType.id", branchManagement.getHierarchyControl().getOrgManagement().getOrgType().getId()));
		if (branchIdList != null && branchIdList.size() > 0) {
			criteria.add(Restrictions.not(Restrictions.in("id", branchIdList)));
		}		
		
		return criteria.list();
	}
}
