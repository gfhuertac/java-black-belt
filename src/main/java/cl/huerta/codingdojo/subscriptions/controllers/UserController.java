package cl.huerta.codingdojo.subscriptions.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.huerta.codingdojo.subscriptions.models.User;
import cl.huerta.codingdojo.subscriptions.services.UserService;
import cl.huerta.codingdojo.subscriptions.validators.UserValidator;

@Controller
public class UserController {
    
    private final UserService userService;
    
    private final UserValidator userValidator;
    
    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }
    
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String login(
    		@RequestParam(value="error", required=false) String error,
    		@RequestParam(value="logout", required=false) String logout, 
    		Model model,
    		Principal principal
    ) {
    	if (principal != null) {
    		String email = principal.getName();
    		User user2 = this.userService.findByEmail(email);
    		if (user2 != null)
	    		if (user2.getRoles().stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName())))
	    			return "redirect:/packages";
	    		else
	    			return "redirect:/users/" + user2.getId();
    	}
    	if(!model.containsAttribute("user")) model.addAttribute("user", new User());

    	if(error != null) {
            model.addAttribute("error", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logout", "Logout Successful!");
        }
        return "registrationAndLoginPage.jsp";
    }
    
    @RequestMapping(value="/", method=RequestMethod.POST)
    public String registerUser(
    		@Valid @ModelAttribute("user") User user,
    		BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
    	userValidator.validate(user, result);
    	// if result has errors, return the main page
    	if (result.hasErrors()) {
    		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", result);
            redirectAttributes.addFlashAttribute("user", user);
    		return "redirect:/";
    	}
        // else, save the user in the database, save the user id in session, and redirect them to the appropriate route
    	else {
    		if (this.userService.countUsers() == 0)
        		user = this.userService.registerAdmin(user);
    		else
    			user = this.userService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", "Registration complete. Please login.");
    		return "redirect:/"; 
    	}
    }
    
}