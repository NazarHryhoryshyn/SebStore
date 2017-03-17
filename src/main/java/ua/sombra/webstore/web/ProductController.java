package ua.sombra.webstore.web;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.ProductExtraFeatures;
import ua.sombra.webstore.domain.User;
import ua.sombra.webstore.service.CategoryService;
import ua.sombra.webstore.service.ProductService;
import ua.sombra.webstore.service.SecurityService;
import ua.sombra.webstore.service.UserService;

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

	@RequestMapping(value = { "/products/{categoryName}", }, method = RequestMethod.GET)
	public String getProductsByCategory(Model model, @PathVariable("categoryName") String categoryName) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		
		Set<String> catNames = new HashSet<String>();
		
		for(Category c : categoryService.listTopCategories()){
			catNames.add(c.getName());
		}
		
		model.addAttribute("categories", catNames);
		
		Set<Product> products = new HashSet<Product>();
		
		if(!categoryName.equals("allCategory")){
			 products = categoryService.findByName(categoryName).getProducts();
		}
		
		int amountProducts = products.size();
		
		model.addAttribute("popularProducts", products);
		model.addAttribute("amountProducts", amountProducts);
		
		return "products";
	}
	
	@RequestMapping(value = { "/product/{id}", }, method = RequestMethod.GET)
	public String productInfo(Model model, @PathVariable("id") int productId) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		
		Product p = productService.findById(productId);
		Set<Integer> photos = new HashSet<Integer>();
		
		for(Photo ph : p.getPhotos()){
			photos.add(ph.getId());
		}

		model.addAttribute("product", p);
		model.addAttribute("photos", photos);
		model.addAttribute("amountPhotos", photos.size());
		
		return "product";
	}
	
	@RequestMapping(value = "/product/addToCart", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addProductToCart(@RequestParam("productId") int productId){
		userService.addProduct( userService.findByLogin(securityService.findLoggedInLogin()).getId(), productId);
	}
}
