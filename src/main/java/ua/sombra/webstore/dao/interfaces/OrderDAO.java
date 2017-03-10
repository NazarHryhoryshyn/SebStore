package ua.sombra.webstore.dao.interfaces;


import ua.sombra.webstore.domain.Orders;

public interface OrderDAO {
	
	public void addOrder(Orders order);
	
	public void removeOrder(int id);
	
	public Orders findById(int id);
}
