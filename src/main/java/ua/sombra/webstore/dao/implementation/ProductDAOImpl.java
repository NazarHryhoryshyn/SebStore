package ua.sombra.webstore.dao.implementation;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;
import ua.sombra.webstore.service.ProductExtraFeatureService;

@Repository
@Transactional
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	ProductExtraFeatureService productExtraFeatureService;

	@Override
	public void addProduct(Product product) {
		sessionFactory.getCurrentSession().save(product);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> listProducts() {
		List<Product> list = (List<Product>) sessionFactory.getCurrentSession().createQuery("From Product").list();
		
		return list;
	}

	@Override
	public void removeProduct(Integer id) {
		Product product = (Product) sessionFactory.getCurrentSession().load(Product.class, id);
		if (product != null) {
			sessionFactory.getCurrentSession().delete(product);
		}
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
	public void editProduct(Integer productId, Product newParamsProduct){
		Product p = findById(productId);
		p.setName(newParamsProduct.getName());
		p.setPrice(newParamsProduct.getPrice());
		p.setProducer(newParamsProduct.getProducer());
		p.setCountry(newParamsProduct.getCountry());
		p.setWeight(newParamsProduct.getWeight());
		p.setAmountOnWarehouse(newParamsProduct.getAmountOnWarehouse());
		sessionFactory.getCurrentSession().update(p);
	}

	@Override
	public void removeCategory(Product p){
		p.setCategory(new Category());
		sessionFactory.getCurrentSession().update( p );
	}

	@Override
	public void addCategory(Product p, Category c){
		p.setCategory(c);
		sessionFactory.getCurrentSession().update( p );
	}
	
	@Override
	public void addNewExtraFeatures(Product p, Set<String> featureNames){
		for(String fName: featureNames){
			if(!p.hasFeature(fName)){
				ProductExtraFeatures newFeature = new ProductExtraFeatures();
				newFeature.setName(fName);
				newFeature.setValue("");
				newFeature.setProduct(p);
				productExtraFeatureService.addProductExtraFeature(newFeature);
			}
		}
	}
	
	@Override
	public void removeExtraFeatures(Product p, Set<String> featureNames){
		for(String fName: featureNames){
			if(p.hasFeature(fName)){
				removeExtraFeature(p, p.getExtraFeatureByName(fName));
			}
		}
	}
	
	@Override
	public void removeExtraFeature(Product p, ProductExtraFeatures extraFeature){
		p.getProductExtraFeatures().remove(extraFeature);
		sessionFactory.getCurrentSession().update( p );
	}
	
	@Override
	public void addPhoto(Product product, Photo photo){
		Session session = sessionFactory.getCurrentSession();
		product.getPhotos().add(photo);
		photo.setProduct(product);
        session.save(photo);
        session.save(product);
	}
	
	@Override
	public void removePhoto(Product product, Photo photo){
		Session session = sessionFactory.getCurrentSession();
		product.getPhotos().remove(photo);
        session.update(product);
		sessionFactory.getCurrentSession().delete(photo);
	}
}






