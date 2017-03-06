package ua.sombra.webstore.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.OrderDAO;
import ua.sombra.webstore.domain.Order;
import ua.sombra.webstore.domain.User;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDAO orderDao;

	@Override
	public void addOrder(Order order, User user) {
		Set<Order> uOrders = user.getOrders();
		uOrders.add(order);
		user.setOrders(uOrders);
		order.setUser(user);
		orderDao.addOrder(order);
	}

	@Override
	public void removeOrder(Integer id) {
		orderDao.removeOrder(id);
	}

	@Override
	public Order findById(Integer id) {
		return orderDao.findById(id);
	}
}
