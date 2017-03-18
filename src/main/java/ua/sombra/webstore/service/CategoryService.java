package ua.sombra.webstore.service;

import java.util.List;
import java.util.Set;

import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Feature;
import ua.sombra.webstore.domain.Product;

public interface CategoryService {

	public void addTopCategory(Category category);
	
	public void addSubCategory(Category category, Integer mainCategoryId);

	public List<Category> listCategory();
	
	public List<Category> listTopCategories();
	
	public List<Category> listSubCategories(int mainCategoryId);

	public void removeCategory(Integer id);
	
	public Category findById(Integer id);
	
	public void rename (Integer id, String newName);
	
	public void addFeatures(Integer id, Set<String> features);
	
	public Set<String> listFeatures(Integer id);

	public Set<String> listTreeFeaturesToTop(Integer categoryId);

	public List<String> TreeCategoriesToTop(Integer categoryId);
	
	public Category findByName(String name);
	
	public void AddFeature(Category category, Feature feature);
	
	public void RemoveFeature(Category category, Feature feature);
	
	public void RemoveAllFeatures(Category category);

	public void AddProduct(Category category, Product product);
	
	public void RemoveProduct(Category category, Product product);
	
	Set<Product> ProductsFromTreeCategory (Category category);
}
