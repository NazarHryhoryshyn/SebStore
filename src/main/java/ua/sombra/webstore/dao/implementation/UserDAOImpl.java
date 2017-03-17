package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Query;
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
	public void addProduct(int userId, int productId){
		Session session = sessionFactory.getCurrentSession();
		User u = (User) session.load(User.class, userId);
		Product p = (Product) session.load(Product.class, productId);
		if(!u.getProducts().contains(p)){
			Query query = session.createSQLQuery("insert into cart values(:userId, :productId)");
			query.setParameter("userId", userId);
			query.setParameter("productId", productId);
			 
			query.executeUpdate();
		}
	}

	@Override
	public void removeProduct(int userId, int productId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("delete from cart where user_id = :userId and product_id = :productId");
		query.setParameter("userId", userId);
		query.setParameter("productId", productId);
		 
		query.executeUpdate();
	}
}
