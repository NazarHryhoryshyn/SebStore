package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.AbstractDAO;
import ua.sombra.webstore.dao.interfaces.UserDAO;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.User;

@Repository
@Transactional
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void create(User user) {
		sessionFactory.getCurrentSession().save(user);
	}
	
	@Override
	public void update(User userNewParameters){
		User u = findById(userNewParameters.getId());
		u.setFirstname(userNewParameters.getFirstname());
		u.setLastname(userNewParameters.getLastname());
		u.setEmail(userNewParameters.getEmail());
		u.setTelephone(userNewParameters.getTelephone());
		u.setSex(userNewParameters.getSex());
		u.setPassword(userNewParameters.getPassword());
	}
	
	@Override
	public User findById(int id) {
		return (User) sessionFactory.getCurrentSession().createQuery("From User u where u.id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	@Override
	public User findByLogin(String login) {
		User u = (User) sessionFactory.getCurrentSession().createQuery("FROM User u WHERE u.login LIKE :login")
				.setString("login", login).uniqueResult();
		return u;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> listAll() {
		return (List<User>) sessionFactory.getCurrentSession().createQuery("From User").list();
	}

	@Override
	public void awardAdminRights(int userId, int roleAdminId){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("insert into user_roles values(:userId, :adminId)");
		q.setParameter("userId", userId);
		q.setParameter("adminId", roleAdminId);
		q.executeUpdate();
	}

	@Override
	public void takeOffAdminRights(int userId, int roleAdminId){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("delete from user_roles where user_id = :userId and role_id = :adminId");
		q.setParameter("userId", userId);
		q.setParameter("adminId", roleAdminId);
		q.executeUpdate();
	}
	
	@Override
	public void awardUserRights(int userId, int roleUserId){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("insert into user_roles values(:userId, :roleUserId)");
		q.setParameter("userId", userId);
		q.setParameter("roleUserId", roleUserId);
		q.executeUpdate();
	}

	@Override
	public void takeOffUserRights(int userId, int roleUserId){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("delete from user_roles where user_id = :userId and role_id = :roleUserId");
		q.setParameter("userId", userId);
		q.setParameter("roleUserId", roleUserId);
		q.executeUpdate();
	}
	
	@Override
	public void addReferenceToProduct(int userId, int productId){
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
	public void removeReferenceToProduct(int userId, int productId){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("delete from cart where user_id = :userId and product_id = :productId");
		query.setParameter("userId", userId);
		query.setParameter("productId", productId);
		query.executeUpdate();
	}

	@Override
	public void delete(int entId) {
		// TODO Auto-generated method stub
		
	}
}
