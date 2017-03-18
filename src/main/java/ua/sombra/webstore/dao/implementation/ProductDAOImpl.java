package ua.sombra.webstore.dao.implementation;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.entity.Product;

@Repository
@Transactional
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void addProduct(Product product) {
		sessionFactory.getCurrentSession().save(product);
	}

	@Override
	public void removeProduct(Integer id) {
		Query q = sessionFactory.getCurrentSession().createSQLQuery("delete from product where id = :id");
		q.setParameter("id", id);
		q.executeUpdate();
	}
	
	@Override
	public Product findById(Integer id) {
		return (Product) sessionFactory.getCurrentSession().createQuery("From Product p where p.id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@Override
	public Product findByName(String name){
		return (Product) sessionFactory.getCurrentSession().createQuery("From Product p where p.name = :name")
				.setParameter("name", name).uniqueResult();
	}

	@Override
	public void editProduct(Product newParamsProduct){
		Product p = findById(newParamsProduct.getId());
		p.setName(newParamsProduct.getName());
		p.setPrice(newParamsProduct.getPrice());
		p.setProducer(newParamsProduct.getProducer());
		p.setCountry(newParamsProduct.getCountry());
		p.setWeight(newParamsProduct.getWeight());
		p.setAmountOnWarehouse(newParamsProduct.getAmountOnWarehouse());
		sessionFactory.getCurrentSession().update(p);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Product> listProducts() {
		return (List<Product>) sessionFactory.getCurrentSession().createQuery("From Product").list();
	}
}






