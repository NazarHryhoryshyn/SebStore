package ua.sombra.webstore.dao.interfaces;

import java.util.Set;

import ua.sombra.webstore.domain.Product;

public interface ProductDAO {
	
	public void addProduct(Product product);

	public Set<Product> listProduct();
	
	public void removeProduct(Integer id);
	
	public Product findById(int id);
}
