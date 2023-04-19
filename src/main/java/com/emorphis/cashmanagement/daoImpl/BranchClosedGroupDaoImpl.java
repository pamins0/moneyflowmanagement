package com.emorphis.cashmanagement.daoImpl;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BranchClosedGroupDao;
import com.emorphis.cashmanagement.model.BranchClosedGroup;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;

@Repository
public class BranchClosedGroupDaoImpl extends AbstractDao<Integer, BranchClosedGroup> implements BranchClosedGroupDao {

	@Autowired
	MySession mySession;

	@Override
	public List<BranchClosedGroup> getBranchClosedBranchList(BranchManagement branchManagement) {
		Criteria crt = createEntityCriteria();

		crt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (!branchManagement.getId().equals("0")) {
			crt.add(Restrictions.eq("parentBranch.id", branchManagement.getId()));
		}
		crt.createAlias("closedGroupBranch", "closedBranch");
		crt.createAlias("closedBranch.hierarchyControl", "hierarchyControl");
		crt.add(Restrictions.eq("closedBranch.deleted", Byte.valueOf((byte) 0)));

		crt.addOrder(Order.desc("hierarchyControl.hierarchyType"));
		crt.addOrder(Order.asc("closedBranch.branchName"));

		return crt.list();
	}

	@Override
	public List<BranchGroup> getGroupBranchList(String groupId) {
		Criteria crt = createEntityCriteria();
		crt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		crt.createAlias("branchManagement", "branchManagement");
		crt.add(Restrictions.eq("branchManagement.deleted", Byte.valueOf((byte) 0)));
		crt.add(Restrictions.eq("groupId", groupId));
		return crt.list();
	}

	@Override
	public void saveCrossBranchClosedGroupByparentBranch(BranchClosedGroup branchClosedGroup,
			Set<BranchClosedGroup> branchClosedGroupsSet) {
		/*
		 * Iterator<BranchClosedGroup> closedGroupBranchSet =
		 * branchClosedGroupsSet.iterator(); List<Integer>
		 * closedGroupBranchIdList = new ArrayList<Integer>(); while
		 * (closedGroupBranchSet.hasNext()) { BranchClosedGroup
		 * branchClosedGroup2 = (BranchClosedGroup) closedGroupBranchSet.next();
		 * closedGroupBranchIdList.add(branchClosedGroup2.getClosedGroupBranch()
		 * .getId()); } System.out.
		 * println("closedGroupBranchIdList to delete the closedbranch of parent branch from db is : "
		 * + closedGroupBranchIdList.iterator().next().intValue()); Session
		 * session = getSession(); String hql =
		 * "delete from BranchClosedGroup where closedGroupBranch.id in (" +
		 * StringUtils.join(closedGroupBranchIdList, ',') +
		 * ") and parentBranch.id=" + branchManagement.getId(); Query query =
		 * session.createQuery(hql);
		 * System.out.println("Query to delete userRoles is : " + query);
		 * System.out.println("Query HQL : " + query.executeUpdate());
		 */

	}

	@Override
	public BranchClosedGroup findByUUID(String uuids) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		BranchClosedGroup branchClosedGroup = (BranchClosedGroup) criteria.uniqueResult();
		return branchClosedGroup;
	}
}
