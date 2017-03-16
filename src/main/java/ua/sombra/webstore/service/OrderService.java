package ua.sombra.webstore.service;

import java.util.List;

import ua.sombra.webstore.domain.Orders;
import ua.sombra.webstore.domain.User;

public interface OrderService {
	
	public void updateOrder(Orders order);
	
	public void addOrder(Orders order);
	
	public void addOrder(Orders order, User user);
	
	public List<Orders> listOrders();
	
	public void removeOrder(Integer id);

	public Orders findById(Integer id);
}
