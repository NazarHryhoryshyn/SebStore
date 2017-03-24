package ua.sombra.webstore.service.databaseService.impls;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;
import ua.sombra.webstore.service.paging.PageMaker;

import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.Constants;
import ua.sombra.webstore.dao.interfaces.RoleDAO;
import ua.sombra.webstore.dao.interfaces.UserDAO;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.Role;
import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.entity.wrappers.PageInfo;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger log = Logger.getLogger(UserServiceImpl.class);
	
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
		try{
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			Set<Role> roles = new HashSet<>();
			roles.add(roleDao.getByName(Constants.USER_ROLE_IN_DB));
			user.setRoles(roles);
			userDao.create(user);
			log.info(user + " successfully created");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void editUser(User newParamUser) {
		try{
			userDao.update(newParamUser);
			log.info("User with id=" + newParamUser.getId() + " successfully edited");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public User findByLogin(String login) {
		try{
			return userDao.findByLogin(login);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Transactional
	@Override
	public List<User> listUsers() {
		try{
			return userDao.listAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean currUserIsAdmin() {
		try{
			String login = securityService.findLoggedInLogin();
			User u = findByLogin(login);
			for (Role r : u.getRoles()) {
				if (r.getName().equals(Constants.ADMIN_ROLE_IN_DB)) {
					return true;
				}
			}
		return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean UserIsAdmin(String login) {
		try{
		User u = findByLogin(login);
		for (Role r : u.getRoles()) {
			if (r.getName().equals(Constants.ADMIN_ROLE_IN_DB)) {
				return true;
			}
		}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
		return false;		
	}

	@Override
	public boolean UserIsBlocked(String login) {
		try{
			User u = findByLogin(login);
			for (Role r : u.getRoles()) {
				if (r.getName().equals(Constants.USER_ROLE_IN_DB)) {
					return false;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return true;
		}
		return true;
	}

	@Transactional
	@Override
	public void ChangeIsAdmin(String login, Boolean status) {
		try{
			User u = findByLogin(login);
			Role admRole = roleDao.getByName(Constants.ADMIN_ROLE_IN_DB);
			if (status == true && !UserIsAdmin(login)) {
				userDao.awardAdminRights(u.getId(), admRole.getId());
			} else if (status == false && UserIsAdmin(login)) {
				userDao.takeOffAdminRights(u.getId(), admRole.getId());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Transactional
	@Override
	public void ChangeIsBlocked(String login, Boolean status) {
		try{
			User u = findByLogin(login);
			Role userRole = roleDao.getByName(Constants.USER_ROLE_IN_DB);
			if (status == true && !UserIsBlocked(login)) {
				userDao.takeOffUserRights(u.getId(), userRole.getId());
			} else if (status == false && UserIsBlocked(login)) {
				userDao.awardUserRights(u.getId(), userRole.getId());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Transactional
	@Override
	public boolean addProductToCurrentUserCart(int productId) {
		try{
			User u = findByLogin(securityService.findLoggedInLogin());
			
			for(Product prod : u.getProducts()){
				if(prod.getId() == productId){
					return false;
				}
			}
			
			Product p = productService.findById(productId);
			if(p.getAmountOnWarehouse() > 0){
				userDao.addReferenceToProduct(u.getId(), productId);
				log.info(p + " successfully aded to user cart");
				p.setAmountOnWarehouse(p.getAmountOnWarehouse()-1);
				productService.update(p);
				return true;
			}
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public void removeReferenceToProduct(int userId, int productId) {
		try{
			userDao.removeReferenceToProduct(userId, productId);
			log.info("Product id="+productId+" successfully removed from user id="+userId+" cart");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void changeFirstName(String firstName) {
		try{
			User u = findByLogin(securityService.findLoggedInLogin());
			u.setFirstname(firstName);
			editUser(u);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void changeLastName(String lastName) {
		try{
			User u = findByLogin(securityService.findLoggedInLogin());
			u.setLastname(lastName);
			editUser(u);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void changeEmail(String email) {
		try{
			User u = findByLogin(securityService.findLoggedInLogin());
			u.setEmail(email);
			editUser(u);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void changeTelephone(String telephone) {
		try{
			User u = findByLogin(securityService.findLoggedInLogin());
			u.setTelephone(telephone);
			editUser(u);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void changeSex(String sex) {
		try{
			User u = findByLogin(securityService.findLoggedInLogin());
			u.setSex(sex);
			editUser(u);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		try{
			User u = findByLogin(securityService.findLoggedInLogin());
			if(bCryptPasswordEncoder.matches(oldPassword, bCryptPasswordEncoder.encode(u.getPassword()))){
				u.setPassword(bCryptPasswordEncoder.encode(newPassword));
			}		
			editUser(u);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Transactional
	@Override
	public void removeProductFromCart(int prodId){
		try{
			User u = userService.findByLogin(securityService.findLoggedInLogin());
			Product p = productService.findById(prodId);
			userService.removeReferenceToProduct(u.getId(), prodId);
			p.setAmountOnWarehouse(p.getAmountOnWarehouse()+1);
			productService.update(p);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public PageInfo<User> getPageInfoUsers(int page){
		PageInfo<User> userInfo = new PageInfo<User>();
		try{
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
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return userInfo;
	}
}
