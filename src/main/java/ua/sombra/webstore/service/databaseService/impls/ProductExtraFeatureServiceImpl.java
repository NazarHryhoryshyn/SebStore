package ua.sombra.webstore.service.databaseService.impls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.Constants;
import ua.sombra.webstore.dao.interfaces.ProductExtraFeaturesDAO;
import ua.sombra.webstore.entity.ProductExtraFeature;
import ua.sombra.webstore.service.databaseService.interfaces.ProductExtraFeatureService;

@Service
@Transactional
public class ProductExtraFeatureServiceImpl implements ProductExtraFeatureService {
	
	private static final Logger log = Logger.getLogger(ProductExtraFeatureServiceImpl.class);
	
	@Autowired
	ProductExtraFeaturesDAO productExtraFeaturesDAO;

	@Override
	public void create(ProductExtraFeature productExtraFeature){
		try{
			productExtraFeaturesDAO.create(productExtraFeature);
			log.info(productExtraFeature + " successfully created");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void delete(int productExtraFeatureId){
		try{
			productExtraFeaturesDAO.delete(productExtraFeatureId);
			log.info("Extra feature with id="+productExtraFeatureId+" successfully deleted");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public ProductExtraFeature findById(Integer productEFId){
		try{
			return productExtraFeaturesDAO.findById(productEFId);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public void setValuesExtraFeatures(Integer productId, Map<String, String> featureNameValue){
		try{
			productExtraFeaturesDAO.setValuesExtraFeatures(productId, featureNameValue);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public void removeAllExtraFeaturesFromProduct(int productId){
		try{
			productExtraFeaturesDAO.removeAllExtraFeaturesFromProduct(productId);
			log.info("Removed all extra feature from product with id="+productId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public Map<String, String> parseStringToMapFeatures(List<String> notParsedFeatures) {
		Map<String, String> efNameValue = new HashMap<String, String>();
		try{
			if(notParsedFeatures.contains(Constants.EMPTY_LIST)){
				notParsedFeatures.remove(Constants.EMPTY_LIST);
			}
			for(String efName : notParsedFeatures){
				String[] nameValue = efName.split(Constants.FEATURE_NAME_VALUE_SEPARATOR);
				if(nameValue[1] == Constants.VALUE_IS_EMPTY){
					nameValue[1] = "";
				}
				efNameValue.put(nameValue[0], nameValue[1]);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return efNameValue;
	}
}
