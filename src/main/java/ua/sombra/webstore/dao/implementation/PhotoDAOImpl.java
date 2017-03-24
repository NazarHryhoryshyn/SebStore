package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.AbstractDAO;
import ua.sombra.webstore.dao.interfaces.PhotoDAO;
import ua.sombra.webstore.entity.Photo;

@Repository
@Transactional
public class PhotoDAOImpl extends AbstractDAO<Photo> implements PhotoDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void create(Photo photo) {
		sessionFactory.getCurrentSession().save(photo);
	}

	@Override
	public void delete(int id) {
		Photo p = findById(id);
		sessionFactory.getCurrentSession().delete(p);
	}
	
	@Override
	public Photo findById(int id) {
		return (Photo) sessionFactory.getCurrentSession().createQuery("from Photo p where p.id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public void update(Photo ent) {
		sessionFactory.getCurrentSession().update(ent);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Photo> listAll() {
		Session s = sessionFactory.getCurrentSession();
		return (List<Photo>) s.createQuery("From Photo").list();
	}	
}
