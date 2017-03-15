package ua.sombra.webstore.service;

import java.util.HashMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO productDao;

	@Override
	public void AddProduct(Product product) {
		productDao.addProduct(product);
	}

	@Override
	public void removeProduct(Integer id) {
		productDao.removeProduct(id);
	}

	@Override
	public List<Product> listProducts() {
		return productDao.listProducts();
	}

	@Override 
	public Product findById(Integer id) {
		return productDao.findById(id);
	}
	
	@Override
	public Set<Byte[]> getBytesPhotos(Integer id) {
		Product product = productDao.findById(id);
		Set<Photo> photos = product.getPhotos();
		Set<Byte[]> byteOfPhotos = new HashSet<>();
		for (Photo p : photos) {
			byteOfPhotos.add(p.getData());
		}
		return byteOfPhotos;
	}

	@Override
	public void addProductOnWarehouse(Integer id, Integer amount) {
		Product product = productDao.findById(id);
		product.setAmountOnWarehouse(product.getAmountOnWarehouse() + amount);
	}

	@Override
	public void editProductProperties(Integer id, Map<String, String> propertiesNameValue) {
		Product product = productDao.findById(id);
		for (Map.Entry<String, String> entry : propertiesNameValue.entrySet()) {
			try {
				product.getClass().getMethod("set" + entry.getKey(), String.class).invoke(product, entry.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public Product findByName(String name){
		return productDao.findByName(name);
	}
	
	@Override
	public void editProduct(Integer productId, Product newParamsProduct){
		productDao.editProduct(productId, newParamsProduct);
	}
	
	@Override
	public void removeCategory(Product p){
		productDao.removeCategory(p);
	}

	@Override
	public void setCategory(Product p, Category c){
		productDao.addCategory(p, c);
	}
	
	@Override
	public void addNewExtraFeatures(Product p, Set<String> featureNames){
		productDao.addNewExtraFeatures(p, featureNames);
	}
	
	@Override
	public void removeExtraFeatures(Product p, Set<String> featureNames){
		productDao.removeExtraFeatures(p, featureNames);
	}
	
	@Override
	public void removeExtraFeature(Product p, ProductExtraFeatures extraFeature){
		productDao.removeExtraFeature(p, extraFeature);
	}
}
