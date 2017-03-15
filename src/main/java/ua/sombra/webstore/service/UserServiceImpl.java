package ua.sombra.webstore.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.domain.User;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.dao.interfaces.RoleDAO;
import ua.sombra.webstore.dao.interfaces.UserDAO;
import ua.sombra.webstore.domain.Orders;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.Role;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserDAO userDao;
	
	@Autowired
	private RoleDAO roleDao;

	@Autowired
	private ProductDAO productDao;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SecurityService securityService;
    
	@Transactional
    public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById(1L));
		user.setRoles(roles);
        userDao.addUser(user);
    }
 
    @Transactional
    public List<User> listUsers() {
 
        return userDao.listUsers();
    }
 
    @Transactional
    public void removeUser(Integer id) {
        userDao.removeUser(id);
    }

	@Override
	public User findByLogin(String login) {
		return userDao.findByLogin(login);
	}
	
	public void editUser(User newParamUser){
		userDao.editUser(newParamUser);
	}
	
	public boolean currUserIsAdmin(){
		String login = securityService.findLoggedInLogin();
		User u = findByLogin(login);
		for (Role r : u.getRoles()) {
			if (r.getName().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}
	
    public boolean UserIsAdmin(String login){
		User u = findByLogin(login);
		for (Role r : u.getRoles()) {
			if (r.getName().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
    }
    
    public boolean UserIsBlocked(String login){
		User u = findByLogin(login);
		for (Role r : u.getRoles()) {
			if (r.getName().equals("ROLE_BLOCKED")) {
				return true;
			}
		}
		return false;
    }
	
	 public void ChangeIsAdmin(String login, Boolean status){
	    	User u = findByLogin(login);
	    	if(status == true){
	    		Set<Role> roles = u.getRoles();
	    		Role admRole = roleDao.getByName("ROLE_ADMIN");
	    		roles.add(admRole);
	    		u.setRoles(roles);
	    		Set<User> users = admRole.getUsers();
	    		users.add(u);
	    		admRole.setUsers(users);
	    	}
	    	else if(status == false){
	    		Set<Role> roles = u.getRoles();
	    		Role admRole = roleDao.getByName("ROLE_ADMIN");
	    		roles.remove(admRole);
	    		u.setRoles(roles);
	    		Set<User> users = admRole.getUsers();
	    		users.remove(u);
	    		admRole.setUsers(users);
	    	}
	    }
	 
	 public void ChangeIsBlocked(String login, Boolean status){
	    	User u = findByLogin(login);
	    	if(status == true){
	    		Set<Role> roles = u.getRoles();
	    		Role blockRole = roleDao.getByName("ROLE_BLOCKED");
	    		Role userRole = roleDao.getByName("ROLE_USER");
	    		roles.add(blockRole);
	    		roles.remove(userRole);
	    		u.setRoles(roles);
	    		Set<User> users = blockRole.getUsers();
	    		users.add(u);
	    		blockRole.setUsers(users);
	    		Set<User> users2 = userRole.getUsers();
	    		users2.remove(u);
	    		userRole.setUsers(users2);
	    	}
	    	else if(status == false){
	    		Set<Role> roles = u.getRoles();
	    		Role blockRole = roleDao.getByName("ROLE_BLOCKED");
	    		Role userRole = roleDao.getByName("ROLE_USER");
	    		roles.remove(blockRole);
	    		roles.add(userRole);
	    		u.setRoles(roles);
	    		Set<User> users = blockRole.getUsers();
	    		users.remove(u);
	    		blockRole.setUsers(users);
	    		Set<User> users2 = userRole.getUsers();
	    		users2.add(u);
	    		userRole.setUsers(users2);
	    	}
	    }
	 
	 public void addProduct(User u, Product p){
		 userDao.addProduct(u, p);
	 }

		public void removeProduct(User u, Product p){
			 userDao.removeProduct(u, p);
		}
}
