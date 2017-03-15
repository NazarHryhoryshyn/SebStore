package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.FeatureDAO;
import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Feature;

@Repository
@Transactional
public class FeatureDAOImpl implements FeatureDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addFeature(Feature feature) {
		sessionFactory.getCurrentSession().save(feature);
	}

	public void removeFeature(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Feature feat = (Feature)session.get(Feature.class, id);
        session.delete(feat);
	}

	@SuppressWarnings("unchecked")
	public List<Feature> listFeatures() {
		return (List<Feature>) sessionFactory.getCurrentSession().createQuery("From Feature").list();
	}

	public Feature findById(Integer id) {
		return (Feature) sessionFactory.getCurrentSession().createQuery("From Feature f where f.id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	public Feature findByName(String name){
		return (Feature) sessionFactory.getCurrentSession().createQuery("From Feature f where f.name = :name")
				.setParameter("name", name).uniqueResult();
	}
	
	public void AddCategory(Feature feature, Category category){
		feature.getCategories().add( category );
		sessionFactory.getCurrentSession().save( feature );
	}
	
	public void removeCategory(Feature feature, Category category){
		feature.getCategories().remove(category);
		sessionFactory.getCurrentSession().update(feature);
	}
}
