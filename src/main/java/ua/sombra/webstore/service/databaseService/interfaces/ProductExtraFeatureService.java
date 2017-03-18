package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.Map;

import ua.sombra.webstore.domain.ProductExtraFeatures;

public interface ProductExtraFeatureService {
	
	public void addProductExtraFeature(ProductExtraFeatures productExtraFeature);
	
	public void removeProductExtraFeature(ProductExtraFeatures productExtraFeatures);
	
	public ProductExtraFeatures findById(Integer productEFId);
	
	public void setValuesExtraFeatures(Integer productId, Map<String, String> featureNameValue);
}
