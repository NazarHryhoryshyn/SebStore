package ua.sombra.webstore.dao.implementation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.RoleDAO;
import ua.sombra.webstore.domain.Role;

@Repository
@Transactional
public class RoleDAOImpl implements RoleDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Role getById(long id) {
		return (Role) sessionFactory.getCurrentSession().createQuery("FROM Role r WHERE r.id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public Role getByName(String Name) {
		return (Role) sessionFactory.getCurrentSession().createQuery("FROM Role r WHERE r.name = :name")
				.setParameter("name", Name).uniqueResult();
	}

}
