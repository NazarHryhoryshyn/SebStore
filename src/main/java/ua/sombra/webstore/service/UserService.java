package ua.sombra.webstore.service;

import java.util.List;
import java.util.Set;

import ua.sombra.webstore.domain.Order;
import ua.sombra.webstore.domain.User;

public interface UserService {
	
	public void addUser(User contact);

	public List<User> listUsers();

	public void removeUser(Integer id);
	
	public User findByEmail(String email);
	
	public void addProductToUserCart(Integer userId, Integer productId);
	
	public Set<Order> listUserOrders(Integer id);
}
