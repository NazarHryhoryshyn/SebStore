package ua.sombra.webstore.dao.interfaces;

import ua.sombra.webstore.domain.Photo;

public interface PhotoDAO {
	
	public void addPhoto(Photo photo);
	
	public void removePhoto(int id);
	
	public Photo findById(int id);
}
