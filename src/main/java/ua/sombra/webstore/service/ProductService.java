package ua.sombra.webstore.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;

public interface ProductService {
	
	public void AddProduct(Product product);
	
	public void removeProduct(Integer id);

	public List<Product> listProducts();
	
	public Product findById(Integer id);
	
	public Set<byte[]> getBytesPhotos(Integer id);

	public void addProductOnWarehouse(Integer id, Integer amount);
	
	public void editProductProperties(Integer id, Map<String, String> propertiesNameValue);
	
	public Product findByName(String name);
	
	public void editProduct(Integer productId, Product newParamsProduct);
	
	public void removeCategory(Product p);

	public void setCategory(Product p, Category c);
	
	public void addNewExtraFeatures(Product p, Set<String> featureNames);
	
	public void removeExtraFeatures(Product p, Set<String> featureNames);
	
	public void removeExtraFeature(Product p, ProductExtraFeatures extraFeature);
	
	public void addPhoto(Product product, Photo photo);

	public void removePhoto(Product product, Photo photo);
}
