package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.List;
import java.util.Map;

import ua.sombra.webstore.entity.ProductExtraFeature;

public interface ProductExtraFeatureService {
	
	public void create(ProductExtraFeature productExtraFeature);
	
	public void delete(int productExtraFeatureId);
	
	public ProductExtraFeature findById(Integer productEFId);
	
	public void setValuesExtraFeatures(Integer productId, Map<String, String> featureNameValue);

	public void removeAllExtraFeaturesFromProduct(int productId);
	
	public Map<String, String> parseStringToMapFeatures(List<String> notParsedFeatures);
}
