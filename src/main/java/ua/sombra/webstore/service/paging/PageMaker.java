package ua.sombra.webstore.service.paging;

import java.util.Set;

public interface PageMaker<T> {
	
	int totalPages();
	
	Set<T> getFromPage(int pageNumber);
	
	int getBlock(int pageNumber);
}
