package com.emorphis.cashmanagement.daoImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BranchGroupDao;
import com.emorphis.cashmanagement.model.BranchGroup;
import com.emorphis.cashmanagement.model.BranchManagement;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.util.CommonFilters;

@Repository
public class BranchGroupDaoImpl extends AbstractDao<Integer, BranchGroup> implements BranchGroupDao {

	private static final Logger log = LoggerFactory.getLogger(BranchGroupDaoImpl.class);
	
	@Autowired
	MySession mySession;

	@Autowired
	CommonFilters commonFilters;

	@Override
	public List<BranchGroup> getGroupBranchList(BranchGroup branchGroup) {
		Criteria crt = createEntityCriteria();
		crt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		commonFilters.getBranchGroupLevelAliasForDelete(crt);
		if (branchGroup.getGroupId() != null) {
			crt.add(Restrictions.eq("groupId", branchGroup.getGroupId()));
		}
		crt.add(Restrictions.eq("orgManage.id", mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId()));
		return crt.list();
	}

	@Override
	public BranchGroup findByUUID(String uuids) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (BranchGroup) criteria.uniqueResult();
	}

	@Override
	public boolean saveBranchGroup(BranchGroup branchGroupObj) {
		persist(branchGroupObj);
		return true;
	}

	@Override
	public void deleteBranchGroup(List<String> deletedVA) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.in("branchManagement.id", deletedVA));
		List<BranchGroup> branchGroups = criteria.list();
		for (BranchGroup branchGroup : branchGroups) {
			delete(branchGroup);
		}
	}

	
	@Override
	public boolean deleteByGroupId(String groupId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("groupId", groupId));
		List<BranchGroup> branchGroups = criteria.list();
		for (BranchGroup branchGroup : branchGroups) {
			this.delete(branchGroup);
		}
		return true;
	}

	@Override
	public List<BranchManagement> getBranchManagementListByGroupId(String groupId) {
		Criteria crt = createEntityCriteria();
		crt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		commonFilters.getBranchGroupLevelAliasForDelete(crt);
		crt.add(Restrictions.eq("groupId", groupId));
		List<BranchManagement> branchManagementsList = (List<BranchManagement>) crt.list();
		return branchManagementsList;
	}

	@Override
	public Set<String> getCCGroupList() {
		Criteria crt = createEntityCriteria();
		crt.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		commonFilters.getBranchGroupLevelAliasForDelete(crt);
		crt.add(Restrictions.eq("hier.hierarchyType", 1));
		crt.add(Restrictions.eq("orgManage.id", mySession.getUser().getBranchManagement().getHierarchyControl().getOrgManagement().getId()));
		crt.setProjection(Projections.property("groupId"));  
		List<String> ccGroupList = crt.list();
		log.info("list size is : "+ccGroupList.size());
		ccGroupList.forEach(n-> log.info("list data : "+n)); 
		Set<String> ccGroupSet = new HashSet<>(ccGroupList);

		log.info("set size is : "+ccGroupSet.size());
		ccGroupSet.forEach(n-> log.info("set data : "+n));
		return ccGroupSet;
	}

}
