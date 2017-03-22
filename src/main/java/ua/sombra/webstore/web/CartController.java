package ua.sombra.webstore.web;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;

@Controller
public class CartController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value = { "/cart", }, method = RequestMethod.GET)
	public String getCartInfo(Model model) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		BigDecimal productSumPrice = productService.getSumProductsPrices(u.getProducts());
		Map<Integer, Integer> photos = productService.getMapProductPhotos(u.getProducts());

		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		model.addAttribute("products", u.getProducts());
		model.addAttribute("photos", photos);
		model.addAttribute("productSumPrice", productSumPrice.longValueExact());
		
		return "cart";
	}
	
	@RequestMapping(value = { "/cart/delete/{productId}", }, method = RequestMethod.GET)
	public String deleteProduct(@PathVariable("productId") int productId){
		userService.removeProductFromCart(productId);
		return  "redirect: /webstore/cart";
	}
}
