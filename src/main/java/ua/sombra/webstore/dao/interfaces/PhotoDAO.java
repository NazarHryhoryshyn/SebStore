package ua.sombra.webstore.dao.interfaces;

import ua.sombra.webstore.entity.Photo;

public interface PhotoDAO {
	
	public void create(Photo photo);
	
	public void delete(int id);
	
	public Photo findById(int id);
}
