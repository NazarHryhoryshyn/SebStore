package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.AbstractDAO;
import ua.sombra.webstore.dao.interfaces.FeatureDAO;
import ua.sombra.webstore.entity.Feature;

@Repository
@Transactional
public class FeatureDAOImpl extends AbstractDAO<Feature> implements FeatureDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void create(Feature feature) {
		sessionFactory.getCurrentSession().save(feature);
	}
	
	@Override
	public void delete(int id) {
	        Query q = sessionFactory.getCurrentSession().createSQLQuery("delete from feature where id = :id");
	        q.setParameter("id", id);
	        q.executeUpdate();
	}

	@Override
	public Feature findById(int id) {
		return (Feature) sessionFactory.getCurrentSession().createQuery("From Feature f where f.id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	@Override
	public Feature findByName(String name){
		return (Feature) sessionFactory.getCurrentSession().createQuery("From Feature f where f.name = :name")
				.setParameter("name", name).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Feature> listAll() {
		return (List<Feature>) sessionFactory.getCurrentSession().createQuery("From Feature").list();
	}

	@Override
	public void update(Feature ent) {
		sessionFactory.getCurrentSession().update(ent);
	}
}
