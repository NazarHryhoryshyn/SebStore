package ua.sombra.webstore.dao.interfaces;


import java.util.List;

import ua.sombra.webstore.entity.Orders;

public interface OrderDAO {

	public void create(Orders order);

	public Orders findById(int id);
	
	public void changeStatus(int orderId, String newStatus);
	
	public List<Orders> listAll();
	
}
