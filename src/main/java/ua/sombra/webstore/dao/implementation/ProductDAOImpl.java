package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.domain.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public void addProduct(Product product) {
		sessionFactory.getCurrentSession().save(product);
	}

	@SuppressWarnings("unchecked")
	public List<Product> listProduct() {
		return (List<Product>) sessionFactory.getCurrentSession().createQuery("From Product").list();
	}

	public void removeProduct(Integer id) {
		Product product = (Product) sessionFactory.getCurrentSession().load(Product.class, id);
		if (product != null) {
			sessionFactory.getCurrentSession().delete(product);
		}
	}

	public Product findById(int id) {
		return (Product) sessionFactory.getCurrentSession().createQuery("From Product p where p.id = :id")
				.setParameter("id", id).uniqueResult();
	}
}
