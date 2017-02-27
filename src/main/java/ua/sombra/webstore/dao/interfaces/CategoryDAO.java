package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.domain.Category;

public interface CategoryDAO {

	public void addCategory(Category category);

	public List<Category> listCategory();
	
	public List<Category> listTopCategories();
	
	public List<Category> listSubCategories(int mainCategoryId);

	public void removeCategory(Integer id);
	
	public Category findById(int id);
}
