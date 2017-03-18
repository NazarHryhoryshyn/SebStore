package ua.sombra.webstore.dao.implementation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.PhotoDAO;
import ua.sombra.webstore.entity.Photo;

@Repository
@Transactional
public class PhotoDAOImpl implements PhotoDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void addPhoto(Photo photo) {
		sessionFactory.getCurrentSession().save(photo);
	}

	@Override
	public void removePhoto(int id) {
		Photo p = findById(id);
		sessionFactory.getCurrentSession().delete(p);
	}
	
	@Override
	public Photo findById(int id) {
		return (Photo) sessionFactory.getCurrentSession().createQuery("from Photo p where p.id = :id")
				.setParameter("id", id).uniqueResult();
	}	
}
