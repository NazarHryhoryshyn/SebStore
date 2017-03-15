package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Feature;
import ua.sombra.webstore.domain.Product;

public interface CategoryDAO {

	public void addCategory(Category category);

	public List<Category> listCategory();
	
	public List<Category> listTopCategories();
	
	public List<Category> listSubCategories(int mainCategoryId);

	public void removeCategory(Integer id);

	public Category findById(int id);
	
	public Category findByName(String name);
	
	public void AddFeature(Category category, Feature feature);
	
	public void RemoveFeature(Category category, Feature feature);
	
	public void RemoveAllFeatures(Category category);

	public void rename (Integer id, String newName);

	public void AddProduct(Category category, Product product);
	
	public void RemoveProduct(Category category, Product product);
}
