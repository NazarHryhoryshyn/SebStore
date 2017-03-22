package ua.sombra.webstore.web;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
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
import ua.sombra.webstore.entity.Orders;
import ua.sombra.webstore.entity.Photo;
import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.entity.wrappers.PageInfo;
import ua.sombra.webstore.entity.wrappers.ProductWrapper;
import ua.sombra.webstore.service.databaseService.interfaces.CategoryService;
import ua.sombra.webstore.service.databaseService.interfaces.OrderService;
import ua.sombra.webstore.service.databaseService.interfaces.PhotoService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private CategoryService categoryService;

	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(Model model) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		
		return "admin";
	}

	@RequestMapping(value = "/admin/getUsers-{page}", method = RequestMethod.GET)
	public @ResponseBody PageInfo<User> getUsers(@PathVariable("page") int page){
		return userService.getPageInfoUsers(page);
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
		categoryService.addCategory(name, mainCategoryId, featureNames);
	}
	
	@RequestMapping(value = "/admin/editCategory", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void editCategory(@RequestParam("name") String newName
			, @RequestParam("categoryId") Integer categoryId
			, @RequestParam("featureNames[]") List<String> featureNames
			){
		categoryService.editCategory(newName, categoryId, featureNames);
	}
	
	@RequestMapping(value = "/admin/removeCategory", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeCategory(@RequestParam("categoryId") Integer categoryId){
		categoryService.tryDeleteCategory(categoryId);
	}
	
	@RequestMapping(value = "/admin/getProducts-{page}", method = RequestMethod.GET)
	public @ResponseBody PageInfo<Product> getProducts(@PathVariable("page") int page){
		return productService.getPageInfoProducts(page);
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
		productService.addNewProduct(name, price, category, producer, country, weight, amountOnWarehouse);
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
		productService.transactionEditProduct(productId, name, price, category, producer, country, weight, amountOnWarehouse, extraFeatures);
	}
	
	@RequestMapping(value = "/admin/removeProduct", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void removeProduct(@RequestParam("productId") Integer productId){
		productService.tryRemoveProduct(productId);
	}
	
	@RequestMapping(value = "/admin/addProductPhoto/{productId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addProductPhoto(@PathVariable("productId") int productId,
			@RequestParam("file") MultipartFile file) {
		photoService.addNewPhoto(productId, file);
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
		return orderService.getPageInfoOrders(page);
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




