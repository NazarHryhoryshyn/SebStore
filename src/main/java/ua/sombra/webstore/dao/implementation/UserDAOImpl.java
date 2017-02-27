package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.sombra.webstore.dao.interfaces.UserDAO;
import ua.sombra.webstore.domain.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void addUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> listUsers() {
		return (List<User>) sessionFactory.getCurrentSession().createQuery("From User").list();
	}

	public void removeUser(Integer id) {
		User user = (User) sessionFactory.getCurrentSession().load(User.class, id);
		if (user != null) {
			sessionFactory.getCurrentSession().delete(user);
		}
	}

	@Override
	public User findByEmail(String email) {
		User u = (User) sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.email LIKE :email")
				.setString("email", email).uniqueResult();
		return u;
	}

	public User findById(Integer id) {
		return (User) sessionFactory.getCurrentSession().createQuery("From User u where u.id = :id")
				.setParameter("id", id).uniqueResult();
	}

}
