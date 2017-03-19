package ua.sombra.webstore.entity.wrappers;

import java.util.Set;

public class PageInfo<T> {
	Set<T> objects;
	int page;
	int totalPages;
	int block;
	int amountPagesInBlock;
	
	public Set<T> getObjects() {
		return objects;
	}
	public void setObjects(Set<T> objects) {
		this.objects = objects;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getBlock() {
		return block;
	}
	public void setBlock(int block) {
		this.block = block;
	}

	public int getAmountPagesInBlock() {
		return amountPagesInBlock;
	}

	public void setAmountPagesInBlock(int amountPagesInBlock) {
		this.amountPagesInBlock = amountPagesInBlock;
	}
}
