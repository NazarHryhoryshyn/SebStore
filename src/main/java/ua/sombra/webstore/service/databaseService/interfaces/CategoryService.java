package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.sombra.webstore.entity.Category;
import ua.sombra.webstore.entity.Product;

public interface CategoryService {

	public void addTopCategory(Category category);
	
	public void addSubCategory(Category category, Integer mainCategoryId);
	
	public void addCategory(String name, Integer mainCategoryId, List<String> featureNames);
	
	public void delete(Integer id);
	
	public void editCategory(String newName, Integer categoryId, List<String> featureNames);
	
	public void tryDeleteCategory(int categoryId);
	
	public void renameCategory (String oldName, String newName);
	
	public Category findById(Integer id);
	
	public Category findByName(String name);
	
	public List<Category> listAllCategories();
	
	public List<Category> listTopCategories();	

	public Set<String> listTopCategoriesNames();
	
	public List<Category> listSubCategories(int mainCategoryId);

	public void AddReferenceToFeature(int categoryId, int featureId);
	
	public void RemoveReferenceToFeature(int categoryId, int featureId);
	
	public void RemoveAllReferencesToFeatures(int categoryId);

	public Set<String> featuresTreeToTop(Integer categoryId);

	public List<String> categoriesTreeToTop(Integer categoryId);
	
	public Set<Category> categoriesTreeFromCategory (Category category);

	public Map<Integer, List<Category>> mapCategorySubCats(List<Category> categs);
	
	public Set<Product> productsTreeFromCategory (Category category);

	public List<String> getAllTreeWithThisCategory(String categoryName);
	
	public Set<Product> getAllProductsFromCategory(String categoryName);

	public Set<Product> filterProducts(Set<Product> products, String prodName, String prodProducer, int prodMinPrice, int prodMaxPrice);
}
