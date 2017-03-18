package ua.sombra.webstore.dao.implementation;

import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.dao.interfaces.ProductExtraFeaturesDAO;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.ProductExtraFeature;

@Repository
@Transactional
public class ProductExtraFeaturesDAOImpl implements ProductExtraFeaturesDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ProductDAO productDao;
	
	@Override
	public void addProductExtraFeature(ProductExtraFeature productExtraFeature) {
		sessionFactory.getCurrentSession().save(productExtraFeature);
	}

	@Override
	public void removeProductExtraFeature(ProductExtraFeature productExtraFeature) {
		sessionFactory.getCurrentSession().delete(productExtraFeature);
	}
	
	@Override
	public ProductExtraFeature findById(Integer productEFId) {
		ProductExtraFeature productExtraFeature = (ProductExtraFeature)sessionFactory.getCurrentSession()
				.get(ProductExtraFeature.class, productEFId);
		return productExtraFeature;
	}

	@Override
	public void setValueExtraFeature(Product product, String valueName, String newValue) {
		for(ProductExtraFeature pef : product.getProductExtraFeatures()){
			if(pef.getValue().equals(valueName)){
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
}
