package ua.sombra.webstore.dao.interfaces;

import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.User;

public interface PhotoDAO {

	public Photo findById(int id);
	
	public void addPhoto(Photo photo);

	public void removePhoto(int id);
	
	public void addProduct(Photo photo, Product product);

	public void removeProduct(Photo photo, Product product);
}
