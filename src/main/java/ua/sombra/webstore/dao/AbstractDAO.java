package ua.sombra.webstore.dao;

import java.util.List;

public abstract class AbstractDAO<Entity> {
	
	 public abstract void create(Entity ent);
	 
	 public abstract Entity findById(int entId);
	 
	 public abstract void update(Entity ent);
	 
	 public abstract void delete(int entId);
	 
	 public abstract List<Entity> listAll();
}
