package ua.sombra.webstore.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.PhotoDAO;
import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.User;

@Service
public class PhotoServiceImpl implements PhotoService{
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private PhotoDAO photoDao;
	
	public void addPhoto(Photo photo) {
		photoDao.addPhoto(photo);
	}

	public void removePhoto(int id) {
		photoDao.removePhoto(id);
	}
	
	@Override
	public void addProduct(Photo photo, Product product){
		photoDao.addProduct(photo, product);
	}

	@Override
	public void removeProduct(Photo photo, Product product){
		photoDao.removeProduct(photo, product);
	}
}
