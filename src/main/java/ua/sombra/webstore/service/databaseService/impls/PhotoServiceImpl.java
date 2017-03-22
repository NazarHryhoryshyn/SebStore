package ua.sombra.webstore.service.databaseService.impls;

import java.io.IOException;
import java.util.Arrays;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ua.sombra.webstore.dao.interfaces.PhotoDAO;
import ua.sombra.webstore.entity.Photo;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.service.databaseService.interfaces.PhotoService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;

@Service
public class PhotoServiceImpl implements PhotoService{
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private PhotoDAO photoDao;

	@Autowired
	private ProductService productService;
	
	@Override
	public void addPhoto(Photo photo) {
		photoDao.create(photo);
	}

	@Override
	public Photo findById(int id){
		return photoDao.findById(id);
	}

	@Override
	public void removePhoto(int id) {
		photoDao.delete(id);
	}
	
	@Override
	public void addNewPhoto(int productId, MultipartFile file){
		try {
			if(file != null && file.getBytes() != null ){
				Product prod = productService.findById(productId);
				for(Photo phot : prod.getPhotos()){
					byte[] bytesFile = file.getBytes();
					byte[] bytesPhoto = phot.getData();
					if(Arrays.equals(bytesFile, bytesPhoto)){
						return;
					}
				}
				
				Photo newPhoto = new Photo();
					newPhoto.setData(file.getBytes());
					newPhoto.setProduct(prod);
					addPhoto(newPhoto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
