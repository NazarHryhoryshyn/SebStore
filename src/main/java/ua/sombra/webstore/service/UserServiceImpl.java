package ua.sombra.webstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.UserDAO;
import ua.sombra.webstore.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserDAO userDAO;

	@Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }
 
    @Transactional
    public List<User> listUsers() {
 
        return userDAO.listUsers();
    }
 
    @Transactional
    public void removeUser(Integer id) {
        userDAO.removeUser(id);
    }
}
