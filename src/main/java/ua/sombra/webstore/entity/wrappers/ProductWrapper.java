package ua.sombra.webstore.entity.wrappers;

import ua.sombra.webstore.entity.Category;
import ua.sombra.webstore.entity.Product;

public class ProductWrapper{
	
	Product product;
	Category category;
	
	public ProductWrapper(){
	}
	
	public void setProduct(Product p){
		product = p;
	}
	
	public Product getProduct(){
		return product;
	}
	
	public void setCategory(Category cat){
		category = cat;
	}
	
	public Category getCategory(){
		return category;
	}
}
