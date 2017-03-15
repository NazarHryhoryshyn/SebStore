package ua.sombra.webstore.service;

import java.util.List;
import java.util.Set;

import ua.sombra.webstore.domain.Orders;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.User;

public interface UserService {
	
	public void addUser(User user);

	public List<User> listUsers();

	public void removeUser(Integer id);
	
	public User findByLogin(String login);
	
	public void editUser(User newParamUser);
	
    public void ChangeIsAdmin(String login, Boolean status);
    
    public boolean currUserIsAdmin();
    
    public boolean UserIsAdmin(String login);

    public boolean UserIsBlocked(String login);
    
    public void ChangeIsBlocked(String login, Boolean status);
    
    public void addProduct(User u, Product p);

	public void removeProduct(User u, Product p);
}
