package ua.sombra.webstore.service.databaseService.impls;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.sombra.webstore.dao.interfaces.PhotoDAO;
import ua.sombra.webstore.entity.Photo;
import ua.sombra.webstore.service.databaseService.interfaces.PhotoService;

@Service
public class PhotoServiceImpl implements PhotoService{
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private PhotoDAO photoDao;
	
	public void addPhoto(Photo photo) {
		photoDao.addPhoto(photo);
	}

	public Photo findById(int id){
		return photoDao.findById(id);
	}
	
	public void removePhoto(int id) {
		photoDao.removePhoto(id);
	}
}
