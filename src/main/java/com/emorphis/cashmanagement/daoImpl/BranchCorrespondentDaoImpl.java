package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.BranchCorrespondentDao;
import com.emorphis.cashmanagement.model.BranchCorrespondentGroup;
import com.emorphis.cashmanagement.model.MySession;
import com.emorphis.cashmanagement.util.CommonFilters;

@Repository
public class BranchCorrespondentDaoImpl extends AbstractDao<String, BranchCorrespondentGroup> implements BranchCorrespondentDao {

	@Autowired
	MySession mySession;

	@Autowired
	CommonFilters commonFilters;
	
	@Override
	public void saveBranchCorrespondentGroup(BranchCorrespondentGroup branchCorrespondentGroup2) {
		persist(branchCorrespondentGroup2);
//		return true;
	}
	
	@Override
	public void deleteBranchCorrespondentGroup(List<String> deletedList,
			BranchCorrespondentGroup branchCorrespondentGroup) {
		/*Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.in("branchManagement.id", deletedVA));
		List<BranchGroup> branchGroups = criteria.list();
		for (BranchGroup branchGroup : branchGroups) {
			delete(branchGroup);
		}*/
		
		Session session = getSession();
		String join = "'" + StringUtils.join(deletedList, "','") + "'";
		String hql = "delete from BranchCorrespondentGroup where associateCorrespondentBranch.id in (" + join + ") and parentCorrespondentBranch.id='"
				+ branchCorrespondentGroup.getParentCorrespondentBranch().getId() + "'";
		System.out.println("HQL QUERY FOR DELETE BranchCorrespondentGroup IS : " + hql);
		Query query = session.createQuery(hql);
		/* System.out.println("Query to delete userRoles is : " + query); */
		System.out.println("Query HQL : " + query.executeUpdate());
	}
}
