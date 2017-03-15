package ua.sombra.webstore.domain;

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
