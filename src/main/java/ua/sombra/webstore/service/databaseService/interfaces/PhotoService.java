package ua.sombra.webstore.service.databaseService.interfaces;

import ua.sombra.webstore.domain.Photo;

public interface PhotoService {
	
	public void addPhoto(Photo photo);

	public Photo findById(int id);
	
	public void removePhoto(int id);
}
