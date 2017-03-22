package ua.sombra.webstore.web;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.sombra.webstore.entity.Product;
import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.service.databaseService.interfaces.CategoryService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;

@Controller
public class MainPageController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = { "/", }, method = RequestMethod.GET)
	public String mainPage(Model model) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		Set<String> catNames = categoryService.listTopCategoriesNames();
		Set<Product> popularProducts = productService.getPopularProducts();
		Map<Integer, Integer> productPhoto = productService.getMapProductPhotos(popularProducts);
		
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		model.addAttribute("categories", catNames);
		model.addAttribute("popularProducts", popularProducts);
		model.addAttribute("amountProducts", popularProducts.size());
		model.addAttribute("productPhoto", productPhoto);
		
		return "mainPage";
	}
}
