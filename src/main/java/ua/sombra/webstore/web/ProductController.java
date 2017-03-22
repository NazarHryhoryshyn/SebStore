package ua.sombra.webstore.web;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.service.databaseService.interfaces.CategoryService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;
import ua.sombra.webstore.service.paging.PageMaker;

@Controller
public class ProductController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = { "/products/{categoryName}-{page}-{name}-{prodMaker}-{minPrice}-{maxPrice}", }, method = RequestMethod.GET)
	public String getProductsByCategory(Model model, @PathVariable("categoryName") String categoryName
			, @PathVariable("page") int page
			, @PathVariable("name") String prodName
			, @PathVariable("prodMaker") String prodMaker
			, @PathVariable("minPrice") int minPrice
			, @PathVariable("maxPrice") int maxPrice
			) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		List<String> catNames = categoryService.getAllTreeWithThisCategory(categoryName);
		Set<Product> catProducts = categoryService.getAllProductsFromCategory(categoryName);		
		
		catProducts = categoryService.filterProducts(catProducts, prodName, prodMaker, minPrice, maxPrice);
		
		PageMaker<Product> pgMaker = new PageMaker<Product>(catProducts);
		int totalPages = pgMaker.totalPages();
		int block = pgMaker.getBlock(page);
		Set<Product> pageProducts = pgMaker.getFromPage(page);
		
		Map<Integer, Integer> photos = productService.getMapProductPhotos(pageProducts);
		
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		model.addAttribute("categories", catNames);
		model.addAttribute("currentCategory", categoryName);
		model.addAttribute("products", pageProducts);
		model.addAttribute("photos", photos);
		model.addAttribute("amountProducts", pageProducts.size());
		model.addAttribute("page", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("block", block);
		
		return "products";
	}
	
	@RequestMapping(value = { "/product/{id}", }, method = RequestMethod.GET)
	public String productInfo(Model model, @PathVariable("id") int productId) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		Product p = productService.findById(productId);
		Set<Integer> photos = productService.getIdProductPhotos(p);

		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		model.addAttribute("product", p);
		model.addAttribute("photos", photos);
		model.addAttribute("amountPhotos", photos.size());
		model.addAttribute("features", p.getProductExtraFeatures());
		
		return "product";
	}
	
	@RequestMapping(value = "/product/addToCart", method = RequestMethod.POST)
	public @ResponseBody boolean addProductToCart(@RequestParam("productId") int productId){
		return userService.addProductToCurrentUserCart(productId);
	}
}