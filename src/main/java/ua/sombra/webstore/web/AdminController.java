package ua.sombra.webstore.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
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

import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Feature;
import ua.sombra.webstore.domain.OrderWrapper;
import ua.sombra.webstore.domain.Orders;
import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;
import ua.sombra.webstore.domain.ProductWrapper;
import ua.sombra.webstore.domain.User;
import ua.sombra.webstore.service.databaseService.interfaces.CategoryService;
import ua.sombra.webstore.service.databaseService.interfaces.FeatureService;
import ua.sombra.webstore.service.databaseService.interfaces.OrderService;
import ua.sombra.webstore.service.databaseService.interfaces.PhotoService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductExtraFeatureService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private FeatureService featureService;
	
	@Autowired
	private SecurityService securityService;

	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductExtraFeatureService productExtraFeatureService;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Model model) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		List<User> users = userService.listUsers();
		
		Map<String, Boolean> isAdmins = new HashMap<String, Boolean>();
		Map<String, Boolean> isBlockeds = new HashMap<String, Boolean>();
		
		for(User user : users){
			isAdmins.put(user.getLogin(), userService.UserIsAdmin(user.getLogin()));
			isBlockeds.put(user.getLogin(), userService.UserIsBlocked(user.getLogin()));
		}

		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		model.addAttribute("users", users);
		model.addAttribute("isAdmins", isAdmins);		
		model.addAttribute("isBlockeds", isBlockeds);
		
		return "admin";
	}

	@RequestMapping(value = "/admin/user/orders", method = RequestMethod.GET)
	public @ResponseBody Set<Orders> userOrders(@RequestParam String login){
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
	public @ResponseBody Category getCategoryById(@RequestParam Integer categoryId){
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
					ProductExtraFeatures newExtFeature = new ProductExtraFeatures();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);					
					
				}
			}
			for(String extraFeature : addNewFeatures){
				if(!p.hasFeature(extraFeature)){
					ProductExtraFeatures newExtFeature = new ProductExtraFeatures();
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
					ProductExtraFeatures newExtFeature = new ProductExtraFeatures();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);
				}
			}
			for(String extraFeature : addNewFeatures){
				if(!p.hasFeature(extraFeature)){
					ProductExtraFeatures newExtFeature = new ProductExtraFeatures();
					newExtFeature.setName(extraFeature);
					newExtFeature.setValue("");
					newExtFeature.setProduct(p);
					productExtraFeatureService.addProductExtraFeature(newExtFeature);
				}
			}
			for(String extraFeature : removeFeatures){
				if(p.hasFeature(extraFeature)){
					ProductExtraFeatures newExtFeature = p.getExtraFeatureByName(extraFeature);
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
	
	@RequestMapping(value = "/admin/getProducts", method = RequestMethod.GET)
	public @ResponseBody List<ProductWrapper> getProducts(){
		List<ProductWrapper> products = new ArrayList<ProductWrapper>();
		
		for(Product p : productService.listProducts()){
			ProductWrapper pw = new ProductWrapper();
			pw.setProduct(p);
			pw.setCategory(p.getCategory());
			products.add(pw);
		}
		return products;
	}
	
	@RequestMapping(value = "/admin/getProduct", method = RequestMethod.GET)
	public @ResponseBody ProductWrapper getProductById(@RequestParam Integer productId){
		ProductWrapper pw = new ProductWrapper();
		Product p = productService.findById(productId);
		pw.setProduct(p);
		pw.setCategory(p.getCategory());
		return pw;
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
			ProductExtraFeatures newExtraFeature = new ProductExtraFeatures();
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
			editedProduct.setCategory(newCat); //check if works
		}
	}
	
	@RequestMapping(value = "/admin/removeProduct", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeProduct(@RequestParam("productId") Integer productId){
		Product p = productService.findById(productId);
		if(p.getOrders().size() == 0 && p.getUsers().size() == 0){
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
	
	@RequestMapping(value = "/admin/getOrders", method = RequestMethod.GET)
	public @ResponseBody List<OrderWrapper> getOrders(HttpServletRequest request, HttpServletResponse response){
		OrderWrapper ord = new OrderWrapper();
		List<OrderWrapper> list = new ArrayList<OrderWrapper>();
		
		for(Orders o : orderService.listOrders()){
			ord.setOrder(o);
			ord.setUser(o.getUser());
			list.add(ord);
		}
		
		return list;
	}
	
	@RequestMapping(value = "/admin/changeOrderStatus", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeOrderStatus(@RequestParam("orderId") int orderId, @RequestParam("status") String status){
		orderService.changeStatus(orderId, status);
	}
}




