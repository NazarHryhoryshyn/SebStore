package ua.sombra.webstore.dao.interfaces;

import ua.sombra.webstore.entity.Role;

public interface RoleDAO {
	
	Role getById(long id);
	
	Role getByName(String Name);
}
