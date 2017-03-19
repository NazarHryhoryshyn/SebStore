package ua.sombra.webstore.entity.wrappers;

import ua.sombra.webstore.entity.Orders;
import ua.sombra.webstore.entity.User;

public class OrderWrapper {
	Orders order;
	User user;
	
	public OrderWrapper(){
	}
	
	public void setOrder(Orders o){
		order = o;
	}
	
	public Orders getOrder(){
		return order;
	}
	
	public void setUser(User u){
		user = u;
	}
	
	public User getUser(){
		return user;
	}
}
