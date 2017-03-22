package ua.sombra.webstore.service.paging;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import ua.sombra.webstore.Constants;

public class PageMaker<T> {
	
	private Set<T> objects = new HashSet<T>();
	
	private int amountObjectsOnPage;
	
	private int amountPagesInBlock;

	public PageMaker(){
		amountObjectsOnPage = Constants.DEFAULT_AMOUNT_OBJECTS_ON_PAGE;
		amountPagesInBlock = Constants.DEFAULT_AMOUNT_PAGES_IN_BLOCK;
	}
	
	public PageMaker(Set<T> objects){
		this.objects = objects;
		amountObjectsOnPage = Constants.DEFAULT_AMOUNT_OBJECTS_ON_PAGE;
		amountPagesInBlock = Constants.DEFAULT_AMOUNT_PAGES_IN_BLOCK;
	}
	
	public int getAmountObjectsOnPage() {
		return amountObjectsOnPage;
	}

	public void setAmountObjectsOnPage(int amountProductsOnPage) {
		this.amountObjectsOnPage = amountProductsOnPage;
	}
	
	public int getAmountPagesInBlock() {
		return amountPagesInBlock;
	}

	public void setAmountPagesInBlock(int amountPagesInBlock) {
		this.amountPagesInBlock = amountPagesInBlock;
	}
	
	public void setObjects(Set<T> objects){
		this.objects = objects;
	}
	
	public int totalPages(){
		double pSize = objects.size();
		double ceil = Math.ceil(pSize/amountObjectsOnPage);
		int totalPages = (int) ceil;
		return totalPages;
	}

	@SuppressWarnings("unchecked")
	public Set<T> getFromPage(int pageNumber){
		Set<T> pageObjs = new TreeSet<T>();
		for(int i = pageNumber * amountObjectsOnPage - amountObjectsOnPage; i < pageNumber * amountObjectsOnPage && i < objects.size(); i++){
			pageObjs.add((T) objects.toArray()[i]);
		}
		return pageObjs;
	}
	
	public int getBlock(int pageNumber){
		double pNumber = pageNumber;
		double ceil = Math.ceil(pNumber/amountPagesInBlock);
		int block = (int) ceil;
		return block;
	}
}
