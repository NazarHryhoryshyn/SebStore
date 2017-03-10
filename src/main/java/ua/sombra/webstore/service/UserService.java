package ua.sombra.webstore.service;

import java.util.List;
import java.util.Set;

import ua.sombra.webstore.domain.Orders;
import ua.sombra.webstore.domain.User;

public interface UserService {
	
	public void addUser(User user);

	public List<User> listUsers();

	public void removeUser(Integer id);
	
	public User findByLogin(String login);
	
	public void addProductToUserCart(Integer userId, Integer productId);
	
	public Set<Orders> listUserOrders(Integer id);
	
	public void editUser(User newParamUser);
}
