package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Feature;

public interface FeatureDAO {
	
	public void addFeature(Feature feature);
	
	public void removeFeature(Integer id);
	
	public List<Feature> listFeatures();
	
	public Feature findById(Integer id);

	public Feature findByName(String name);

	public void AddCategory(Feature feature, Category category);
	
	public void removeCategory(Feature f, Category category);
}
