package ua.sombra.webstore.service.databaseService.impls;

import java.util.HashMap;
import java.util.List;
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
		productExtraFeaturesDAO.create(productExtraFeature);
	}

	@Override
	public void removeProductExtraFeature(int productExtraFeatureId){
		productExtraFeaturesDAO.delete(productExtraFeatureId);
	}

	@Override
	public ProductExtraFeature findById(Integer productEFId){
		return productExtraFeaturesDAO.findById(productEFId);
	}
	
	@Override
	public void setValuesExtraFeatures(Integer productId, Map<String, String> featureNameValue){
		productExtraFeaturesDAO.setValuesExtraFeatures(productId, featureNameValue);
	}
	
	@Override
	public void removeAllExtraFeaturesFromProduct(int productId){
		productExtraFeaturesDAO.removeAllExtraFeaturesFromProduct(productId);
	}

	@Override
	public Map<String, String> parseStringtoMapFeatures(List<String> notParsedFeatures) {
		Map<String, String> efNameValue = new HashMap<String, String>();
		if(notParsedFeatures.contains("no_elements")){
			notParsedFeatures.remove("no_elements");
		}
		for(String efName : notParsedFeatures){
			String[] nameValue = efName.split("__");
			if(nameValue[1] == "null_null"){
				nameValue[1] = "";
			}
			efNameValue.put(nameValue[0], nameValue[1]);
		}
		return efNameValue;
	}
}
