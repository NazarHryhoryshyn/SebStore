package ua.sombra.webstore.service.databaseService.impls;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.Constants;
import ua.sombra.webstore.dao.interfaces.CategoryDAO;
import ua.sombra.webstore.entity.Category;
import ua.sombra.webstore.entity.Feature;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.ProductExtraFeature;
import ua.sombra.webstore.service.databaseService.interfaces.CategoryService;
import ua.sombra.webstore.service.databaseService.interfaces.FeatureService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductExtraFeatureService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

	private static final Logger log = Logger.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	private CategoryDAO categoryDao;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private FeatureService featureService;
	
	@Autowired
	private ProductExtraFeatureService productExtraFeatureService;
	
	@Override
	public void addTopCategory(Category category) {
		try{
			category.setIsSub(false);
			category.setMainCategoryId(0);
			categoryDao.create(category);
			log.info(category + " successfully created");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public void addSubCategory(Category category, Integer mainCategoryId) {
		try{
			category.setIsSub(true);
			category.setMainCategoryId(mainCategoryId);
			categoryDao.create(category);
			log.info(category + " successfully created");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Transactional
	@Override
	public void addCategory(String name, Integer mainCategoryId, List<String> featureNames){
		log.debug("Start to add new category with name="+name);
		try{
			if(featureNames.contains(Constants.EMPTY_LIST)){
				featureNames.remove(Constants.EMPTY_LIST);
			}
			Category newCategory = new Category();
			newCategory.setName(name);
			
			if(mainCategoryId == Constants.TOP_CATEGORY){
				categoryService.addTopCategory(newCategory);
			}
			else{
				categoryService.addSubCategory(newCategory, mainCategoryId);
			}
			
			Set<Category> editedCategories = new HashSet<Category>();
			Set<Product> editedProducts = new HashSet<Product>();
			editedCategories = categoryService.categoriesTreeFromCategory(newCategory);
			editedProducts = categoryService.productsTreeFromCategory(newCategory);
	
			Set<String> addExistsFeature = new HashSet<String>();
			Set<String> addNewFeatures = new HashSet<String>();
			
			for(String feature : featureNames){
				if(featureService.findByName(feature) != null ){
					addExistsFeature.add(feature);
				} else {
					addNewFeatures.add(feature);
				}
			}
			
			for(String fName : addExistsFeature){
				Feature f = featureService.findByName(fName);
				for(Category cat : editedCategories){
					categoryService.AddReferenceToFeature(cat.getId(), f.getId());
				}
			}
			
			for(String fName : addNewFeatures){
				Feature newFeat = new  Feature();
				newFeat.setName(fName);
				featureService.create(newFeat);
				for(Category cat : editedCategories){
					categoryService.AddReferenceToFeature(cat.getId(), newFeat.getId());
				}
			}
			
			for(Product p : editedProducts){
				for(String extraFeature : addExistsFeature){
					if(!p.hasFeature(extraFeature)){
						ProductExtraFeature newExtFeature = new ProductExtraFeature();
						newExtFeature.setName(extraFeature);
						newExtFeature.setValue(Constants.DEFAULT_EXTRA_FEATURE_VALUE);
						newExtFeature.setProduct(p);
						productExtraFeatureService.create(newExtFeature);					
						
					}
				}
				for(String extraFeature : addNewFeatures){
					if(!p.hasFeature(extraFeature)){
						ProductExtraFeature newExtFeature = new ProductExtraFeature();
						newExtFeature.setName(extraFeature);
						newExtFeature.setValue(Constants.DEFAULT_EXTRA_FEATURE_VALUE);
						newExtFeature.setProduct(p);
						productExtraFeatureService.create(newExtFeature);					
						
					}
				}
			}
			log.debug("Category with name="+name+" successfully aded");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public void delete(Integer id) {
		try{
			categoryDao.delete(id);
			log.info("Category with id="+id+" successfully deleted");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Transactional
	@Override
	public void editCategory(String newName, Integer categoryId, List<String> featureNames){
		log.debug("Start to edit category with id="+categoryId);
		try{
			if(featureNames.contains(Constants.EMPTY_LIST)){
				featureNames.remove(Constants.EMPTY_LIST);
			}
	
			Category editedCategory = categoryService.findById(categoryId);
			categoryService.renameCategory(editedCategory.getName(), newName);
			
			Set<Category> editedCategories = new HashSet<Category>();
			Set<Product> editedProducts = new HashSet<Product>();
			
			editedCategories = categoryService.categoriesTreeFromCategory(editedCategory);
			editedProducts = categoryService.productsTreeFromCategory(editedCategory);
			
			Set<String> featsOldNames = new HashSet<String>();
			Set<String> addExistsFeature = new HashSet<String>();
			Set<String> addNewFeatures = new HashSet<String>();
			Set<String> removeFeatures = new HashSet<String>();
			
			for(Feature p : editedCategory.getFeatures()){
				featsOldNames.add(p.getName());
			}
			
			for(String feature : featureNames){
				if(featureService.findByName(feature) != null && !featsOldNames.contains(feature)){
					addExistsFeature.add(feature);
				}
			}
			
			for(String oldFeature : featsOldNames){
				if(!featureNames.contains(oldFeature)){
					removeFeatures.add(oldFeature);
				}
			}
			
			for(String feature : featureNames){
				if(featureService.findByName(feature) == null){
					addNewFeatures.add(feature);
				}
			}
			
			for(String fName : addExistsFeature){
				Feature f = featureService.findByName(fName);
				for(Category cat : editedCategories){
					categoryService.AddReferenceToFeature(cat.getId(), f.getId());
				}
			}
			
			for(String fName : addNewFeatures){
				Feature newFeat = new  Feature();
				newFeat.setName(fName);
				featureService.create(newFeat);
				for(Category cat : editedCategories){
					categoryService.AddReferenceToFeature(cat.getId(), newFeat.getId());
				}
			}
			
			for(String removeFeat : removeFeatures){
				Feature removingFeat = featureService.findByName(removeFeat);
				
				for(Category cat : editedCategories){
					categoryService.RemoveReferenceToFeature(cat.getId(), removingFeat.getId());
				}
				
				removingFeat = featureService.findByName(removeFeat);
				if(removingFeat.getCategories().size() == 0){
					featureService.delete(removingFeat.getId());
				}
			}
			
			for(Product p : editedProducts){
				for(String extraFeature : addExistsFeature){
					if(!p.hasFeature(extraFeature)){
						ProductExtraFeature newExtFeature = new ProductExtraFeature();
						newExtFeature.setName(extraFeature);
						newExtFeature.setValue(Constants.DEFAULT_EXTRA_FEATURE_VALUE);
						newExtFeature.setProduct(p);
						productExtraFeatureService.create(newExtFeature);
					}
				}
				for(String extraFeature : addNewFeatures){
					if(!p.hasFeature(extraFeature)){
						ProductExtraFeature newExtFeature = new ProductExtraFeature();
						newExtFeature.setName(extraFeature);
						newExtFeature.setValue(Constants.DEFAULT_EXTRA_FEATURE_VALUE);
						newExtFeature.setProduct(p);
						productExtraFeatureService.create(newExtFeature);
					}
				}
				for(String extraFeature : removeFeatures){
					if(p.hasFeature(extraFeature)){
						ProductExtraFeature newExtFeature = p.getExtraFeatureByName(extraFeature);
						productExtraFeatureService.delete(newExtFeature.getId());
					}
				}
			}
			log.debug("Category with id="+categoryId+" succesfully edited");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Transactional
	@Override
	public void tryDeleteCategory(int categoryId){
		log.debug("Start to delete category with id="+categoryId);
		try{
			Category cat = categoryService.findById(categoryId);
			
			if(listSubCategories(categoryId).size() == 0 && cat.getProducts().size() == 0){
				for(Feature f : cat.getFeatures()){
					RemoveReferenceToFeature(cat.getId(), f.getId());
					if(f.getCategories().size() == 0){
						featureService.delete(f.getId());
					}
				}
				delete(categoryId);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public void renameCategory(String oldName, String newName) {
		try{
			categoryDao.renameCategory(oldName, newName);
			log.info("Category "+oldName+" successfully renamed to "+newName);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public Category findById(Integer id) {
		try{
			return categoryDao.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Category findByName(String name){
		try{
			return categoryDao.findByName(name);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<Category> listAllCategories() {
		try{
			return categoryDao.listAll();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<Category> listTopCategories() {
		try{
			return categoryDao.listTopCategories();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Set<String> listTopCategoriesNames(){
		Set<String> catNames = new HashSet<String>();
		try{
			for(Category c : categoryService.listTopCategories()){
				catNames.add(c.getName());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return catNames;
	}

	@Override
	public List<Category> listSubCategories(int mainCategoryId) {
		try{
			return categoryDao.listSubCategories(mainCategoryId);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void AddReferenceToFeature(int categoryId, int featureId){
		try{
			categoryDao.AddReferenceToFeature(categoryId, featureId);
			log.info("Category id="+categoryId+" and feature id="+featureId+" successfully referenced");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public void RemoveReferenceToFeature(int categoryId, int featureId){
		try{
			categoryDao.RemoveReferenceToFeature(categoryId, featureId);
			log.info("Category id="+categoryId+" and feature id="+featureId+" successfully unreferenced");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public void RemoveAllReferencesToFeatures(int categoryId){
		try{
			categoryDao.RemoveAllReferencesToFeatures(categoryId);
			log.info("Successfully removed all features from category id="+categoryId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	@Override
	public Set<String> featuresTreeToTop(Integer categoryId){
		try{
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
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public List<String> categoriesTreeToTop(Integer categoryId){
		try{
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
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public Set<Category> categoriesTreeFromCategory (Category category){
		try{
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
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public Map<Integer, List<Category>> mapCategorySubCats(List<Category> categs){
		Map<Integer, List<Category>> mapCategs = new HashMap<Integer, List<Category>>();
		try{
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
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return mapCategs;
	}
	
	@Override
	public Set<Product> productsTreeFromCategory (Category category){
		Set<Product> products = new HashSet<Product>();
		try{
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
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return products;
	}
	
	@Override
	public List<String> getAllTreeWithThisCategory(String categoryName){
		List<String> catNames = new ArrayList<String>();
		try{
			List<String> categoryTree = new ArrayList<String>();
			
			for(Category c : listTopCategories()){
				catNames.add(c.getName());
			}
			
			if(!categoryName.equals(Constants.SELECTED_ALL_CATEGORY)){
				Category currentCategory = categoryService.findByName(categoryName);
				
				categoryTree = categoryService.categoriesTreeToTop(currentCategory.getId());
				
				if(!categoryTree.contains(categoryName)){
					categoryTree.add(categoryName);
				}
	
				List<String> subCatNames = new ArrayList<String>();
				List<String> addingItems = new ArrayList<String>();
				
				for(String catName : categoryTree){
					subCatNames.clear();
					addingItems.clear();
					 for(Category c : categoryService.listSubCategories(categoryService.findByName(catName).getId())){
						 subCatNames.add(c.getName());
					 }
					 if(subCatNames.size() > 0){
						 addingItems.add(Constants.TOP_SEPARATOR);
						 for(int j = 0; j < subCatNames.size(); j++){
							 addingItems.add(subCatNames.get(j));
						 }
						 addingItems.add(Constants.BOTTOM_SEPARATOR);
						 int indexInsert = catNames.indexOf(catName);
						 List<String> newCatNames = new ArrayList<String>();
						 int ind = 0;
						 for(; ind <= indexInsert; ind++){
							 newCatNames.add(catNames.get(ind));
						 }
						 newCatNames.addAll(addingItems);
						 for(; ind < catNames.size(); ind++){
							 newCatNames.add(catNames.get(ind));
						 }
						 catNames = newCatNames;
					 }
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return catNames;
	}
	
	public Set<Product> getAllProductsFromCategory(String categoryName){
		Set<Product> catProducts = new TreeSet<Product>();
		try{
			if(categoryName.equals(Constants.SELECTED_ALL_CATEGORY)){
				for(Product p : productService.listProducts()){
					catProducts.add(p);
				}
			} else {
				catProducts = productsTreeFromCategory(categoryService.findByName(categoryName));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return catProducts;
	}
	
	public Set<Product> filterProducts(Set<Product> products, String prodName, String prodProducer, int prodMinPrice, int prodMaxPrice){
		Set<Product> filteredCatProducts = new TreeSet<Product>();
		try{
			if(!prodName.equals(Constants.SEARCH_ALL_COMMAND) || !prodProducer.equals(Constants.SEARCH_ALL_COMMAND) || prodMinPrice != 0 || prodMaxPrice != 0){
				BigDecimal bd = new BigDecimal(prodMinPrice);
				BigDecimal bd2 = new BigDecimal(prodMaxPrice);
				for(Product p : products){
					int res = p.getPrice().compareTo(bd);
					int res2 = p.getPrice().compareTo(bd2);
					boolean addProduct = true;				
					if(!prodName.equals(Constants.SEARCH_ALL_COMMAND) && !p.getName().matches("(.*)"+prodName+"(.*)")){
						addProduct = false;
					}
					if(!prodProducer.equals(Constants.SEARCH_ALL_COMMAND) && !p.getProducer().matches("(.*)"+prodProducer+"(.*)")){
						addProduct = false;
					}
					if(prodMinPrice != 0 && res != 1){
						addProduct = false;
					}
					if(prodMaxPrice != 0 && res2 != -1){
						addProduct = false;
					}
					
					if(addProduct){
						filteredCatProducts.add(p);
					}
				}
			} else {
				filteredCatProducts = products;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return filteredCatProducts;
	}
}
