package ua.sombra.webstore.dao.implementation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.sombra.webstore.dao.interfaces.OrderDAO;
import ua.sombra.webstore.domain.Order;

@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public void addOrder(Order order) {
		sessionFactory.getCurrentSession().save(order);
	}

	public void removeOrder(int id) {
		Order order = (Order) sessionFactory.getCurrentSession().load(Order.class, id);
		if (order != null) {
			sessionFactory.getCurrentSession().delete(order);
		}
	}

	@Override
	public Order findById(int id) {
		return (Order) sessionFactory.getCurrentSession().createQuery("From Order o where o.id = :id")
				.setParameter("id", id).uniqueResult();
	}
}
