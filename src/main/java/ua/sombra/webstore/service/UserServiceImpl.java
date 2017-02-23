package ua.sombra.webstore.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.domain.User;
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

	@Transactional
    public void addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getById(1L));
        System.out.println("user: " + user.toString() + "; roles: " + roles);
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
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}
}
