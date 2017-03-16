package ua.sombra.webstore.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.OrderDAO;
import ua.sombra.webstore.domain.Orders;
import ua.sombra.webstore.domain.User;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderDAO orderDao;

	@Override
	public void updateOrder(Orders order){
		orderDao.updateOrder(order);
	}
	
	@Override
	public void addOrder(Orders order){
		orderDao.addOrder(order);
	}
	
	@Override
	public void addOrder(Orders order, User user) {
		Set<Orders> uOrders = user.getOrders();
		uOrders.add(order);
		user.setOrders(uOrders);
		order.setUser(user);
		orderDao.addOrder(order);
	}

	public List<Orders> listOrders(){
		return orderDao.listOrders();
	}
	
	@Override
	public void removeOrder(Integer id) {
		orderDao.removeOrder(id);
	}

	@Override
	public Orders findById(Integer id) {
		return orderDao.findById(id);
	}
}
