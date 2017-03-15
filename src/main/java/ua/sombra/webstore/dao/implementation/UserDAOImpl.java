package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.UserDAO;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.User;
import ua.sombra.webstore.service.ProductService;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ProductService productService;

	@Override
	public void addUser(User user) {
		sessionFactory.getCurrentSession().save(user);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> listUsers() {
		return (List<User>) sessionFactory.getCurrentSession().createQuery("From User").list();
	}

	@Override
	public void removeUser(Integer id) {
		User user = (User) sessionFactory.getCurrentSession().load(User.class, id);
		if (user != null) {
			sessionFactory.getCurrentSession().delete(user);
		}
	}

	@Override
	public User findByLogin(String login) {
		User u = (User) sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.login LIKE :login")
				.setString("login", login).uniqueResult();
		return u;
	}

	@Override
	public User findById(Integer id) {
		return (User) sessionFactory.getCurrentSession().createQuery("From User u where u.id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void editUser(User userNewParameters){
		User u = findById(userNewParameters.getId());
		u.setFirstname(userNewParameters.getFirstname());
		u.setLastname(userNewParameters.getLastname());
		u.setEmail(userNewParameters.getEmail());
		u.setTelephone(userNewParameters.getTelephone());
		u.setSex(userNewParameters.getSex());
		u.setPassword(userNewParameters.getPassword());
	}

	@Override
	public void addProduct(User u, Product p){
		Session session = sessionFactory.getCurrentSession();
		u.getProducts().add(p);
        p.getUsers().add(u);
        session.save(p);
        session.save(u);
	}

	@Override
	public void removeProduct(User u, Product p){
		Session session = sessionFactory.getCurrentSession();
		u.getProducts().remove(p);
        p.getUsers().remove(u);
        session.save(p);
        session.save(u);
	}
}
