package ua.sombra.webstore.web;

import java.util.Map;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }
    
    @RequestMapping(value = { "/profile", }, method = RequestMethod.GET)
	public String profile(Model model) {
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
		model.addAttribute("isAdmin", securityService.currUserIsAdmin());
		model.addAttribute("user", u);

		
		return "profile";
	}
    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

    	userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.addUser(userForm);

        securityService.autoLogin(userForm.getLogin(), userForm.getConfirmPassword());

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Login or password is incorrect.");
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
	
	@RequestMapping(value = "/profile/changefname", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeFirstName(@RequestParam("firstname") String firstname){
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		u.setFirstname(firstname);
		userService.editUser(u);
	}
	
	@RequestMapping(value = "/profile/changelname", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeLastName(@RequestParam("lastname") String lastname){
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		u.setLastname(lastname);
		userService.editUser(u);
	}
	
	@RequestMapping(value = "/profile/changeEmail", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeEmail(@RequestParam("email") String email){
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		u.setEmail(email);
		userService.editUser(u);
	}
	
	@RequestMapping(value = "/profile/changeTelephone", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeTelephone(@RequestParam("telephone") String telephone){
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		u.setTelephone(telephone);
		userService.editUser(u);
	}
	
	@RequestMapping(value = "/profile/changeSex", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeSex(@RequestParam("sex") String sex){
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		u.setSex(sex);
		userService.editUser(u);
	}
	
	@RequestMapping(value = "/profile/changePassword", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword){
		User u = userService.findByLogin(securityService.findLoggedInLogin());
		if(bCryptPasswordEncoder.matches(oldPassword, bCryptPasswordEncoder.encode(u.getPassword()))){
			u.setPassword(bCryptPasswordEncoder.encode(newPassword));
		}		
		userService.editUser(u);
	}
}
