package ua.sombra.webstore.service;

import java.util.List;
import java.util.Set;

import ua.sombra.webstore.domain.Category;

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
}
