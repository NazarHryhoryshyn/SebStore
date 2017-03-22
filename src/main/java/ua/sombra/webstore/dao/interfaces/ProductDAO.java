package ua.sombra.webstore.dao.interfaces;

import java.util.List;

import ua.sombra.webstore.entity.Product;

public interface ProductDAO {
	
	public void create(Product product);

	public void delete(int id);
	
	public Product findById(int id);
	
	public Product findByName(String name);
	
	public void update(Product newParamsProduct);
	
	public List<Product> listAll();
	
	public void setNewCategory(int productId, int categoryId);
}
