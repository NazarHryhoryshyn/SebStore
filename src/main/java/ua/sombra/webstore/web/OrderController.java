package ua.sombra.webstore.web;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.service.databaseService.interfaces.OrderService;
import ua.sombra.webstore.service.databaseService.interfaces.ProductService;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;

@Controller
public class OrderController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String makeOrderPage(Model model) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		model.addAttribute("products",u.getProducts());
		BigDecimal productSumPrice = productService.getSumProductsPrices(u.getProducts());
		
		model.addAttribute("productSumPrice", productSumPrice.longValueExact());
		return "order";
	}

	@RequestMapping(value = "/admin/order/{orderId}", method = RequestMethod.GET)
	public String orderInfo(Model model, @PathVariable("orderId") Integer orderId) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", userService.currUserIsAdmin());
		model.addAttribute("order", orderService.findById(orderId));
		
		return "orderFullInfo";
	}
	
	@RequestMapping(value = "/admin/addOrder", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addOrder(@RequestParam("deliveryType") String deliveryType
			, @RequestParam("paymentType") String paymentType
			, @RequestParam("receiver") String receiver
			, @RequestParam("phone") String phone
			, @RequestParam("email") String email
			, @RequestParam("address") String address
			, @RequestParam("cardNumber") String cardNumber
			, @RequestParam("cardTreeNumber") String cardTreeNumber
			, @RequestParam("cardTermOf") String cardTermOf
			){
		orderService.makeOrder(address, receiver, paymentType, deliveryType, phone, email, cardNumber, cardTreeNumber, cardTermOf);
	}
}
