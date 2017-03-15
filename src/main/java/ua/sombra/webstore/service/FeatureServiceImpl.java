package ua.sombra.webstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.FeatureDAO;
import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Feature;

@Service
public class FeatureServiceImpl implements FeatureService{

	@Autowired
	private FeatureDAO featureDao;
	
	public void addFeature(Feature feature){
		featureDao.addFeature(feature);
	}
	
	public void removeFeature(Integer id){
		featureDao.removeFeature(id);
	}
	
	public List<Feature> listFeatures(){
		return featureDao.listFeatures();
	}
	
	public Feature findById(Integer id){
		return featureDao.findById(id);
	}	

	public Feature findByName(String name){
		return featureDao.findByName(name);
	}
	
	public void AddCategory(Feature feature, Category category){
		featureDao.AddCategory(feature, category);
	}

	public void removeCategory(Feature feature, Category category){
		featureDao.removeCategory(feature, category);
	}
}
