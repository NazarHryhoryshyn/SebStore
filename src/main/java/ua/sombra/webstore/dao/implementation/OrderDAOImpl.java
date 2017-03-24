package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.AbstractDAO;
import ua.sombra.webstore.dao.interfaces.OrderDAO;
import ua.sombra.webstore.entity.Orders;

@Repository
@Transactional
public class OrderDAOImpl extends AbstractDAO<Orders> implements OrderDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void create(Orders order) {
		sessionFactory.getCurrentSession().save(order);
	}
	
	@Override
	public Orders findById(int id) {
		return (Orders) sessionFactory.getCurrentSession().createQuery("From Orders o where o.id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	@Override
	public void changeStatus(int orderId, String newStatus) {
		Query q = sessionFactory.getCurrentSession().createSQLQuery("update orders set status = :newStatus where id = :orderId");
		q.setParameter("newStatus", newStatus);
		q.setParameter("orderId", orderId);
		q.executeUpdate();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Orders> listAll() {
		return (List<Orders>) sessionFactory.getCurrentSession().createQuery("From Orders").list();
	}

	@Override
	public void update(Orders ent) {
		sessionFactory.getCurrentSession().update(ent);
	}

	@Override
	public void delete(int entId) {
		Orders o = (Orders)sessionFactory.getCurrentSession().load(Orders.class, entId);
		sessionFactory.getCurrentSession().delete(o);
	}	
}
