package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.entity.Feature;

public interface FeatureDAO {
	
	public void create(Feature feature);
	
	public void delete(int id);
	
	public Feature findById(int id);

	public Feature findByName(String name);

	public List<Feature> listAll();
}
