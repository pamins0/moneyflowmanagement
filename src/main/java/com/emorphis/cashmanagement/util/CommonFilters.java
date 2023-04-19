package com.emorphis.cashmanagement.util;

import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.model.UserRole;

@Component
public class CommonFilters {

	@Autowired
	MySession mySession;

	public Criteria getUserLevelAliasForDelete(Criteria criteria) {
		System.out.println("Inside Alias For delete in CommonFilter Class session user value : "
				+ this.mySession.getUser().getFirstName());
		criteria.createAlias("branchManagement", "branch");
		criteria.createAlias("branch.hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("branch.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("hier.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));

		return criteria;
	}

	public Criteria getBranchManagementLevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("hier.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));

		return criteria;
	}
	
	public Criteria getDashboardFinalBidAliasForDelete(Criteria criteria) {
		
		criteria.createAlias("requestStatus", "request");
		
		criteria.createAlias("branchManagement", "branch");
		criteria.createAlias("branch.hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("branch.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("hier.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
//		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));
		
		return criteria;
	}

	public Criteria getBranchAccountLevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("branchManagement", "branch");
		criteria.createAlias("branch.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("branch.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));

		return criteria;
	}

	public Criteria getBranchContactLevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("branchManagement", "branch");
		criteria.createAlias("branch.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("branch.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));

		return criteria;
	}

	public Criteria getOrganizationLevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("orgType", "org");

		criteria.add(Restrictions.eq("org.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));

		return criteria;
	}

	public Criteria getOrganizationTypeLevelAliasForDelete(Criteria criteria) {
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));

		return criteria;
	}

	public Criteria getHierarchicalControllevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));

		return criteria;
	}
	
	public Criteria getCurrencyChestlevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("hier.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));
		
		return criteria;
	}

	public Criteria getRoleManagementlevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("hier.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));

		return criteria;
	}
	
	public Criteria getDesignationManagementlevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("hier.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));

		return criteria;
	}
	
	public Criteria getDepartmentManagementlevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("hier.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		
		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));

		return criteria;
	}

	public boolean getSuperAdmin() {
		boolean flag = false;
		Set<UserRole> userRoleSet = this.mySession.getUser().getUserRoles();
		if (userRoleSet != null) {
			for (UserRole userRole : userRoleSet) {
				if (userRole.getRole().getTitle().trim().equals("Super Admin")) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public boolean getOrgAdmin() {
		boolean flag = false;
		Set<UserRole> userRoleSet = this.mySession.getUser().getUserRoles();
		if (userRoleSet != null) {
			for (UserRole userRole : userRoleSet) {
				if (userRole.getRole().getTitle().trim().equals("Organization Admin")) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public Criteria getPlaceCashRequestBidAliasForDelete(Criteria criteria) {
		criteria.createAlias("requestStatus", "request");
		
		criteria.createAlias("branchManagementRequestedFrom", "branch");
		criteria.createAlias("branch.hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("branch.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("hier.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		
		criteria.createAlias("branchManagementRequestedTo", "branch1");
		criteria.createAlias("branch1.hierarchyControl", "hier1");
		criteria.createAlias("hier1.orgManagement", "orgManage1");
		criteria.createAlias("orgManage1.orgType", "orgType1");

		criteria.add(Restrictions.eq("branch1.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("hier1.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage1.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType1.deleted", Byte.valueOf((byte) 0)));
//		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));


		return criteria;
	}

	public Criteria getPlaceCashRequestSwapBidAliasForDelete(Criteria criteria) {
		criteria.createAlias("requestStatus", "request");
		
		criteria.createAlias("branchManagementRequestedFrom", "branch");
		criteria.createAlias("branch.hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		criteria.add(Restrictions.eq("branch.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("hier.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType.deleted", Byte.valueOf((byte) 0)));
		
		criteria.createAlias("branchManagementRequestedTo", "branch1");
		criteria.createAlias("branch1.hierarchyControl", "hier1");
		criteria.createAlias("hier1.orgManagement", "orgManage1");
		criteria.createAlias("orgManage1.orgType", "orgType1");

		criteria.add(Restrictions.eq("branch1.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("hier1.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgManage1.deleted", Byte.valueOf((byte) 0)));
		criteria.add(Restrictions.eq("orgType1.deleted", Byte.valueOf((byte) 0)));
//		criteria.add(Restrictions.eq("deleted", Byte.valueOf((byte) 0)));


		return criteria;
	}

	public void getDashboardFinalBidAuditTrialAliasForDelete(Criteria criteria) {
		criteria.createAlias("requestStatus", "request");
	}

	public Criteria getBranchParameterLevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("branchParameter", "branchParam");
		criteria.createAlias("branchManagement", "branch");
		criteria.createAlias("branch.hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");

		return criteria;
	}

	public void getBranchGroupLevelAliasForDelete(Criteria criteria) {
		criteria.createAlias("branchManagement", "branch");
		criteria.createAlias("branch.hierarchyControl", "hier");
		criteria.createAlias("hier.orgManagement", "orgManage");
		criteria.createAlias("orgManage.orgType", "orgType");
		
		criteria.add(Restrictions.eq("branch.deleted", Byte.valueOf((byte) 0)));
	}
}