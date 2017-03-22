package ua.sombra.webstore.service.databaseService.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.wrappers.PageInfo;

public interface ProductService {
	
	public void create(Product product);
	
	public void removeProduct(Integer id);

	public Product findById(Integer id);
	
	public Product findByName(String name);
	
	public void editProduct(int productId, String name, BigDecimal price, String category, String producer, String country
			, int weight, int amountOnWarehouse);
	
	public List<Product> listProducts();
	
	public Set<byte[]> getBytesPhotos(Integer id);
	
	public void editProductProperties(Integer id, Map<String, String> propertiesNameValue);
	
	public void setNewCategory(int productId, int categoryId);
	
	public Set<Integer> getIdProductPhotos(Product prod);
	
	public Map<Integer, Integer> getMapProductPhotos(Set<Product> products);
	
	public Set<Product> getPopularProducts();
	
	public BigDecimal getSumProductsPrices(Set<Product> products);
	
	public void tryRemoveProduct(int productId);
	
	public void update(Product p);
	
	public void changeCategory(int productId, String newCatName);
	
	public void addNewProduct(String name, BigDecimal price, String category, String producer
			, String country, int weight, int amountOnWarehouse);
	
	public void transactionEditProduct(int productId, String name, BigDecimal price, String category, String producer
			, String country, int weight, int amountOnWarehouse, List<String> extraFeatures);
	
	public PageInfo<Product> getPageInfoProducts(int page);
}
