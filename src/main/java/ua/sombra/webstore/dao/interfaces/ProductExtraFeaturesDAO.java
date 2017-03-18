package ua.sombra.webstore.dao.interfaces;

import java.util.Map;

import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;

public interface ProductExtraFeaturesDAO {
	
	public void addProductExtraFeature(ProductExtraFeatures productExtraFeature);
	
	public void removeProductExtraFeature(ProductExtraFeatures productExtraFeatures);
	
	public ProductExtraFeatures findById(Integer productEFId);
	
	public void setValueExtraFeature(Product product, String valueName, String newValue);
	
	public void setValuesExtraFeatures(Integer productId, Map<String, String> featureNameValue);
}
