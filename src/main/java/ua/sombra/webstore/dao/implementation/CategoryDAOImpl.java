package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.sombra.webstore.dao.interfaces.CategoryDAO;
import ua.sombra.webstore.domain.Category;

@Repository
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addCategory(Category category) {
		sessionFactory.getCurrentSession().save(category);
	}

	@SuppressWarnings("unchecked")
	public List<Category> listCategory() {
		return (List<Category>) sessionFactory.getCurrentSession().createQuery("From Category").list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> listMainCategories() {
		return (List<Category>) sessionFactory.getCurrentSession().createQuery("From Category C where C.isSub = false")
				.list();
	}

	@SuppressWarnings("unchecked")
	public List<Category> listSubCategories(int mainCategoryId) {
		return (List<Category>) sessionFactory.getCurrentSession()
				.createQuery("From Category C where C.mainCategoryId = :mainCategoryId")
				.setParameter("mainCategoryId", mainCategoryId).list();
	}

	public void removeCategory(Integer id) {
		Category category = (Category) sessionFactory.getCurrentSession().load(Category.class, id);
		if (category != null) {
			sessionFactory.getCurrentSession().delete(category);
		}
	}

	public Category findById(int id) {
		return (Category) sessionFactory.getCurrentSession().createQuery("From Category c where c.id = :id")
				.setParameter("id", id).uniqueResult();
	}

}
