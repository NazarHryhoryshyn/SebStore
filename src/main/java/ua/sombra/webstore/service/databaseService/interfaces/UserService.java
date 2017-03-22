package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.List;

import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.entity.wrappers.PageInfo;

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
    
	public boolean addProductToCurrentUserCart(int productId);

	public void removeReferenceToProduct(int userId, int productId);

	public void changeFirstName(String firstName);
	
	public void changeLastName(String lastName);

	public void changeEmail(String email);
	
	public void changeTelephone(String telephone);

	public void changeSex(String sex);

	public void changePassword(String oldPassword, String newPassword);
	
	public void removeProductFromCart(int prodId);
	
	public PageInfo<User> getPageInfoUsers(int page);
}
