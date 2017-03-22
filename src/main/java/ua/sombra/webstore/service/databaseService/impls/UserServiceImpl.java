package ua.sombra.webstore.service.databaseService.impls;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;
import ua.sombra.webstore.service.paging.PageMaker;

import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.RoleDAO;
import ua.sombra.webstore.dao.interfaces.UserDAO;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.Role;
import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.entity.wrappers.PageInfo;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;

	@Autowired
	private RoleDAO roleDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	@Override
	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<>();
		roles.add(roleDao.getByName("ROLE_USER"));
		user.setRoles(roles);
		userDao.create(user);
	}

	@Override
	public void editUser(User newParamUser) {
		userDao.update(newParamUser);
	}

	@Override
	public User findByLogin(String login) {
		return userDao.findByLogin(login);
	}

	@Transactional
	@Override
	public List<User> listUsers() {

		return userDao.listAll();
	}

	@Override
	public boolean currUserIsAdmin() {
		String login = securityService.findLoggedInLogin();
		User u = findByLogin(login);
		for (Role r : u.getRoles()) {
			if (r.getName().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean UserIsAdmin(String login) {
		User u = findByLogin(login);
		for (Role r : u.getRoles()) {
			if (r.getName().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean UserIsBlocked(String login) {
		User u = findByLogin(login);
		for (Role r : u.getRoles()) {
			if (r.getName().equals("ROLE_USER")) {
				return false;
			}
		}
		return true;
	}

	@Transactional
	@Override
	public void ChangeIsAdmin(String login, Boolean status) {
		User u = findByLogin(login);
		Role admRole = roleDao.getByName("ROLE_ADMIN");
		if (status == true && !UserIsAdmin(login)) {
			userDao.awardAdminRights(u.getId(), admRole.getId());
		} else if (status == false && UserIsAdmin(login)) {
			userDao.takeOffAdminRights(u.getId(), admRole.getId());
		}
	}

	@Transactional
	@Override
	public void ChangeIsBlocked(String login, Boolean status) {
		User u = findByLogin(login);
		Role userRole = roleDao.getByName("ROLE_USER");
		if (status == true && !UserIsBlocked(login)) {
			userDao.takeOffUserRights(u.getId(), userRole.getId());
		} else if (status == false && UserIsBlocked(login)) {
			userDao.awardUserRights(u.getId(), userRole.getId());
		}
	}

	@Transactional
	@Override
	public boolean addProductToCurrentUserCart(int productId) {
		User u = findByLogin(securityService.findLoggedInLogin());
		
		for(Product prod : u.getProducts()){
			if(prod.getId() == productId){
				return false;
			}
		}
		
		Product p = productService.findById(productId);
		if(p.getAmountOnWarehouse() > 0){
			userDao.addReferenceToProduct(u.getId(), productId);
			p.setAmountOnWarehouse(p.getAmountOnWarehouse()-1);
			productService.update(p);
			return true;
		}
		return false;
	}

	@Override
	public void removeReferenceToProduct(int userId, int productId) {
		userDao.removeReferenceToProduct(userId, productId);
	}

	@Override
	public void changeFirstName(String firstName) {
		User u = findByLogin(securityService.findLoggedInLogin());
		u.setFirstname(firstName);
		editUser(u);
	}

	@Override
	public void changeLastName(String lastName) {
		User u = findByLogin(securityService.findLoggedInLogin());
		u.setLastname(lastName);
		editUser(u);
	}

	@Override
	public void changeEmail(String email) {
		User u = findByLogin(securityService.findLoggedInLogin());
		u.setEmail(email);
		editUser(u);
	}

	@Override
	public void changeTelephone(String telephone) {
		User u = findByLogin(securityService.findLoggedInLogin());
		u.setTelephone(telephone);
		editUser(u);
	}

	@Override
	public void changeSex(String sex) {
		User u = findByLogin(securityService.findLoggedInLogin());
		u.setSex(sex);
		editUser(u);
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		User u = findByLogin(securityService.findLoggedInLogin());
		if(bCryptPasswordEncoder.matches(oldPassword, bCryptPasswordEncoder.encode(u.getPassword()))){
			u.setPassword(bCryptPasswordEncoder.encode(newPassword));
		}		
		editUser(u);
	}
	
	@Transactional
	@Override
	public void removeProductFromCart(int prodId){
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		Product p = productService.findById(prodId);
		userService.removeReferenceToProduct(u.getId(), prodId);
		p.setAmountOnWarehouse(p.getAmountOnWarehouse()+1);
		productService.update(p);
	}
	
	@Override
	public PageInfo<User> getPageInfoUsers(int page){
		PageInfo<User> userInfo = new PageInfo<User>();
		Set<User> allUsers = new TreeSet<User>();
		
		for(User u : userService.listUsers()){
			allUsers.add(u);
		}
		
		PageMaker<User> pgMaker = new PageMaker<User>();
		pgMaker.setObjects(allUsers);

		userInfo.setTotalPages(pgMaker.totalPages());
		userInfo.setPage(page);
		userInfo.setBlock(pgMaker.getBlock(page));
		userInfo.setObjects(pgMaker.getFromPage(page));
		userInfo.setAmountPagesInBlock(pgMaker.getAmountPagesInBlock());
		return userInfo;
	}
}
