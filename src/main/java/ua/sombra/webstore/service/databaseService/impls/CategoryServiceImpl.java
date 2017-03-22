package ua.sombra.webstore.service.databaseService.impls;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CategoryServiceImpl implements CategoryService{

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
		category.setIsSub(false);
		category.setMainCategoryId(0);
		categoryDao.create(category);
	}

	@Override
	public void addSubCategory(Category category, Integer mainCategoryId) {
		category.setIsSub(true);
		category.setMainCategoryId(mainCategoryId);
		categoryDao.create(category);
	}

	@Override
	public void addCategory(String name, Integer mainCategoryId, List<String> featureNames){
		if(featureNames.contains("no_elements")){
			featureNames.remove("no_elements");
		}
		Category newCategory = new Category();
		newCategory.setName(name);
		
		if(mainCategoryId == -1){
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
			featureService.addFeature(newFeat);
			for(Category cat : editedCategories){
				categoryService.AddReferenceToFeature(cat.getId(), newFeat.getId());
			}
		}
		
		for(Product p : editedProducts){
			for(String extraFeature : addExistsFeature){
				if(!p.hasFeature(extraFeature)){
					ProductExtraFeature newExtFeature = new ProductExtraFeature();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("-");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);					
					
				}
			}
			for(String extraFeature : addNewFeatures){
				if(!p.hasFeature(extraFeature)){
					ProductExtraFeature newExtFeature = new ProductExtraFeature();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("-");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);					
					
				}
			}
		}
	}
	
	@Override
	public void delete(Integer id) {
		categoryDao.delete(id);
	}
	
	@Override
	public void editCategory(String newName, Integer categoryId, List<String> featureNames){
		if(featureNames.contains("no_elements")){
			featureNames.remove("no_elements");
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
			featureService.addFeature(newFeat);
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
				featureService.removeFeature(removingFeat.getId());
			}
		}
		
		for(Product p : editedProducts){
			for(String extraFeature : addExistsFeature){
				if(!p.hasFeature(extraFeature)){
					ProductExtraFeature newExtFeature = new ProductExtraFeature();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);
				}
			}
			for(String extraFeature : addNewFeatures){
				if(!p.hasFeature(extraFeature)){
					ProductExtraFeature newExtFeature = new ProductExtraFeature();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);
				}
			}
			for(String extraFeature : removeFeatures){
				if(p.hasFeature(extraFeature)){
					ProductExtraFeature newExtFeature = p.getExtraFeatureByName(extraFeature);
					productExtraFeatureService.removeProductExtraFeature(newExtFeature.getId());
				}
			}
		}
	}

	@Transactional
	@Override
	public void tryDeleteCategory(int categoryId){
		Category cat = categoryService.findById(categoryId);
		
		if(categoryService.listSubCategories(categoryId).size() == 0 && cat.getProducts().size() == 0){
			for(Feature f : cat.getFeatures()){
				categoryService.RemoveReferenceToFeature(cat.getId(), f.getId());
				if(f.getCategories().size() == 0){
					featureService.removeFeature(f.getId());
				}
			}
			categoryService.delete(categoryId);
		}
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
		return categoryDao.listAll();
	}

	@Override
	public List<Category> listTopCategories() {
		return categoryDao.listTopCategories();
	}
	
	@Override
	public Set<String> listTopCategoriesNames(){
		Set<String> catNames = new HashSet<String>();
		for(Category c : categoryService.listTopCategories()){
			catNames.add(c.getName());
		}
		return catNames;
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
	
	@Override
	public List<String> getAllTreeWithThisCategory(String categoryName){
		List<String> catNames = new ArrayList<String>();

		List<String> categoryTree = new ArrayList<String>();
		
		for(Category c : listTopCategories()){
			catNames.add(c.getName());
		}
		
		if(!categoryName.equals("All category")){
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
					 addingItems.add("topSeparator");
					 for(int j = 0; j < subCatNames.size(); j++){
						 addingItems.add(subCatNames.get(j));
					 }
					 addingItems.add("bottomSeparator");
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
		
		return catNames;
	}
	
	public Set<Product> getAllProductsFromCategory(String categoryName){
		Set<Product> catProducts = new TreeSet<Product>();
		
		if(categoryName.equals("All category")){
			for(Product p : productService.listProducts()){
				catProducts.add(p);
			}
		} else {
			catProducts = productsTreeFromCategory(categoryService.findByName(categoryName));
		}
		
		return catProducts;
	}
	
	public Set<Product> filterProducts(Set<Product> products, String prodName, String prodProducer, int prodMinPrice, int prodMaxPrice){
		Set<Product> filteredCatProducts = new TreeSet<Product>();
		
		if(!prodName.equals("all") || !prodProducer.equals("all") || prodMinPrice != 0 || prodMaxPrice != 0){
			BigDecimal bd = new BigDecimal(prodMinPrice);
			BigDecimal bd2 = new BigDecimal(prodMaxPrice);
			for(Product p : products){
				int res = p.getPrice().compareTo(bd);
				int res2 = p.getPrice().compareTo(bd2);
				boolean addProduct = true;				
				if(!prodName.equals("all") && !p.getName().matches("(.*)"+prodName+"(.*)")){
					addProduct = false;
				}
				if(!prodProducer.equals("all") && !p.getProducer().matches("(.*)"+prodProducer+"(.*)")){
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
		return filteredCatProducts;
	}
}
