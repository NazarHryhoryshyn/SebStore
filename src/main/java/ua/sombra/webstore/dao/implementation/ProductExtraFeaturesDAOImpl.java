package ua.sombra.webstore.dao.implementation;

import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.dao.interfaces.ProductExtraFeaturesDAO;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;

@Repository
@Transactional
public class ProductExtraFeaturesDAOImpl implements ProductExtraFeaturesDAO {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	ProductDAO productDao;
	
	@Override
	public void addProductExtraFeature(ProductExtraFeatures productExtraFeature) {
		sessionFactory.getCurrentSession().save(productExtraFeature);
	}

	@Override
	public void removeProductExtraFeature(ProductExtraFeatures productExtraFeature) {
		sessionFactory.getCurrentSession().delete(productExtraFeature);
	}

	@Override
	public void setValueProductExtraFeature(Integer productId, String valueName, String value) {
		Product p = productDao.findById(productId);
		for(ProductExtraFeatures pef : p.getProductExtraFeatures()){
			if(pef.getValue().equals(valueName)){
				pef.setValue(value);
				sessionFactory.getCurrentSession().save(pef);
				break;
			}
		}
	}

	@Override
	public void setExtraFeatures(Integer productId, Map<String, String> featureNameValue){
		Product p = productDao.findById(productId);
		for(Entry<String, String> entry : featureNameValue.entrySet()){
			for(ProductExtraFeatures pef : p.getProductExtraFeatures()){
				if(pef.getName().equals(entry.getKey())){
					pef.setValue(entry.getValue());
					sessionFactory.getCurrentSession().update(pef);
					break;
				}
			}
		}
	}
	
	@Override
	public ProductExtraFeatures findById(Integer productEFId) {
		ProductExtraFeatures productExtraFeature = (ProductExtraFeatures)sessionFactory.getCurrentSession()
				.get(ProductExtraFeatures.class, productEFId);
		return productExtraFeature;
	}
}
