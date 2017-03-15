package ua.sombra.webstore.service;

import java.util.Map;

import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;

public interface ProductExtraFeatureService {
	
	public void addProductExtraFeature(ProductExtraFeatures productExtraFeature);
	
	public void removeProductExtraFeature(ProductExtraFeatures productExtraFeature);
	
	public void setValueProductExtraFeature(Integer productId, String valueName, String value);
	
	public ProductExtraFeatures findById(Integer productEFId);

	public void setExtraFeatures(Integer productId, Map<String, String> featureNameValue);
}
