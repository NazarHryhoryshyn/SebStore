package ua.sombra.webstore.entity;

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
