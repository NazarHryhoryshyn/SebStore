package ua.sombra.webstore.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.CategoryDAO;
import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Feature;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryDAO categoryDao;
	
	@Override
	public void addTopCategory(Category category) {
		categoryDao.addCategory(category);
	}

	@Override
	public void addSubCategory(Category category, Integer mainCategoryId) {
		category.setIsSub(true);
		category.setMainCategoryId(mainCategoryId);
		categoryDao.addCategory(category);
	}

	@Override
	public List<Category> listCategory() {
		return categoryDao.listCategory();
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
	public void removeCategory(Integer id) {
		categoryDao.removeCategory(id);
	}

	@Override
	public Category findById(Integer id) {
		return categoryDao.findById(id);
	}

	@Override
	public void rename(Integer id, String newName) {
		Category c = categoryDao.findById(id);
		c.setName(newName);
	}

	@Override
	public void addFeatures(Integer id, Set<String> features) {
		Category c = categoryDao.findById(id);
		Set<Feature> feats = c.getFeatures();
		Set<String> feturesName = listFeatures(id);
		
		for(String f : features){
			if(feturesName.contains(f)){
				continue;
			}
			Feature feature = new Feature();
			feature.setName(f);
			feats.add(feature);
		}
		c.setFeatures(feats);
	}

	@Override
	public Set<String> listFeatures(Integer id) {
		Category c = categoryDao.findById(id);
		Set<Feature> features = c.getFeatures();
		Set<String> feturesName = new HashSet<String>();
		
		for(Feature f : features){
			feturesName.add(f.getName());
		}
		return feturesName;
	}
}
