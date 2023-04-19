package com.emorphis.cashmanagement.daoImpl;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emorphis.cashmanagement.dao.UserManagementDaoIndependent;
import com.emorphis.cashmanagement.model.User;

@Repository("userManagementDaoIndependent")
public class UserManagementDaoIndependentImpl implements UserManagementDaoIndependent {

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public int saveUser(User user) {
		System.out.println("User generaed id before  ....");
		Session session = getSession();
		// session.getTransaction().begin();
		user.setCreatedTime(new Date());
		user.setModifiedTime(new Date());
		System.out.println("User generaed id before : " + user.getId());
		Serializable result = session.save(user);
		System.out.println("Result value : "+result); 
		Integer r = (Integer) result;
		System.out.println("User generaed id : " + r.longValue() + " user id : " + user.getId());
		// session.getTransaction().commit();
		return 0;
	}

}
