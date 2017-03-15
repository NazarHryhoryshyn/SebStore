package ua.sombra.webstore.service;

import ua.sombra.webstore.domain.Orders;
import ua.sombra.webstore.domain.User;

public interface OrderService {
	
	public void addOrder(Orders order, User user);
	
	public void removeOrder(Integer id);

	public Orders findById(Integer id);
}
