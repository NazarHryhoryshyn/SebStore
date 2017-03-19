package ua.sombra.webstore.dao.interfaces;

import java.util.Map;

import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.ProductExtraFeature;

public interface ProductExtraFeaturesDAO {
	
	public void addProductExtraFeature(ProductExtraFeature productExtraFeature);
	
	public void removeProductExtraFeature(ProductExtraFeature productExtraFeatures);
	
	public ProductExtraFeature findById(Integer productEFId);
	
	public void setValueExtraFeature(Product product, String valueName, String newValue);
	
	public void setValuesExtraFeatures(Integer productId, Map<String, String> featureNameValue);
	
	public void removeAllExtraFeaturesFromProduct(int productId);
}
