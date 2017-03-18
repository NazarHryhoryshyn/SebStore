package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.OrderDAO;
import ua.sombra.webstore.domain.Orders;

@Repository
@Transactional
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void addOrder(Orders order) {
		sessionFactory.getCurrentSession().save(order);
	}
	
	@Override
	public Orders findById(int id) {
		return (Orders) sessionFactory.getCurrentSession().createQuery("From Orders o where o.id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	@Override
	public void changeStatus(int orderId, String newStatus) {
		Query q = sessionFactory.getCurrentSession().createSQLQuery("update orders set status = :newstatus where id = :orderId");
		q.setParameter("orderId", orderId);
		q.executeUpdate();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Orders> listOrders() {
		return (List<Orders>) sessionFactory.getCurrentSession().createQuery("From Orders").list();
	}	
}
