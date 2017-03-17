package ua.sombra.webstore.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import org.hibernate.SessionFactory;
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
import ua.sombra.webstore.service.CategoryService;
import ua.sombra.webstore.service.FeatureService;
import ua.sombra.webstore.service.OrderService;
import ua.sombra.webstore.service.PhotoService;
import ua.sombra.webstore.service.ProductExtraFeatureService;
import ua.sombra.webstore.service.ProductService;
import ua.sombra.webstore.service.SecurityService;
import ua.sombra.webstore.service.UserService;

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
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		List<User> users = userService.listUsers();
		
		Map<String, Boolean> isAdmins = new HashMap<String, Boolean>();
		Map<String, Boolean> isBlockeds = new HashMap<String, Boolean>();
		
		for(User user : users){
			isAdmins.put(user.getLogin(), userService.UserIsAdmin(user.getLogin()));
			isBlockeds.put(user.getLogin(), userService.UserIsBlocked(user.getLogin()));
		}

		model.addAttribute("users", users);
		model.addAttribute("isAdmins", isAdmins);		
		model.addAttribute("isBlockeds", isBlockeds);
		
		return "admin";
	}

	@RequestMapping(value = "/admin/order/{orderId}", method = RequestMethod.GET)
	public String order(Model model, @PathVariable("orderId") Integer orderId) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		
		model.addAttribute("order", orderService.findById(orderId));
		
		return "orderFullInfo";
	}
	
	@RequestMapping(value = "/admin/user/orders", method = RequestMethod.GET)
	public @ResponseBody Set<Orders> userOrders(@RequestParam String login, HttpServletRequest request, HttpServletResponse response, Model model){
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
	public @ResponseBody Map<Integer, List<Category>> getTreeCategories(HttpServletRequest request, HttpServletResponse response, Model model){
		return mapCategories(categoryService.listTopCategories());
	}
	
	@RequestMapping(value = "/admin/MainCategories", method = RequestMethod.GET)
	public @ResponseBody List<Category> getMainCategories(HttpServletRequest request, HttpServletResponse response, Model model){
		return categoryService.listTopCategories();
	}
	
	@RequestMapping(value = "/admin/allCategories", method = RequestMethod.GET)
	public @ResponseBody List<Category> getAllCategories(HttpServletRequest request, HttpServletResponse response, Model model){
		return categoryService.listCategory();
	}
	
	private Map<Integer, List<Category>> mapCategories(List<Category> categs){
		Map<Integer, List<Category>> mapCategs = new HashMap<Integer, List<Category>>();
		for(Category c : categs){
			if(categoryService.listSubCategories(c.getId()).size() > 0){
				mapCategs.put(c.getId(), categoryService.listSubCategories(c.getId()));
				List<Category> catList =  categoryService.listSubCategories(c.getId());
				for (Map.Entry<Integer, List<Category>> entry :  mapCategories(catList).entrySet())
				{
					mapCategs.put(entry.getKey(), entry.getValue());
				}
			}
			else{
				mapCategs.put(c.getId(), new ArrayList<Category>());
			}
		}	
		return mapCategs;
	}
	
	@RequestMapping(value = "/admin/getCategory", method = RequestMethod.GET)
	public @ResponseBody Category getCategoryById(@RequestParam Integer categoryId, HttpServletRequest request, HttpServletResponse response, Model model){
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
		
		for(String fname: featureNames ){
			Feature f = new  Feature();
			f.setName(fname);
			featureService.addFeature(f);
			categoryService.AddFeature(newCategory, f);
			
			featureService.AddCategory(f, newCategory);
		}
	}
	
	@RequestMapping(value = "/admin/editCategory", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void editCategory(@RequestParam("name") String name
			, @RequestParam("categoryId") Integer categoryId
			, @RequestParam("featureNames[]") List<String> featureNames
			){
		if(featureNames.contains("no_elements")){
			featureNames.remove("no_elements");
		}

		Category editedCategory = categoryService.findById(categoryId);
		categoryService.rename(categoryId, name);
		
		Set<String> featsOldNames = FeatureNames(editedCategory.getFeatures());

		List<Product> productsTree =  ProductsFromTreeCategory(editedCategory);
		
		System.out.println("product tree:");
		for(Product p : productsTree){
			System.out.println("p.name : " + p.getName() + " p.category : " + p.getCategory().getName());
		}
		
		Set<String> keepFeatures = new HashSet<String>();
		Set<String> removeFeatures = new HashSet<String>();
		Set<String> addFeatures = new HashSet<String>();
		
		for(String feature : featureNames){
			if(featureService.findByName(feature) != null){
				keepFeatures.add(feature);
			}
		}
		
		for(String oldFeature : featsOldNames){
			if(!featureNames.contains(oldFeature)){
				removeFeatures.add(oldFeature);
			}
		}
		
		for(String feature : featureNames){
			if(featureService.findByName(feature) == null){
				addFeatures.add(feature);
			}
		}
				
		for(Feature f : editedCategory.getFeatures()){
			featureService.removeCategory(f, editedCategory);
		}
		
		categoryService.RemoveAllFeatures(editedCategory);
		
		for(String kFeat : keepFeatures){
			Feature f = featureService.findByName(kFeat);
			categoryService.AddFeature(editedCategory, f);
			//featureService.AddCategory(f, editedCategory);
		}

		System.out.println("in cycle addFeatures:");
		for(String addFeat : addFeatures){
			System.out.println(addFeat);
			Feature newFeat = new  Feature();
			newFeat.setName(addFeat);
			featureService.addFeature(newFeat);
			categoryService.AddFeature(editedCategory, newFeat);
			//featureService.AddCategory(newFeat, editedCategory);
		}

		for(Product p : productsTree){
			productService.addNewExtraFeatures(p, addFeatures);
			productService.removeExtraFeatures(p, removeFeatures);
		}
		
		System.out.println("in cycle removeFeatures:");
		for(String removeFeat : removeFeatures){
			System.out.println(removeFeat);
			Feature removingFeat = featureService.findByName(removeFeat);
			featureService.removeCategory(removingFeat, editedCategory);
			categoryService.RemoveFeature(editedCategory, removingFeat);
			featureService.removeFeature(removingFeat.getId());            //fix the problem can not delete a feature
		}
		
	}
	
	//
	private Set<String> FeatureNames(Set<Feature> feats){
		Set<String> names = new HashSet<String>();
		for(Feature p : feats){
			names.add(p.getName());
		}
		return names;
	}
	
	//
	private List<Product> ProductsFromTreeCategory (Category category){
		List<Product> products = new ArrayList<Product>();
		System.out.println("category.name : " + category.getName());
		System.out.println("categoryService.listSubCategories(category.getId()).size() : " 
				+ categoryService.listSubCategories(category.getId()).size());
		
		if(categoryService.listSubCategories(category.getId()).size() > 0){
			List<Category> catList =  categoryService.listSubCategories(category.getId());
			
			for (Category cat :  catList)
			{
				for(Product p : ProductsFromTreeCategory(cat)){
					System.out.println("catList.p : " + p.getName());
					products.add(p);
				}
			}
		}
			else{
				for(Product p : category.getProducts()){
					products.add(p);
				}
			}
		return products;
	}
	
	@RequestMapping(value = "/admin/removeCategory", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeCategory(@RequestParam("categoryId") Integer categoryId){
		Category cat = categoryService.findById(categoryId);
		
		if(categoryService.listSubCategories(categoryId).size() == 0 && cat.getProducts().size() == 0){
			categoryService.removeCategory(categoryId);
		}
	}
	
	@RequestMapping(value = "/admin/getProducts", method = RequestMethod.GET)
	public @ResponseBody List<ProductWrapper> getProducts(HttpServletRequest request, HttpServletResponse response){
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
	public @ResponseBody ProductWrapper getProductById(@RequestParam Integer productId, HttpServletRequest request, HttpServletResponse response, Model model){
		
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
		
		Set<String> featureNames = categoryService.listTreeFeaturesToTop(cat.getId());
		
		for(String f : featureNames){
			System.out.println("f : " + f);
		}
		
		productService.addNewExtraFeatures(newProduct, featureNames);
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
			//, @RequestParam("extraFeatures[]") List<String> extraFeatures
			){
		
		Map<String, String> efNameValue = new HashMap<String, String>();
		
//		for(String efName : extraFeatures){
//			String[] nameValue = efName.split("__");
//			if(nameValue[1] == "null_null"){
//				nameValue[1] = "";
//			}
//			efNameValue.put(nameValue[0], nameValue[1]);
//		}
		
		productExtraFeatureService.setExtraFeatures(productId, efNameValue);
		
		Product editedProduct = productService.findById(productId);
		editedProduct.setName(name);
		editedProduct.setPrice(price);
		editedProduct.setProducer(producer);
		editedProduct.setCountry(country);
		editedProduct.setWeight(weight);
		editedProduct.setAmountOnWarehouse(amountOnWarehouse);

		productService.editProduct(productId, editedProduct);
		
		Category oldCat = editedProduct.getCategory();
		Category newCat = categoryService.findByName(category);

		if(!oldCat.getName().equals(newCat.getName())){
			productService.setCategory(editedProduct, newCat);
		}
	}
	
	@RequestMapping(value = "/admin/removeProduct", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeProduct(@RequestParam("productId") Integer productId){
		Product p = productService.findById(productId);
		if(p.getOrders().size() == 0){
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
		
		//productService.removePhoto(photoService.findById(photoId).getProduct(), photoService.findById(photoId));
				photoService.removePhoto(photoId);
//				
//				Photo newPhoto = new Photo();
//					newPhoto.setData(file.getBytes());
//					newPhoto.setProduct(prod);
//					photoService.addPhoto(newPhoto);
	}
	
	@RequestMapping(value = "/admin/product/photo", method = RequestMethod.GET)
	  public void showImage(@RequestParam("id") Integer itemId, HttpServletResponse response,HttpServletRequest request) 
	          throws ServletException, IOException{

	    Photo photo = photoService.findById(itemId);        
	    response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(photo.getData());
	    
	    response.getOutputStream().close();
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
		Orders ord = orderService.findById(orderId);
		ord.setStatus(status);
		orderService.updateOrder(ord);
	}
}




