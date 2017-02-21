package ua.sombra.webstore.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ua.sombra.webstore.domain.Role;

public class RoleDAOImpl implements RoleDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Role getById(long id){
		return (Role) sessionFactory.getCurrentSession().createQuery("FROM Role r WHERE r.id = "+id).uniqueResult();
	}

}
