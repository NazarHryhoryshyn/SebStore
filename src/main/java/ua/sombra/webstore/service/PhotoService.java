package ua.sombra.webstore.service;

import org.hibernate.Session;

import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.User;

public interface PhotoService {
	
	public void addPhoto(Photo photo);
	
	public void removePhoto(int id);
	
	public void addProduct(Photo photo, Product product);

	public void removeProduct(Photo photo, Product product);
}
