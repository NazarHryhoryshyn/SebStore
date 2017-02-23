package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.domain.Feature;

public interface FeatureDAO {
	
	public void addFeature(Feature feature);
	
	public void removeFeature(int id);
	
	public List<Feature> listFeatures();
	
	public Feature findById(int id);
}
