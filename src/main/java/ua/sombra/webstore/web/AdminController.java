package ua.sombra.webstore.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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

import ua.sombra.webstore.domain.Orders;
import ua.sombra.webstore.domain.User;
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
}






