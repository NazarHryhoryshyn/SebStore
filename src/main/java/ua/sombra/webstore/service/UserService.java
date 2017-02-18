package ua.sombra.webstore.service;

import java.util.List;

import ua.sombra.webstore.domain.User;

public interface UserService {
	
	public void addUser(User contact);

	public List<User> listUsers();

	public void removeUser(Integer id);
}
