package cl.huerta.codingdojo.subscriptions.controllers;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cl.huerta.codingdojo.subscriptions.models.Package;
import cl.huerta.codingdojo.subscriptions.models.User;
import cl.huerta.codingdojo.subscriptions.services.CustomerService;

@Controller
@RequestMapping("/users")
public class CustomerController {
	
    private final CustomerService customerService;
    
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

	@RequestMapping(value="/{id}/edit", method = RequestMethod.POST)
    public String edit(
    		@PathVariable(name = "id") Long userid,
    		@RequestParam(name = "package") Long packageid,
    		Principal principal,
    		HttpSession session,
    		Model model
    ) throws AccessDeniedException {
        // get user from session, save them in the model and return the home page
    	String username = principal.getName();
    	if (username == null) {
    		return "redirect:/";
    	}
    	User user = this.customerService.findCustomerById(userid);
    	if (user == null) {
    		return "redirect:/";
    	}
    	if (!user.getEmail().equals(username)) {
    		throw new AccessDeniedException("Cannot see another user' profile");
    	}
    	this.customerService.setPackage(user, packageid);
    	return "redirect:/";
    }

	@RequestMapping("/{id}")
    public String index(@PathVariable(name = "id") Long userid, Principal principal, HttpSession session, Model model) throws AccessDeniedException {
        // get user from session, save them in the model and return the home page
    	String username = principal.getName();
    	if (username == null) {
    		return "redirect:/";
    	}
    	User user = this.customerService.findCustomerById(userid);
    	if (user == null) {
    		return "redirect:/";
    	}
    	if (!user.getEmail().equals(username)) {
    		throw new AccessDeniedException("Cannot see another user' profile");
    	}
    	session.setAttribute("user", user);
    	model.addAttribute("user", user);
    	
    	List<Package> availablePackages = customerService.findAvailablePackages(user);
    	model.addAttribute("availablePackages", availablePackages);
    	return "userPage.jsp";
    }
	
}
