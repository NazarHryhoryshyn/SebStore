package ua.sombra.webstore.dao.implementation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.sombra.webstore.dao.interfaces.OrderDAO;
import ua.sombra.webstore.domain.Orders;

@Repository
public class OrderDAOImpl implements OrderDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public void addOrder(Orders order) {
		sessionFactory.getCurrentSession().save(order);
	}

	public void removeOrder(int id) {
		Orders order = (Orders) sessionFactory.getCurrentSession().load(Orders.class, id);
		if (order != null) {
			sessionFactory.getCurrentSession().delete(order);
		}
	}

	@Override
	public Orders findById(int id) {
		return (Orders) sessionFactory.getCurrentSession().createQuery("From Orders o where o.id = :id")
				.setParameter("id", id).uniqueResult();
	}
}
