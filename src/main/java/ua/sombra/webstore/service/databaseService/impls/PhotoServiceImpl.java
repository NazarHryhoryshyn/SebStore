package ua.sombra.webstore.service.databaseService.impls;

import java.io.IOException;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ua.sombra.webstore.dao.interfaces.PhotoDAO;
import ua.sombra.webstore.entity.Photo;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.service.databaseService.interfaces.PhotoService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService{
	
	private static final Logger log = Logger.getLogger(PhotoServiceImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private PhotoDAO photoDao;

	@Autowired
	private ProductService productService;
	
	@Override
	public void create(Photo photo) {
		try{
			photoDao.create(photo);
			log.info(photo + " successfully created");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public Photo findById(int id){
		try{
			return photoDao.findById(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void removePhoto(int id) {
		try{
			photoDao.delete(id);
			log.info("Photo with id="+id+" successfully deleted");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
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
					create(newPhoto);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
