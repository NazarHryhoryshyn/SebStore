package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.domain.Product;

public interface ProductDAO {
	
	public void addProduct(Product product);

	public List<Product> listProduct();
	
	public void removeProduct(Integer id);
	
	public Product findById(int id);
}
