package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.entity.User;

public interface UserDAO {
	
	public void create(User user);

	public void update(User userNewParameters);

	public User findById(int id);

	public User findByLogin(String login);
	
	public List<User> listAll();

	public void awardAdminRights(int userId, int roleAdminId);

	public void takeOffAdminRights(int userId, int roleAdminId);
	
	public void awardUserRights(int userId, int roleUserId);

	public void takeOffUserRights(int userId, int roleUserId);
	
	public void addReferenceToProduct(int userId, int productId);

	public void removeReferenceToProduct(int userId, int productId);
}
