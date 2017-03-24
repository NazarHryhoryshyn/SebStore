package ua.sombra.webstore.service.databaseService.impls;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.FeatureDAO;
import ua.sombra.webstore.entity.Feature;
import ua.sombra.webstore.service.databaseService.interfaces.FeatureService;

@Service
@Transactional
public class FeatureServiceImpl implements FeatureService{

	private static final Logger log = Logger.getLogger(FeatureServiceImpl.class);
	
	@Autowired
	private FeatureDAO featureDao;
	
	public void create(Feature feature){
		try{
			featureDao.create(feature);
			log.info(feature + "successfully created");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void delete(Integer id){
		try{
			featureDao.delete(id);
			log.info("Feature with id="+id+" successfully deleted");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public Feature findById(Integer id){
		try{
			return featureDao.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}	

	public Feature findByName(String name){
		try{
			return featureDao.findByName(name);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public List<Feature> listAllFeatures(){
		try{
			return featureDao.listAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
