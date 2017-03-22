package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.entity.Category;

public interface CategoryDAO{

	public void create(Category category);

	public void delete(int id);

	public void renameCategory (String oldName, String newName);
	
	public Category findById(int id);
	
	public Category findByName(String name);

	public List<Category> listAll();
	
	public List<Category> listTopCategories();
	
	public List<Category> listSubCategories(int mainCategoryId);

	public void AddReferenceToFeature(int categoryId, int featureId);
	
	public void RemoveReferenceToFeature(int categoryId, int featureId);
	
	public void RemoveAllReferencesToFeatures(int categoryId);

}
