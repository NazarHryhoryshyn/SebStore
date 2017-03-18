package ua.sombra.webstore.service.databaseService.impls;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.domain.User;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;

import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.RoleDAO;
import ua.sombra.webstore.dao.interfaces.UserDAO;
import ua.sombra.webstore.domain.Role;

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
	SecurityService securityService;

	@Transactional
	@Override
	public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<>();
		roles.add(roleDao.getById(1L));
		user.setRoles(roles);
		userDao.addUser(user);
	}

	@Override
	public void editUser(User newParamUser) {
		userDao.editUser(newParamUser);
	}

	@Override
	public User findByLogin(String login) {
		return userDao.findByLogin(login);
	}

	@Transactional
	@Override
	public List<User> listUsers() {

		return userDao.listUsers();
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

	@Override
	public void ChangeIsBlocked(String login, Boolean status) {
		User u = findByLogin(login);
		Role userRole = roleDao.getByName("ROLE_USER");
		if (status == true && !UserIsBlocked(login)) {
			userDao.awardUserRights(u.getId(), userRole.getId());
		} else if (status == false && UserIsBlocked(login)) {
			userDao.takeOffUserRights(u.getId(), userRole.getId());
		}
	}

	@Override
	public void addReferenceToProduct(int userId, int productId) {
		userDao.addReferenceToProduct(userId, productId);
	}

	@Override
	public void removeReferenceToProduct(int userId, int productId) {
		userDao.removeReferenceToProduct(userId, productId);
	}
}
