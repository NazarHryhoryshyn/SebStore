package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.List;

import ua.sombra.webstore.entity.Feature;

public interface FeatureService {
	
	public void create(Feature feature);
	
	public void delete(Integer id);

	public Feature findById(Integer id);

	public Feature findByName(String name);
	
	public List<Feature> listAllFeatures();
	
}
