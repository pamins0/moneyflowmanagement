package com.emorphis.cashmanagement.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.AbstractDao;
import com.emorphis.cashmanagement.dao.PermissionDao;
import com.emorphis.cashmanagement.model.Permission;
import com.emorphis.cashmanagement.util.Utility;

@Repository
public class PermissionDaoImpl extends AbstractDao<String, Permission> implements PermissionDao {

	@Autowired
	Utility utility;

	public List<Permission> getPermissionList() {
		boolean flag = false;
		Criteria criteria = createEntityCriteria().addOrder(Order.desc("createdTime"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		flag = utility.isAllowed("can_view_everything");
	//	Integer ids[] = {10,11,12,13,14,16,17,52,35,36,37,38}; 
	//	String ids[] = {""};
		String ids[] = {"85ebb670-23b1-467c-aba0-d99dbzzzzz2b",
	    		"85ebb670-23b1-467c-aba0-d99dbododj2b",
	    		"85ebb670-23b1-467c-aba0-d99dbgfgfg09",
	    		"85ebb670-23b1-467c-aba0-d99dbzzoop2b",
	    		"85ebb670-23b1-gghc-aba0-d99dbzzzzz2b",
	    		"85ebb670-papa-467c-aba0-d99dbzzzzz2b",
	    		"85ebb670-23b1-467c-aba0-d99dbkdkdj2b",
	    		"85ebb670-23b1-467c-aba0-d99emoryesiu",
	    		"85eaisak-23b1-467c-aba0-d99dbzzzzz2b",
	    		"85ebb670-ishq-467c-aba0-d99dbzzzzz2b",
	    		"85ebb670-23b1-467c-aba0-d99dbzzzpopo",
	    		"hjhjhjjh-23b1-467c-aba0-d99dbzzzzz2b"};
		if (!flag) {
			criteria.add(Restrictions.not(Restrictions.in("id", ids)));
		}

		@SuppressWarnings("unchecked")
		List<Permission> permissionList = (List<Permission>) criteria.list();
		return permissionList;
	}

	public void savePermission(Permission permission) {
		try {

			System.out.println("Inside daoImpl of Permission........key is : " + permission.getKeyVal() + " title is : "
					+ permission.getTitle() + " Module is : " + permission.getModule());
			/*
			 * Session session = getSession(); String sql =
			 * "insert into Permission (module,title,key,createdTime,modifiedTime) values('"
			 * +permission.getModule()+"','"+permission.getTitle()+"','"+
			 * permission.
			 * getKey()+"','"+permission.getCreatedTime()+"','"+permission.
			 * getModifiedTime()+"')"; Query hql = session.createQuery(sql);
			 * System.out.println("HQL QUERY IS : "+hql);
			 * System.out.println("Query in permission persist is : " +
			 * hql.executeUpdate());
			 */
			// getSession().save(permission);
			persist(permission);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}

	public Permission findById(String id) {
		return getByKey(id);
	}

	public void deleteById(String id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Permission permission = (Permission) crit.uniqueResult();
		delete(permission);
	}
	
	@Override
	public Permission findByUUID(String uuids) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", uuids));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Permission permission = (Permission) criteria.uniqueResult();
		return permission;
	}
}
