package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.CategoryDAO;
import ua.sombra.webstore.dao.interfaces.FeatureDAO;
import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Feature;
import ua.sombra.webstore.domain.Product;

@Repository
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private FeatureDAO featureDao;
	
	public void addCategory(Category category) {
		sessionFactory.getCurrentSession().save(category);
	}

	@SuppressWarnings("unchecked")
	public List<Category> listCategory() {
		return (List<Category>) sessionFactory.getCurrentSession().createQuery("From Category").list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> listTopCategories() {
		return (List<Category>) sessionFactory.getCurrentSession().createQuery("From Category C where C.isSub = 0")
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> listSubCategories(int mainCategoryId) {
		return (List<Category>) sessionFactory.getCurrentSession()
				.createQuery("From Category C where C.mainCategoryId = :mainCategoryId")
				.setParameter("mainCategoryId", mainCategoryId).list();
	}

	public void removeCategory(Integer id) {
		Category category = (Category) sessionFactory.getCurrentSession().get(Category.class, id);
		if (category != null) {
			if(category.getProducts().size() == 0){
				for(Feature f : category.getFeatures()){
					featureDao.removeCategory(f, category);
				}
				category.getFeatures().removeAll(category.getFeatures());
				sessionFactory.getCurrentSession().save(category);
				sessionFactory.getCurrentSession().delete(category);
			}
		}
	}

	public Category findById(int id) {
		return (Category) sessionFactory.getCurrentSession().createQuery("From Category c where c.id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	public Category findByName(String name){
		return (Category) sessionFactory.getCurrentSession().createQuery("From Category c where c.name = :name")
				.setParameter("name", name).uniqueResult();
	}

	public void AddFeature(Category category, Feature feature){
		category.getFeatures().add( feature );
         sessionFactory.getCurrentSession().update( category );
	}
	
	public void RemoveFeature(Category category, Feature feature){
		Session session = sessionFactory.getCurrentSession();
		category.getFeatures().remove(feature);
         session.update( category );
	}
	
	public void RemoveAllFeatures(Category category){
		Session session = sessionFactory.getCurrentSession();
		category.getFeatures().removeAll( category.getFeatures() );
         session.update( category );
	}
	
	public void rename(Integer id, String newName){
		Session session = sessionFactory.getCurrentSession();
		Category cat = (Category)session.get(Category.class, id);
         cat.setName(newName);
         session.update(cat);
	}
	
	public void AddProduct(Category category, Product product){
		category.getProducts().add( product );
         sessionFactory.getCurrentSession().save( category );
	}
	
	public void RemoveProduct(Category category, Product product){
		category.getProducts().remove( product );
		sessionFactory.getCurrentSession().save( category );
	}
}
