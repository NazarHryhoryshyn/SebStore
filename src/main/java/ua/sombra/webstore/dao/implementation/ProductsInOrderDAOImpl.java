package ua.sombra.webstore.dao.implementation;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.sombra.webstore.dao.interfaces.ProductsInOrderDAO;
import ua.sombra.webstore.domain.ProductsInOrder;

@Repository
public class ProductsInOrderDAOImpl implements ProductsInOrderDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public void addProductsInOrder(ProductsInOrder productsInOrder) {
		sessionFactory.getCurrentSession().save(productsInOrder);
	}
}
