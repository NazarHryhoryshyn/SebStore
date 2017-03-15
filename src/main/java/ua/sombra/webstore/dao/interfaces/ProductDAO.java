package ua.sombra.webstore.dao.interfaces;

import java.util.List;
import java.util.Set;

import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;

public interface ProductDAO {
	
	public void addProduct(Product product);

	public List<Product> listProducts();
	
	public void removeProduct(Integer id);

	public Product findById(Integer id);
	
	public Product findByName(String name);
	
	public void editProduct(Integer productId, Product newParamsProduct);

	public void removeCategory(Product p);

	public void addCategory(Product p, Category c);
	
	public void addNewExtraFeatures(Product p, Set<String> featureNames);
	
	public void removeExtraFeatures(Product p, Set<String> featureNames);
	
	public void removeExtraFeature(Product p, ProductExtraFeatures extraFeature);
	
	public void addPhoto(Product product, Photo photo);

	public void removePhoto(Product product, Photo photo);
}
