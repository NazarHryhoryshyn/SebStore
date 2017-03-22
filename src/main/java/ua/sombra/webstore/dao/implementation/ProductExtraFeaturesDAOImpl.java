package ua.sombra.webstore.dao.implementation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.AbstractDAO;
import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.dao.interfaces.ProductExtraFeaturesDAO;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.ProductExtraFeature;

@Repository
@Transactional
public class ProductExtraFeaturesDAOImpl extends AbstractDAO<ProductExtraFeature> implements ProductExtraFeaturesDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ProductDAO productDao;
	
	@Override
	public void create(ProductExtraFeature productExtraFeature) {
		sessionFactory.getCurrentSession().save(productExtraFeature);
	}

	@Override
	public void delete(int productExtraFeatureId) {
		ProductExtraFeature pef = findById(productExtraFeatureId);
		sessionFactory.getCurrentSession().delete(pef);
	}
	
	@Override
	public ProductExtraFeature findById(int productEFId) {
		ProductExtraFeature productExtraFeature = (ProductExtraFeature)sessionFactory.getCurrentSession()
				.get(ProductExtraFeature.class, productEFId);
		return productExtraFeature;
	}

	@Override
	public void setValueExtraFeature(Product product, String valueName, String newValue) {
		for(ProductExtraFeature pef : product.getProductExtraFeatures()){
			if(pef.getName().equals(valueName)){
				pef.setValue(newValue);
				sessionFactory.getCurrentSession().update(pef);
				break;
			}
		}
	}

	@Override
	public void setValuesExtraFeatures(Integer productId, Map<String, String> featureNameValue){
		Product p = productDao.findById(productId);
		for(Entry<String, String> entry : featureNameValue.entrySet()){			
			setValueExtraFeature(p, entry.getKey(), entry.getValue());
		}
	}
	
	@Override
	public void removeAllExtraFeaturesFromProduct(int productId){
		Query q = sessionFactory.getCurrentSession().createSQLQuery("delete from product_extra_features where product_id = :productId");
		q.setParameter("productId", productId);
		q.executeUpdate();
	}

	@Override
	public void update(ProductExtraFeature ent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ProductExtraFeature> listAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
