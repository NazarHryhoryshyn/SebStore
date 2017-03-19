package ua.sombra.webstore.service.databaseService.impls;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.entity.Photo;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;

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
	public Product findById(Integer id) {
		return productDao.findById(id);
	}
	
	@Override
	public Product findByName(String name){
		return productDao.findByName(name);
	}
	
	@Override
	public void editProduct(Product newParamsProduct){
		productDao.editProduct(newParamsProduct);
	}
	
	@Override
	public List<Product> listProducts() {
		return productDao.listProducts();
	}

	@Override
	public Set<byte[]> getBytesPhotos(Integer id) {
		Product product = productDao.findById(id);
		Set<Photo> photos = product.getPhotos();
		Set<byte[]> byteOfPhotos = new HashSet<>();
		for (Photo p : photos) {
			byteOfPhotos.add(p.getData());
		}
		return byteOfPhotos;
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
	public void setNewCategory(int productId, int categoryId){
		productDao.setNewCategory(productId, categoryId);
	}
}
