package ua.sombra.webstore.service.paging;

import java.util.HashSet;
import java.util.Set;

import ua.sombra.webstore.domain.Product;

public class ProductPageMaker implements PageMaker<Product> {
	
	private Set<Product> products;
	
	public ProductPageMaker(){
		products = new HashSet<Product>();
	}
	
	public void setProducts(Set<Product> products){
		this.products = products;
	}
	
	@Override
	public int totalPages(){
		double pSize = products.size();
		double ceil = Math.ceil(pSize/6);
		int totalPages = (int) ceil;
		return totalPages;
	}

	@Override
	public Set<Product> getFromPage(int pageNumber){
		Set<Product> pageProds = new HashSet<Product>();
		for(int i = pageNumber * 6 - 6; i < pageNumber * 6 && i < products.size(); i++){
			pageProds.add((Product) products.toArray()[i]);
		}
		return pageProds;
	}
	
	@Override
	public int getBlock(int pageNumber){
		double pNumber = pageNumber;
		double ceil = Math.ceil(pNumber/5);
		int block = (int) ceil;
		return block;
	}
}
