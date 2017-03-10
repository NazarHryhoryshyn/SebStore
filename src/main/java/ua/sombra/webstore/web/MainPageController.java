package ua.sombra.webstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.sombra.webstore.domain.User;
import ua.sombra.webstore.service.SecurityService;
import ua.sombra.webstore.service.UserService;

@Controller
public class MainPageController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@RequestMapping(value = { "/", }, method = RequestMethod.GET)
	public String mainPage(Model model) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		System.out.println(u);
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", securityService.currUserIsAdmin());
		return "mainPage";
	}
}
