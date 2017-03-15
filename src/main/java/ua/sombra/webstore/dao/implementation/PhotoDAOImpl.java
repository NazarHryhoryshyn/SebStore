package ua.sombra.webstore.dao.implementation;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.PhotoDAO;
import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.User;

@Repository
@Transactional
public class PhotoDAOImpl implements PhotoDAO {

	@Autowired
	SessionFactory sessionFactory;

	public void addPhoto(Photo photo) {
		sessionFactory.getCurrentSession().save(photo);
	}

	public Photo findById(int id) {
		return (Photo) sessionFactory.getCurrentSession().createQuery("from Photo p where p.id = :id")
				.setParameter("id", id).uniqueResult();
	}

	public void removePhoto(int id) {
		Photo p = findById(id);
		sessionFactory.getCurrentSession().delete(p);
	}

	@Override
	public void addProduct(Photo photo, Product product) {
		Session session = sessionFactory.getCurrentSession();
		photo.setProduct(product);
		product.getPhotos().add(photo);
		session.save(photo);
		session.save(product);
	}

	@Override
	public void removeProduct(Photo photo, Product product) {
		Session session = sessionFactory.getCurrentSession();
		photo.setProduct(new Product());
		product.getPhotos().remove(photo);
		session.save(photo);
		session.save(product);
	}
}
