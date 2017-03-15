package ua.sombra.webstore.dao.interfaces;

import java.util.Map;

import ua.sombra.webstore.domain.ProductExtraFeatures;

public interface ProductExtraFeaturesDAO {
	
	public void addProductExtraFeature(ProductExtraFeatures productExtraFeature);
	
	public void removeProductExtraFeature(ProductExtraFeatures productExtraFeatures);
	
	public void setValueProductExtraFeature(Integer productId, String valueName, String value);
	
	public ProductExtraFeatures findById(Integer productEFId);

	public void setExtraFeatures(Integer productId, Map<String, String> featureNameValue);
}
