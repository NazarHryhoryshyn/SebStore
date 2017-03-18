package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.Map;

import ua.sombra.webstore.entity.ProductExtraFeature;

public interface ProductExtraFeatureService {
	
	public void addProductExtraFeature(ProductExtraFeature productExtraFeature);
	
	public void removeProductExtraFeature(ProductExtraFeature productExtraFeatures);
	
	public ProductExtraFeature findById(Integer productEFId);
	
	public void setValuesExtraFeatures(Integer productId, Map<String, String> featureNameValue);
}
