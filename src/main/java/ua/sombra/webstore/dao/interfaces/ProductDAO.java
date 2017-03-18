package ua.sombra.webstore.dao.interfaces;

import java.util.List;
import ua.sombra.webstore.domain.Product;

public interface ProductDAO {
	
	public void addProduct(Product product);

	public void removeProduct(Integer id);
	
	public Product findById(Integer id);
	
	public Product findByName(String name);
	
	public void editProduct(Product newParamsProduct);
	
	public List<Product> listProducts();
}
