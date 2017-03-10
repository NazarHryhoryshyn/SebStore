package ua.sombra.webstore.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@RequestMapping(value = { "/products", }, method = RequestMethod.GET)
	public String getProducts(Model model) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", securityService.currUserIsAdmin());
		return "products";
	}

	@RequestMapping(value = { "/products/{categoryName}", }, method = RequestMethod.GET)
	public String getProductsByCategory(Model model, @PathVariable("categoryName") String categoryName) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", securityService.currUserIsAdmin());
		return "products";
	}
	
	@RequestMapping(value = { "/product/{id}", }, method = RequestMethod.GET)
	public String productInfo(Model model, @PathVariable("id") String productId) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", securityService.currUserIsAdmin());
		return "product";
	}
}
