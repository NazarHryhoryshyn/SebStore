package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.sombra.webstore.dao.interfaces.FeatureDAO;
import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Feature;

@Repository
public class FeatureDAOImpl implements FeatureDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addFeature(Feature feature) {
		sessionFactory.getCurrentSession().save(feature);
	}

	public void removeFeature(int id) {
		Feature feature = (Feature) sessionFactory.getCurrentSession().load(Category.class, id);
		if (feature != null) {
			sessionFactory.getCurrentSession().delete(feature);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Feature> listFeatures() {
		return (List<Feature>) sessionFactory.getCurrentSession().createQuery("From Feature").list();
	}

	public Feature findById(int id) {
		return (Feature) sessionFactory.getCurrentSession().createQuery("From Feature f where f.id = :id")
				.setParameter("id", id).uniqueResult();
	}
}
