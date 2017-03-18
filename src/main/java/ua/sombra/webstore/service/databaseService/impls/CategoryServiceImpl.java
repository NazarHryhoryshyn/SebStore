package ua.sombra.webstore.service.databaseService.impls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.CategoryDAO;
import ua.sombra.webstore.entity.Category;
import ua.sombra.webstore.entity.Feature;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.service.databaseService.interfaces.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryDAO categoryDao;
	
	@Override
	public void addTopCategory(Category category) {
		category.setIsSub(false);
		category.setMainCategoryId(0);
		categoryDao.addCategory(category);
	}

	@Override
	public void addSubCategory(Category category, Integer mainCategoryId) {
		category.setIsSub(true);
		category.setMainCategoryId(mainCategoryId);
		categoryDao.addCategory(category);
	}

	@Override
	public void removeCategory(Integer id) {
		categoryDao.removeCategory(id);
	}

	@Override
	public void renameCategory(String oldName, String newName) {
		categoryDao.renameCategory(oldName, newName);
	}
	
	@Override
	public Category findById(Integer id) {
		return categoryDao.findById(id);
	}
	
	@Override
	public Category findByName(String name){
		return categoryDao.findByName(name);
	}
	
	@Override
	public List<Category> listAllCategories() {
		return categoryDao.listAllCategories();
	}

	@Override
	public List<Category> listTopCategories() {
		return categoryDao.listTopCategories();
	}

	@Override
	public List<Category> listSubCategories(int mainCategoryId) {
		return categoryDao.listSubCategories(mainCategoryId);
	}

	@Override
	public void AddReferenceToFeature(int categoryId, int featureId){
		categoryDao.AddReferenceToFeature(categoryId, featureId);
	}
	
	@Override
	public void RemoveReferenceToFeature(int categoryId, int featureId){
		categoryDao.RemoveReferenceToFeature(categoryId, featureId);
	}
	
	@Override
	public void RemoveAllReferencesToFeatures(int categoryId){
		categoryDao.RemoveAllReferencesToFeatures(categoryId);
	}
	
	@Override
	public Set<String> featuresTreeToTop(Integer categoryId){
		Category c = categoryDao.findById(categoryId);
		Set<String> feturesName = new HashSet<String>();
		
		if(c.getIsSub()){
			Set<String> topCatFeats = featuresTreeToTop(c.getMainCategoryId());
			for(String feats : topCatFeats){
				feturesName.add(feats);
			}
		}
		else{
			for(Feature f : c.getFeatures()){
				feturesName.add(f.getName());
			}
		}
		return feturesName;
	}
	
	@Override
	public List<String> categoriesTreeToTop(Integer categoryId){
		Category c = categoryDao.findById(categoryId);
		List<String> categoryNames = new ArrayList<String>();
		
		if(c.getIsSub()){
			List<String> topCategory = categoriesTreeToTop(c.getMainCategoryId());
			for(String cat : topCategory){
				categoryNames.add(cat);
			}
		}
		categoryNames.add(c.getName());
		return categoryNames;
	}

	@Override
	public Set<Category> categoriesTreeFromCategory (Category category){
		Set<Category> categoriesTree = new HashSet<Category>();
		
		if(categoryDao.listSubCategories(category.getId()).size() > 0){
			List<Category> catList =  categoryDao.listSubCategories(category.getId());
			
			for (Category cat :  catList)
			{
				for(Category c : categoriesTreeFromCategory(cat)){
					categoriesTree.add(c);
				}
			}
		}
		categoriesTree.add(category);
		return categoriesTree;
	}
	
	@Override
	public Map<Integer, List<Category>> mapCategorySubCats(List<Category> categs){
		Map<Integer, List<Category>> mapCategs = new HashMap<Integer, List<Category>>();
		for(Category c : categs){
			if(listSubCategories(c.getId()).size() > 0){
				mapCategs.put(c.getId(), listSubCategories(c.getId()));
				List<Category> catList =  listSubCategories(c.getId());
				for (Map.Entry<Integer, List<Category>> entry :  mapCategorySubCats(catList).entrySet())
				{
					mapCategs.put(entry.getKey(), entry.getValue());
				}
			}
			else{
				mapCategs.put(c.getId(), new ArrayList<Category>());
			}
		}	
		return mapCategs;
	}
	
	@Override
	public Set<Product> productsTreeFromCategory (Category category){
		Set<Product> products = new HashSet<Product>();
		
		if(categoryDao.listSubCategories(category.getId()).size() > 0){
			List<Category> catList =  categoryDao.listSubCategories(category.getId());
			
			for (Category cat :  catList)
			{
				for(Product p : productsTreeFromCategory(cat)){
					products.add(p);
				}
			}
		}
		for(Product p : category.getProducts()){
			products.add(p);
		}
		return products;
	}
}
