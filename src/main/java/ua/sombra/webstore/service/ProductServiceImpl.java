package ua.sombra.webstore.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ua.sombra.webstore.dao.interfaces.ProductDAO;
import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;

public class ProductServiceImpl implements ProductService {

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
	public Set<Product> listProduct() {
		return productDao.listProduct();
	}

	@Override
	public Product findById(Integer id) {
		return productDao.findById(id);
	}

	@Override
	public Set<String> getExtraFeaturesName(Integer id) {
		Product p = productDao.findById(id);
		Set<ProductExtraFeatures> features = p.getProductExtraFeatures();
		Set<String> featuresName = new HashSet<String>();
		for (ProductExtraFeatures f : features) {
			featuresName.add(f.getName());
		}
		return featuresName;
	}

	@Override
	public Map<String, String> getExtraFeaturesNameValue(int id) {
		Product p = productDao.findById(id);
		Set<ProductExtraFeatures> features = p.getProductExtraFeatures();
		Map<String, String> featuresNameValue = new HashMap<String, String>();
		for (ProductExtraFeatures f : features) {
			featuresNameValue.put(f.getName(), f.getValue());
		}
		return featuresNameValue;
	}

	@Override
	public void setExtraFeatures(Integer id, Map<String, String> featureNameValue) {
		Product p = productDao.findById(id);
		Set<ProductExtraFeatures> features = p.getProductExtraFeatures();
		Set<String> featuresName = getExtraFeaturesName(id);
		for (Map.Entry<String, String> entry : featureNameValue.entrySet()) {
			if (featuresName.contains(entry.getKey())) {
				continue;
			}
			ProductExtraFeatures pef = new ProductExtraFeatures();
			pef.setName(entry.getValue());
			pef.setValue(entry.getValue());
			pef.setProduct(p);
			features.add(pef);
		}
		p.setProductExtraFeatures(features);
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
	public void removeProductFromWarehouse(Integer id, Integer amount) {
		Product product = productDao.findById(id);
		product.setAmountOnWarehouse(product.getAmountOnWarehouse() - amount);
	}

	@Override
	public void editProduct(Integer id, Map<String, String> propertiesNameValue) {
		Product product = productDao.findById(id);
		for (Map.Entry<String, String> entry : propertiesNameValue.entrySet()) {
			try {
				product.getClass().getMethod("set" + entry.getKey(), String.class).invoke(product, entry.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
