package ua.sombra.webstore.service.databaseService.interfaces;

import org.springframework.web.multipart.MultipartFile;

import ua.sombra.webstore.entity.Photo;

public interface PhotoService {
	
	public void addPhoto(Photo photo);

	public Photo findById(int id);
	
	public void removePhoto(int id);
	
	public void addNewPhoto(int productId, MultipartFile file);
}
