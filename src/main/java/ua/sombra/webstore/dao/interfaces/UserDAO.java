package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.User;

public interface UserDAO {
	
	public void addUser(User user);

	public List<User> listUsers();

	public void removeUser(Integer id);
	
	public void editUser(User userNewParameters);
	
	public User findByLogin(String login);
	
	public User findById(Integer id);
	
	public void addProduct(int userId, int productId);

	public void removeProduct(int userId, int productId);
}
