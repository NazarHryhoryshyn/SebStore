package ua.sombra.webstore.service.databaseService.impls;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.ProductExtraFeaturesDAO;
import ua.sombra.webstore.entity.ProductExtraFeature;
import ua.sombra.webstore.service.databaseService.interfaces.ProductExtraFeatureService;

@Service
public class ProductExtraFeatureServiceImpl implements ProductExtraFeatureService {
	
	@Autowired
	ProductExtraFeaturesDAO productExtraFeaturesDAO;

	@Override
	public void addProductExtraFeature(ProductExtraFeature productExtraFeature){
		productExtraFeaturesDAO.addProductExtraFeature(productExtraFeature);
	}

	@Override
	public void removeProductExtraFeature(ProductExtraFeature productExtraFeature){
		productExtraFeaturesDAO.removeProductExtraFeature(productExtraFeature);
	}

	@Override
	public ProductExtraFeature findById(Integer productEFId){
		return productExtraFeaturesDAO.findById(productEFId);
	}
	
	@Override
	public void setValuesExtraFeatures(Integer productId, Map<String, String> featureNameValue){
		productExtraFeaturesDAO.setValuesExtraFeatures(productId, featureNameValue);
	}
}
