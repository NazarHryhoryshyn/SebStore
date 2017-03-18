package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.List;

import ua.sombra.webstore.domain.Orders;

public interface OrderService {

	public void addOrder(Orders order);

	public Orders findById(Integer id);
	
	public void changeStatus(int orderId, String newStatus);

	public List<Orders> listOrders();
}
