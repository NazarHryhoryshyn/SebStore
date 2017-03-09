package ua.sombra.webstore.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderController {
	 @RequestMapping(value = "/order", method = RequestMethod.GET)
	    public String makeOrder(Model model) {
	        return "order";
	    }
}
