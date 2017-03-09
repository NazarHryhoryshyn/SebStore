package ua.sombra.webstore.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ua.sombra.webstore.service.SecurityService;
import ua.sombra.webstore.validator.UserValidator;
import ua.sombra.webstore.domain.User;
import ua.sombra.webstore.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }
    
    @RequestMapping(value = { "/profile", }, method = RequestMethod.GET)
	public String profile(Model model) {
		User u = userService.findByEmail(securityService.findLoggedInEmail());
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", securityService.currUserIsAdmin());
		return "profile";
	}
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

    	userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.addUser(userForm);

        securityService.autoLogin(userForm.getEmail(), userForm.getConfirmPassword());

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Email or password is incorrect.");
        }

        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }
    
	@RequestMapping("/users")
	public String listUsers(Map<String, Object> map) {

		map.put("user", new User());
		map.put("userList", userService.listUsers());

		return "user";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("contact") User user,
			BindingResult result) {

		userService.addUser(user);

		return "redirect:/";
	}

	@RequestMapping("/delete/{userId}")
	public String deleteUser(@PathVariable("userId") Integer userId) {

		userService.removeUser(userId);

		return "redirect:/";
	}
}
