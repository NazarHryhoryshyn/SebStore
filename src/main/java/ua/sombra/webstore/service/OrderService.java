package ua.sombra.webstore.service;

import ua.sombra.webstore.domain.Order;
import ua.sombra.webstore.domain.User;

public interface OrderService {
	
	public void addOrder(Order order, User user);
	
	public void removeOrder(Integer id);
	
	public Order findById(Integer id);
}
