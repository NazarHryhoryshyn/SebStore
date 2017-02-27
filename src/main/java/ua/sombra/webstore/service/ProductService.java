package ua.sombra.webstore.service;

import java.util.Map;
import java.util.Set;

import ua.sombra.webstore.domain.Product;

public interface ProductService {
	
	public void AddProduct(Product product);
	
	public void removeProduct(Integer id);

	public Set<Product> listProduct();
	
	public Product findById(Integer id);

	public Set<String> getExtraFeaturesName(Integer id);
	
	public Map<String, String> getExtraFeaturesNameValue(int id);
	
	public void setExtraFeatures(Integer id, Map<String, String> featureNameValue);
	
	public Set<Byte[]> getBytesPhotos(Integer id);

	public void addProductOnWarehouse(Integer id, Integer amount);
	
	public void removeProductFromWarehouse(Integer id, Integer amount);
	
	public void editProduct(Integer id, Map<String, String> propertiesNameValue);
}
