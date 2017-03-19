package ua.sombra.webstore.service.databaseService.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Set;

import ua.sombra.webstore.entity.Product;

public interface ProductService {
	
	public void AddProduct(Product product);
	
	public void removeProduct(Integer id);

	public Product findById(Integer id);
	
	public Product findByName(String name);
	
	public void editProduct(Product newParamsProduct);
	
	public List<Product> listProducts();
	
	public Set<byte[]> getBytesPhotos(Integer id);
	
	public void editProductProperties(Integer id, Map<String, String> propertiesNameValue);
	
	public void setNewCategory(int productId, int categoryId);
}
