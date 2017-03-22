package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.AbstractDAO;
import ua.sombra.webstore.dao.interfaces.CategoryDAO;
import ua.sombra.webstore.entity.Category;

@Repository
@Transactional
public class CategoryDAOImpl extends AbstractDAO<Category> implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void create(Category category) {
		sessionFactory.getCurrentSession().save(category);
	}

	@Override
	public void delete(int id) {
		Category category = findById(id);
		if (category != null) {
			sessionFactory.getCurrentSession().delete(category);
		}
	}

	@Override
	public void renameCategory(String oldName, String newName){
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createSQLQuery("update category set name = :newname where name = :oldname");
		q.setParameter("newname", newName);
		q.setParameter("oldname", oldName);
		q.executeUpdate();
	}
	
	@Override
	public Category findById(int id) {
		return (Category) sessionFactory.getCurrentSession().createQuery("From Category c where c.id = :id")
				.setParameter("id", id).uniqueResult();
	}
	
	@Override
	public Category findByName(String name){
		return (Category) sessionFactory.getCurrentSession().createQuery("From Category c where c.name = :name")
				.setParameter("name", name).uniqueResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Category> listAll() {
		return (List<Category>) sessionFactory.getCurrentSession().createQuery("From Category").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> listTopCategories() {
		return (List<Category>) sessionFactory.getCurrentSession().createQuery("From Category C where C.isSub = 0")
				.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> listSubCategories(int mainCategoryId) {
		return (List<Category>) sessionFactory.getCurrentSession()
				.createQuery("From Category C where C.mainCategoryId = :mainCategoryId")
				.setParameter("mainCategoryId", mainCategoryId).list();
	}

	@Override
	public void AddReferenceToFeature(int categoryId, int featureId){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("insert into additional_features values(:categoryId, :featureId)");
		q.setParameter("categoryId", categoryId);
		q.setParameter("featureId", featureId);
		q.executeUpdate();
	}
	
	@Override
	public void RemoveReferenceToFeature(int categoryId, int featureId){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("delete from additional_features where category_id = :categoryId and feature_id = :featureId");
		q.setParameter("categoryId", categoryId);
		q.setParameter("featureId", featureId);
		q.executeUpdate();
	}
	
	@Override
	public void RemoveAllReferencesToFeatures(int categoryId){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("delete from additional_features where category_id = :categoryId");
		q.setParameter("categoryId", categoryId);
		q.executeUpdate();
	}

	@Override
	public void update(Category ent) {
		// TODO Auto-generated method stub
	}
}
