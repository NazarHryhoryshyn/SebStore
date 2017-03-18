package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.List;
import ua.sombra.webstore.domain.User;

public interface UserService {
	
	public void addUser(User user);

	public void editUser(User newParamUser);
	
	public User findByLogin(String login);

	public List<User> listUsers();    
    
    public boolean currUserIsAdmin();
    
    public boolean UserIsAdmin(String login);

    public boolean UserIsBlocked(String login);

    public void ChangeIsAdmin(String login, Boolean status);

    public void ChangeIsBlocked(String login, Boolean status);
    
	public void addReferenceToProduct(int userId, int productId);

	public void removeReferenceToProduct(int userId, int productId);
}
