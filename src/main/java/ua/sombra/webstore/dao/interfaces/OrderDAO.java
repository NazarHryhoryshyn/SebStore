package ua.sombra.webstore.dao.interfaces;


import java.util.List;

import ua.sombra.webstore.domain.Orders;

public interface OrderDAO {
	
	public void updateOrder(Orders order);
	
	public void addOrder(Orders order);
	
	public List<Orders> listOrders();
	
	public void removeOrder(int id);
	
	public Orders findById(int id);
}
