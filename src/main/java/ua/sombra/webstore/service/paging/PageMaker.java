package ua.sombra.webstore.service.paging;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class PageMaker<T> {
	
	private Set<T> objects;
	
	private int amountObjectsOnPage;
	
	private int amountPagesInBlock;

	public PageMaker(){
		objects = new HashSet<T>();
		amountObjectsOnPage = 6;
		amountPagesInBlock = 5;
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
