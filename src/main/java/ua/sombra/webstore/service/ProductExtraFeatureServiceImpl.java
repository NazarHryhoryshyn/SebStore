package ua.sombra.webstore.service;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.ProductExtraFeaturesDAO;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;

@Service
public class ProductExtraFeatureServiceImpl implements ProductExtraFeatureService {
	
	@Autowired
	ProductExtraFeaturesDAO productExtraFeaturesDAO;

	@Override
	public void addProductExtraFeature(ProductExtraFeatures productExtraFeature){
		productExtraFeaturesDAO.addProductExtraFeature(productExtraFeature);
	}

	@Override
	public void removeProductExtraFeature(ProductExtraFeatures productExtraFeature){
		productExtraFeaturesDAO.removeProductExtraFeature(productExtraFeature);
	}

	@Override
	public void setValueProductExtraFeature(Integer productId, String valueName, String value){
		productExtraFeaturesDAO.setValueProductExtraFeature(productId, valueName, value);
	}

	@Override
	public ProductExtraFeatures findById(Integer productEFId){
		return productExtraFeaturesDAO.findById(productEFId);
	}

	@Override
	public void setExtraFeatures(Integer productId, Map<String, String> featureNameValue){
		productExtraFeaturesDAO.setExtraFeatures(productId, featureNameValue);
	}
}
