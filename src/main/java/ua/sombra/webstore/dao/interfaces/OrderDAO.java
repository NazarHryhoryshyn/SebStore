package ua.sombra.webstore.dao.interfaces;


import ua.sombra.webstore.domain.Order;

public interface OrderDAO {
	
	public void addOrder(Order order);
	
	public void removeOrder(int id);
	
	public Order findById(int id);
}
