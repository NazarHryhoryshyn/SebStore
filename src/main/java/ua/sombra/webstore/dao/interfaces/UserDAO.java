package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.domain.User;

public interface UserDAO {
	
	public void addUser(User user);

	public List<User> listUsers();

	public void removeUser(Integer id);
	
	public User findByEmail(String email);
}
