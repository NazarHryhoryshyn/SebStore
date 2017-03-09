package ua.sombra.webstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.sombra.webstore.service.CategoryService;
import ua.sombra.webstore.service.OrderService;
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
	private SecurityService securityService;
	
	@Autowired
	private OrderService orderService;
	
	 @RequestMapping(value = "/admin", method = RequestMethod.GET)
	    public String admin(Model model) {
	        return "admin";
	    }
	 
	 @RequestMapping(value = "/admin/order/{orderId}", method = RequestMethod.GET)
	    public String order(Model model, @PathVariable("orderId") Integer orderId) {
	        return "orderFullInfo";
	    }
}
