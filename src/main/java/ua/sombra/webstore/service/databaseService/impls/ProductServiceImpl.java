package ua.sombra.webstore.service.databaseService.impls;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.entity.Category;
import ua.sombra.webstore.entity.Feature;
import ua.sombra.webstore.entity.Photo;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.ProductExtraFeature;
import ua.sombra.webstore.entity.wrappers.PageInfo;
import ua.sombra.webstore.service.databaseService.interfaces.CategoryService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductExtraFeatureService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.paging.PageMaker;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO productDao;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductExtraFeatureService productExtraFeatureService;
	
	@Override
	public void create(Product product) {
		productDao.create(product);
	}

	@Override
	public void removeProduct(Integer id) {
		productDao.delete(id);
	}

	@Override 
	public Product findById(Integer id) {
		return productDao.findById(id);
	}
	
	@Override
	public Product findByName(String name){
		return productDao.findByName(name);
	}
	
	@Override
	public void editProduct(int productId, String name, BigDecimal price, String category, String producer, String country
			, int weight, int amountOnWarehouse){
		Product editedProduct = findById(productId);
		editedProduct.setName(name);
		editedProduct.setPrice(price);
		editedProduct.setProducer(producer);
		editedProduct.setCountry(country);
		editedProduct.setWeight(weight);
		editedProduct.setAmountOnWarehouse(amountOnWarehouse);
		
		productDao.update(editedProduct);
	}
	
	@Override
	public List<Product> listProducts() {
		return productDao.listAll();
	}

	@Override
	public Set<byte[]> getBytesPhotos(Integer id) {
		Product product = productDao.findById(id);
		Set<Photo> photos = product.getPhotos();
		Set<byte[]> byteOfPhotos = new HashSet<>();
		for (Photo p : photos) {
			byteOfPhotos.add(p.getData());
		}
		return byteOfPhotos;
	}

	@Override
	public void editProductProperties(Integer id, Map<String, String> propertiesNameValue) {
		Product product = productDao.findById(id);
		for (Map.Entry<String, String> entry : propertiesNameValue.entrySet()) {
			try {
				product.getClass().getMethod("set" + entry.getKey(), String.class).invoke(product, entry.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setNewCategory(int productId, int categoryId){
		productDao.setNewCategory(productId, categoryId);
	}
	
	@Override
	public Set<Integer> getIdProductPhotos(Product prod){
		Set<Integer> photosIds = new HashSet<Integer>();
		for(Photo ph : prod.getPhotos()){
			photosIds.add(ph.getId());
		}
		return photosIds;
	}
	
	@Override
	public Map<Integer, Integer> getMapProductPhotos(Set<Product> products){
		Map<Integer, Integer> mapPhotProd = new HashMap<Integer, Integer>();
		
		for(Product p : products){
			mapPhotProd.put(p.getId(), p.getPhotos().iterator().next().getId());
		}
		
		return mapPhotProd;
	}

	@Override
	public Set<Product> getPopularProducts() {
		Set<Product> popularProducts = new HashSet<Product>();
		List<Product> prods = listProducts();		
		int prodSize = prods.size();
		for(int i = 0; i < prodSize || i < 12; i++){
			popularProducts.add(prods.get(i));
		}
		return popularProducts;
	}
	
	@Override
	public BigDecimal getSumProductsPrices(Set<Product> products){
		BigDecimal productSumPrice = new BigDecimal(0);
		for(Product p : products){
			productSumPrice = productSumPrice.add(p.getPrice());
		}
		return productSumPrice;
	}
	
	@Transactional
	@Override
	public void tryRemoveProduct(int productId){
		Product p = findById(productId);
		if(p.getOrders().size() == 0 && p.getUsers().size() == 0){
			productExtraFeatureService.removeAllExtraFeaturesFromProduct(productId);
			removeProduct(productId);
		}
	}
	
	@Override
	public void update(Product p){
		productDao.update(p);
	}
	
	@Transactional
	@Override
	public void changeCategory(int productId, String newCatName){
		Product editedProduct = findById(productId);
		
		Category oldCat = editedProduct.getCategory();
		Category newCat = categoryService.findByName(newCatName);

		if(!oldCat.getName().equals(newCatName)){
			productExtraFeatureService.removeAllExtraFeaturesFromProduct(editedProduct.getId());
			setNewCategory(editedProduct.getId(), newCat.getId());
			for(Feature f : newCat.getFeatures()){
				ProductExtraFeature newPef = new ProductExtraFeature();
				newPef.setName(f.getName());
				newPef.setValue("-");
				newPef.setProduct(editedProduct);
				productExtraFeatureService.addProductExtraFeature(newPef);
			}
		}
	}

	@Transactional
	@Override
	public void addNewProduct(String name, BigDecimal price, String category, String producer
			, String country, int weight, int amountOnWarehouse){
		if(findByName(name) != null){
			return;
		}
		
		Category cat = categoryService.findByName(category);
		
		Product newProduct = new Product();
		newProduct.setName(name);
		newProduct.setPrice(price);
		newProduct.setProducer(producer);
		newProduct.setCountry(country);
		newProduct.setWeight(weight);
		newProduct.setAmountOnWarehouse(amountOnWarehouse);
		newProduct.setCategory(categoryService.findByName(category));
		
		create(newProduct);
		
		Set<String> featureNames = categoryService.featuresTreeToTop(cat.getId());
		for(String featName : featureNames){
			ProductExtraFeature newExtraFeature = new ProductExtraFeature();
			newExtraFeature.setName(featName);
			newExtraFeature.setValue("");
			newExtraFeature.setProduct(newProduct);
			productExtraFeatureService.addProductExtraFeature(newExtraFeature);
		}
	}
	
	@Transactional
	@Override
	public void transactionEditProduct(int productId, String name, BigDecimal price, String category, String producer
			, String country, int weight, int amountOnWarehouse, List<String> extraFeatures){
		Map<String, String> efNameValue = productExtraFeatureService.parseStringToMapFeatures(extraFeatures);
		productExtraFeatureService.setValuesExtraFeatures(productId, efNameValue);
		editProduct(productId, name, price, category, producer, country, weight, amountOnWarehouse);
		changeCategory(productId, category);
	}
	
	@Override
	public PageInfo<Product> getPageInfoProducts(int page){
		PageInfo<Product> productInfo = new PageInfo<Product>();
		Set<Product> allProducts = new TreeSet<Product>();
		
		for(Product p : listProducts()){
			allProducts.add(p);
		}
		
		PageMaker<Product> pgMaker = new PageMaker<Product>();
		pgMaker.setObjects(allProducts);

		productInfo.setTotalPages(pgMaker.totalPages());
		productInfo.setPage(page);
		productInfo.setBlock(pgMaker.getBlock(page));
		productInfo.setObjects(pgMaker.getFromPage(page));
		productInfo.setAmountPagesInBlock(pgMaker.getAmountPagesInBlock());
		
		return productInfo;
	}
}
