package ua.sombra.webstore.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.sombra.webstore.domain.Category;
import ua.sombra.webstore.domain.Photo;
import ua.sombra.webstore.domain.Product;
import ua.sombra.webstore.domain.User;
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
		Set<String> catNames = new HashSet<String>();
		Map<Integer, Integer> productPhoto = new HashMap<Integer, Integer>();
		Set<Product> popularProducts = new HashSet<Product>();
		int prodSize = 0;
		int amountProducts = 0;
		
		for(Category c : categoryService.listTopCategories()){
			catNames.add(c.getName());
		}
		
		List<Product> prods = productService.listProducts();
		
		prodSize = prods.size();
		
		for(int i = 0; i < prodSize || i < 12; i++){
			popularProducts.add(prods.get(i));
			Iterator<Photo> iter = prods.get(i).getPhotos().iterator();
			productPhoto.put(prods.get(i).getId(), iter.next().getId());
		}
		
		amountProducts = popularProducts.size();

		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		model.addAttribute("categories", catNames);
		model.addAttribute("popularProducts", popularProducts);
		model.addAttribute("amountProducts", amountProducts);
		model.addAttribute("productPhoto", productPhoto);
		
		return "mainPage";
	}
}
