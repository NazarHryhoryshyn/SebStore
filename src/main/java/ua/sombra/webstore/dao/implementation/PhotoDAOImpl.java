package ua.sombra.webstore.dao.implementation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.sombra.webstore.dao.interfaces.PhotoDAO;
import ua.sombra.webstore.domain.Photo;

@Repository
public class PhotoDAOImpl implements PhotoDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public void addPhoto(Photo photo) {
		sessionFactory.getCurrentSession().save(photo);
	}

	public void removePhoto(int id) {
		Photo photo = (Photo) sessionFactory.getCurrentSession().load(Photo.class, id);
		if (photo != null) {
			sessionFactory.getCurrentSession().delete(photo);
		}
	}
}
