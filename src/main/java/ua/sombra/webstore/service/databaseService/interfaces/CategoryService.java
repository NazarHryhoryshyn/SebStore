package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.sombra.webstore.entity.Category;
import ua.sombra.webstore.entity.Product;

public interface CategoryService {

	public void addTopCategory(Category category);
	
	public void addSubCategory(Category category, Integer mainCategoryId);
	
	public void removeCategory(Integer id);
	
	public void renameCategory (String oldName, String newName);
	
	public Category findById(Integer id);
	
	public Category findByName(String name);
	
	public List<Category> listAllCategories();
	
	public List<Category> listTopCategories();
	
	public List<Category> listSubCategories(int mainCategoryId);

	public void AddReferenceToFeature(int categoryId, int featureId);
	
	public void RemoveReferenceToFeature(int categoryId, int featureId);
	
	public void RemoveAllReferencesToFeatures(int categoryId);

	public Set<String> featuresTreeToTop(Integer categoryId);

	public List<String> categoriesTreeToTop(Integer categoryId);
	
	public Set<Category> categoriesTreeFromCategory (Category category);

	public Map<Integer, List<Category>> mapCategorySubCats(List<Category> categs);
	
	public Set<Product> productsTreeFromCategory (Category category);
}
