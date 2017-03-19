package ua.sombra.webstore.web;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import ua.sombra.webstore.entity.Category;
import ua.sombra.webstore.entity.Feature;
import ua.sombra.webstore.entity.Orders;
import ua.sombra.webstore.entity.Photo;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.ProductExtraFeature;
import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.entity.wrappers.PageInfo;
import ua.sombra.webstore.entity.wrappers.ProductWrapper;
import ua.sombra.webstore.service.databaseService.interfaces.CategoryService;
import ua.sombra.webstore.service.databaseService.interfaces.FeatureService;
import ua.sombra.webstore.service.databaseService.interfaces.OrderService;
import ua.sombra.webstore.service.databaseService.interfaces.PhotoService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductExtraFeatureService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;
import ua.sombra.webstore.service.paging.PageMaker;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FeatureService featureService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductExtraFeatureService productExtraFeatureService;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Model model) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		
		return "admin";
	}

	@RequestMapping(value = "/admin/getUsers-{page}", method = RequestMethod.GET)
	public @ResponseBody PageInfo<User> getUsers(@PathVariable("page") int page){
		PageInfo<User> userInfo = new PageInfo<User>();
		Set<User> allUsers = new TreeSet<User>();
		
		for(User u : userService.listUsers()){
			allUsers.add(u);
		}
		
		PageMaker<User> pgMaker = new PageMaker<User>();
		pgMaker.setObjects(allUsers);

		userInfo.setTotalPages(pgMaker.totalPages());
		userInfo.setPage(page);
		userInfo.setBlock(pgMaker.getBlock(page));
		userInfo.setObjects(pgMaker.getFromPage(page));
		userInfo.setAmountPagesInBlock(pgMaker.getAmountPagesInBlock());
		return userInfo;
	}
	
	@RequestMapping(value = "/admin/checkUserIsAdmin", method = RequestMethod.GET)
	public @ResponseBody boolean getUserIsAdmin(@RequestParam("login") String login){
		return userService.UserIsAdmin(login);
	}
	
	@RequestMapping(value = "/admin/checkUserIsBlocked", method = RequestMethod.GET)
	public @ResponseBody boolean getUserIsBlocked(@RequestParam("login") String login){
		return userService.UserIsBlocked(login);
	}
	
	@RequestMapping(value = "/admin/user/orders", method = RequestMethod.GET)
	public @ResponseBody Set<Orders> userOrders(@RequestParam("login") String login){
		User u = userService.findByLogin(login);
		 return u.getOrders();
	}	
	
	@RequestMapping(value = "/admin/changeIsAdmin", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeIsAdmin(@RequestParam("login") String login, @RequestParam("status") Boolean status){
		userService.ChangeIsAdmin(login, status);
	}
	
	@RequestMapping(value = "/admin/changeIsBlocked", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeIsBlocked(@RequestParam("login") String login, @RequestParam("status") Boolean status){
		userService.ChangeIsBlocked(login, status);
	}
	
	@RequestMapping(value = "/admin/TreeCategories", method = RequestMethod.GET)
	public @ResponseBody Map<Integer, List<Category>> getTreeCategories(){
		return categoryService.mapCategorySubCats(categoryService.listTopCategories());
	}
	
	@RequestMapping(value = "/admin/MainCategories", method = RequestMethod.GET)
	public @ResponseBody List<Category> getMainCategories(){
		return categoryService.listTopCategories();
	}
	
	@RequestMapping(value = "/admin/allCategories", method = RequestMethod.GET)
	public @ResponseBody List<Category> getAllCategories(){
		return categoryService.listAllCategories();
	}
	
	@RequestMapping(value = "/admin/getCategory", method = RequestMethod.GET)
	public @ResponseBody Category getCategoryById(@RequestParam int categoryId){
		return categoryService.findById(categoryId);
	}
	
	@RequestMapping(value = "/admin/addCategory", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addCategory(@RequestParam("name") String name
			, @RequestParam("mainCategoryId") Integer mainCategoryId
			, @RequestParam("featureNames[]") List<String> featureNames
			){
		if(featureNames.contains("no_elements")){
			featureNames.remove("no_elements");
		}
		Category newCategory = new Category();
		newCategory.setName(name);
		
		if(mainCategoryId == -1){
			categoryService.addTopCategory(newCategory);
		}
		else{
			categoryService.addSubCategory(newCategory, mainCategoryId);
		}
		
		Set<Category> editedCategories = new HashSet<Category>();
		Set<Product> editedProducts = new HashSet<Product>();
		editedCategories = categoryService.categoriesTreeFromCategory(newCategory);
		editedProducts = categoryService.productsTreeFromCategory(newCategory);

		Set<String> addExistsFeature = new HashSet<String>();
		Set<String> addNewFeatures = new HashSet<String>();
		
		for(String feature : featureNames){
			if(featureService.findByName(feature) != null ){
				addExistsFeature.add(feature);
			} else {
				addNewFeatures.add(feature);
			}
		}
		
		for(String fName : addExistsFeature){
			Feature f = featureService.findByName(fName);
			for(Category cat : editedCategories){
				categoryService.AddReferenceToFeature(cat.getId(), f.getId());
			}
		}
		
		for(String fName : addNewFeatures){
			Feature newFeat = new  Feature();
			newFeat.setName(fName);
			featureService.addFeature(newFeat);
			for(Category cat : editedCategories){
				categoryService.AddReferenceToFeature(cat.getId(), newFeat.getId());
			}
		}
		
		for(Product p : editedProducts){
			for(String extraFeature : addExistsFeature){
				if(!p.hasFeature(extraFeature)){
					ProductExtraFeature newExtFeature = new ProductExtraFeature();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);					
					
				}
			}
			for(String extraFeature : addNewFeatures){
				if(!p.hasFeature(extraFeature)){
					ProductExtraFeature newExtFeature = new ProductExtraFeature();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);					
					
				}
			}
		}
	}
	
	@RequestMapping(value = "/admin/editCategory", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void editCategory(@RequestParam("name") String newName
			, @RequestParam("categoryId") Integer categoryId
			, @RequestParam("featureNames[]") List<String> featureNames
			){
		if(featureNames.contains("no_elements")){
			featureNames.remove("no_elements");
		}

		Category editedCategory = categoryService.findById(categoryId);
		categoryService.renameCategory(editedCategory.getName(), newName);
		
		Set<Category> editedCategories = new HashSet<Category>();
		Set<Product> editedProducts = new HashSet<Product>();
		
		editedCategories = categoryService.categoriesTreeFromCategory(editedCategory);
		editedProducts = categoryService.productsTreeFromCategory(editedCategory);
		
		Set<String> featsOldNames = new HashSet<String>();
		Set<String> addExistsFeature = new HashSet<String>();
		Set<String> addNewFeatures = new HashSet<String>();
		Set<String> removeFeatures = new HashSet<String>();
		
		for(Feature p : editedCategory.getFeatures()){
			featsOldNames.add(p.getName());
		}
		
		for(String feature : featureNames){
			if(featureService.findByName(feature) != null && !featsOldNames.contains(feature)){
				addExistsFeature.add(feature);
			}
		}
		
		for(String oldFeature : featsOldNames){
			if(!featureNames.contains(oldFeature)){
				removeFeatures.add(oldFeature);
			}
		}
		
		for(String feature : featureNames){
			if(featureService.findByName(feature) == null){
				addNewFeatures.add(feature);
			}
		}
		
		for(String fName : addExistsFeature){
			Feature f = featureService.findByName(fName);
			for(Category cat : editedCategories){
				categoryService.AddReferenceToFeature(cat.getId(), f.getId());
			}
		}
		
		for(String fName : addNewFeatures){
			Feature newFeat = new  Feature();
			newFeat.setName(fName);
			featureService.addFeature(newFeat);
			for(Category cat : editedCategories){
				categoryService.AddReferenceToFeature(cat.getId(), newFeat.getId());
			}
		}
		
		for(String removeFeat : removeFeatures){
			Feature removingFeat = featureService.findByName(removeFeat);
			
			for(Category cat : editedCategories){
				categoryService.RemoveReferenceToFeature(cat.getId(), removingFeat.getId());
			}
			
			removingFeat = featureService.findByName(removeFeat);
			if(removingFeat.getCategories().size() == 0){
				featureService.removeFeature(removingFeat.getId());
			}
		}
		
		for(Product p : editedProducts){
			for(String extraFeature : addExistsFeature){
				if(!p.hasFeature(extraFeature)){
					ProductExtraFeature newExtFeature = new ProductExtraFeature();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);
				}
			}
			for(String extraFeature : addNewFeatures){
				if(!p.hasFeature(extraFeature)){
					ProductExtraFeature newExtFeature = new ProductExtraFeature();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);
				}
			}
			for(String extraFeature : removeFeatures){
				if(p.hasFeature(extraFeature)){
					ProductExtraFeature newExtFeature = p.getExtraFeatureByName(extraFeature);
					productExtraFeatureService.removeProductExtraFeature(newExtFeature);
				}
			}
		}	
	}
	
	@RequestMapping(value = "/admin/removeCategory", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeCategory(@RequestParam("categoryId") Integer categoryId){
		Category cat = categoryService.findById(categoryId);
		
		if(categoryService.listSubCategories(categoryId).size() == 0 && cat.getProducts().size() == 0){
			for(Feature f : cat.getFeatures()){
				categoryService.RemoveReferenceToFeature(cat.getId(), f.getId());
				if(f.getCategories().size() == 0){
					featureService.removeFeature(f.getId());
				}
			}
			categoryService.removeCategory(categoryId);
		}
	}
	
	@RequestMapping(value = "/admin/getProducts-{page}", method = RequestMethod.GET)
	public @ResponseBody PageInfo<Product> getProducts(@PathVariable("page") int page){
		PageInfo<Product> productInfo = new PageInfo<Product>();
		Set<Product> allProducts = new TreeSet<Product>();
		
		for(Product p : productService.listProducts()){
			allProducts.add(p);
		}
		
		PageMaker<Product> pgMaker = new PageMaker<Product>();
		pgMaker.setObjects(allProducts);

		productInfo.setTotalPages(pgMaker.totalPages());
		productInfo.setPage(page);
		productInfo.setBlock(pgMaker.getBlock(page));
		productInfo.setObjects(pgMaker.getFromPage(page));
		productInfo.setAmountPagesInBlock(pgMaker.getAmountPagesInBlock());
		return productInfo;
	}
	
	@RequestMapping(value = "/admin/getProduct", method = RequestMethod.GET)
	public @ResponseBody ProductWrapper getProductById(@RequestParam("productId") int productId){
		ProductWrapper pw = new ProductWrapper();
		Product p = productService.findById(productId);
		pw.setProduct(p);
		pw.setCategory(p.getCategory());
		return pw;
	}
	
	@RequestMapping(value = "/admin/getCatNameProduct", method = RequestMethod.GET)
	public @ResponseBody String getCatNameProduct(@RequestParam("productId") int productId){
		Product p = productService.findById(productId);
		return p.getCategory().getName();
	}
	
	@RequestMapping(value = "/admin/addProduct", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addProduct(@RequestParam("name") String name
			, @RequestParam("price") BigDecimal price
			, @RequestParam("category") String category
			, @RequestParam("producer") String producer
			, @RequestParam("country") String country
			, @RequestParam("weight") Integer weight
			, @RequestParam("amountOnWarehouse") Integer amountOnWarehouse
			){
		
		if(productService.findByName(name) != null){
			return;
		}
		
		Category cat = categoryService.findByName(category);
		
		Product newProduct = new Product();
		newProduct.setName(name);
		newProduct.setPrice(price);
		newProduct.setProducer(producer);
		newProduct.setCountry(country);
		newProduct.setWeight(weight);
		newProduct.setAmountOnWarehouse(amountOnWarehouse);
		newProduct.setCategory(categoryService.findByName(category));
		
		productService.AddProduct(newProduct);
		
		Set<String> featureNames = categoryService.featuresTreeToTop(cat.getId());
		for(String featName : featureNames){
			ProductExtraFeature newExtraFeature = new ProductExtraFeature();
			newExtraFeature.setName(featName);
			newExtraFeature.setValue("");
			newExtraFeature.setProduct(newProduct);
			productExtraFeatureService.addProductExtraFeature(newExtraFeature);
		}
	}
		
	@RequestMapping(value = "/admin/editProduct", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void editProduct(@RequestParam("productId") Integer productId
			, @RequestParam("name") String name
			, @RequestParam("price") BigDecimal price
			, @RequestParam("category") String category
			, @RequestParam("producer") String producer
			, @RequestParam("country") String country
			, @RequestParam("weight") Integer weight
			, @RequestParam("amountOnWarehouse") Integer amountOnWarehouse
			, @RequestParam("extraFeatures[]") List<String> extraFeatures
			){
		
		Map<String, String> efNameValue = new HashMap<String, String>();
		if(extraFeatures.contains("no_elements")){
			extraFeatures.remove("no_elements");
		}
		for(String efName : extraFeatures){
			String[] nameValue = efName.split("__");
			if(nameValue[1] == "null_null"){
				nameValue[1] = "";
			}
			efNameValue.put(nameValue[0], nameValue[1]);
		}
		
		productExtraFeatureService.setValuesExtraFeatures(productId, efNameValue);
		
		Product editedProduct = productService.findById(productId);
		editedProduct.setName(name);
		editedProduct.setPrice(price);
		editedProduct.setProducer(producer);
		editedProduct.setCountry(country);
		editedProduct.setWeight(weight);
		editedProduct.setAmountOnWarehouse(amountOnWarehouse);

		productService.editProduct(editedProduct);
		
		Category oldCat = editedProduct.getCategory();
		Category newCat = categoryService.findByName(category);

		if(!oldCat.getName().equals(newCat.getName())){
			productExtraFeatureService.removeAllExtraFeaturesFromProduct(editedProduct.getId());
			productService.setNewCategory(editedProduct.getId(), newCat.getId());
			for(Feature f : newCat.getFeatures()){
				ProductExtraFeature newPef = new ProductExtraFeature();
				newPef.setName(f.getName());
				newPef.setValue("-");
				newPef.setProduct(editedProduct);
				productExtraFeatureService.addProductExtraFeature(newPef);
			}
		}
	}
	
	@RequestMapping(value = "/admin/removeProduct", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeProduct(@RequestParam("productId") Integer productId){
		Product p = productService.findById(productId);
		if(p.getOrders().size() == 0 && p.getUsers().size() == 0){
			productExtraFeatureService.removeAllExtraFeaturesFromProduct(productId);
			productService.removeProduct(productId);
		}
	}
	
	@RequestMapping(value = "/admin/addProductPhoto/{productId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addProductPhoto(@PathVariable("productId") int productId,
			@RequestParam("file") MultipartFile file) {

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
					photoService.addPhoto(newPhoto);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	
	@RequestMapping(value = "/admin/removeProductPhoto", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeProductPhoto(@RequestParam("photoId") int photoId) {
		photoService.removePhoto(photoId);
	}
	
	@RequestMapping(value = "/admin/product/photo", method = RequestMethod.GET)
	public void getImage(@RequestParam("id") Integer photoId, HttpServletResponse response){
	    Photo photo = photoService.findById(photoId);        
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    try {
			response.getOutputStream().write(photo.getData());
		    response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/admin/getOrders-{page}", method = RequestMethod.GET)
	public @ResponseBody PageInfo<Orders> getOrders(@PathVariable("page") int page){
		PageInfo<Orders> orderInfo = new PageInfo<Orders>();
		Set<Orders> allOrders = new TreeSet<Orders>();
		
		for(Orders o : orderService.listOrders()){
			allOrders.add(o);
		}
		
		PageMaker<Orders> pgMaker = new PageMaker<Orders>();
		pgMaker.setObjects(allOrders);

		orderInfo.setTotalPages(pgMaker.totalPages());
		orderInfo.setPage(page);
		orderInfo.setBlock(pgMaker.getBlock(page));
		orderInfo.setObjects(pgMaker.getFromPage(page));
		orderInfo.setAmountPagesInBlock(pgMaker.getAmountPagesInBlock());
		return orderInfo;
	}
	
	@RequestMapping(value = "/admin/getUserLoginFromOrder", method = RequestMethod.GET)
	public @ResponseBody String getUserFromOrder(@RequestParam("orderId") Integer orderId){
		Orders p = orderService.findById(orderId);
		return p.getUser().getLogin();
	}
	
	@RequestMapping(value = "/admin/changeOrderStatus", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeOrderStatus(@RequestParam("orderId") int orderId, @RequestParam("status") String status){
		orderService.changeStatus(orderId, status);
	}
}




