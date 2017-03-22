package ua.sombra.webstore.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import ua.sombra.webstore.validator.UserValidator;
import ua.sombra.webstore.entity.User;
import ua.sombra.webstore.service.databaseService.interfaces.SecurityService;
import ua.sombra.webstore.service.databaseService.interfaces.UserService;

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
	
    @RequestMapping(value = { "/profile", }, method = RequestMethod.GET)
   	public String profile(Model model) {
   		User u = userService.findByLogin(securityService.findLoggedInLogin());
   		model.addAttribute("uname", u.getLastname() + " " + u.getFirstname());
   		model.addAttribute("isAdmin", userService.currUserIsAdmin());
   		model.addAttribute("user", u);
   		
   		return "profile";
   	}
    
	@RequestMapping(value = "/profile/changefname", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeFirstName(@RequestParam("firstname") String firstname){
		userService.changeFirstName(firstname);
	}
	
	@RequestMapping(value = "/profile/changelname", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeLastName(@RequestParam("lastname") String lastName){
		userService.changeLastName(lastName);
	}
	
	@RequestMapping(value = "/profile/changeEmail", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeEmail(@RequestParam("email") String email){
		userService.changeEmail(email);
	}
	
	@RequestMapping(value = "/profile/changeTelephone", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeTelephone(@RequestParam("telephone") String telephone){
		userService.changeTelephone(telephone);
	}
	
	@RequestMapping(value = "/profile/changeSex", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changeSex(@RequestParam("sex") String sex){
		userService.changeSex(sex);
	}
	
	@RequestMapping(value = "/profile/changePassword", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword){
		userService.changePassword(oldPassword, newPassword);
	}
}
