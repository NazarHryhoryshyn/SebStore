package ua.sombra.webstore.service.databaseService.impls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.FeatureDAO;
import ua.sombra.webstore.entity.Feature;
import ua.sombra.webstore.service.databaseService.interfaces.FeatureService;

@Service
@Transactional
public class FeatureServiceImpl implements FeatureService{

	@Autowired
	private FeatureDAO featureDao;
	
	public void addFeature(Feature feature){
		featureDao.create(feature);
	}
	
	public void removeFeature(Integer id){
		featureDao.delete(id);
	}
	
	public Feature findById(Integer id){
		return featureDao.findById(id);
	}	

	public Feature findByName(String name){
		return featureDao.findByName(name);
	}
	
	public List<Feature> listAllFeatures(){
		return featureDao.listAll();
	}
}
