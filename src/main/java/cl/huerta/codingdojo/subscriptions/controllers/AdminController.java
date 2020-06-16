package cl.huerta.codingdojo.subscriptions.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.huerta.codingdojo.subscriptions.models.Package;
import cl.huerta.codingdojo.subscriptions.models.User;
import cl.huerta.codingdojo.subscriptions.services.CustomerService;
import cl.huerta.codingdojo.subscriptions.services.PackageService;

@Controller
public class AdminController {
	
    private final PackageService packageService;
    private final CustomerService customerService;
    
    public AdminController(PackageService packageService, CustomerService customerService) {
    	this.packageService = packageService;
        this.customerService = customerService;
    }

	@RequestMapping(value = "/packages", method = RequestMethod.GET)
    public String index(Model model) {
    	if(!model.containsAttribute("pkg")) model.addAttribute("pkg", new Package());

    	List<User> customers = this.customerService.findAll();
    	model.addAttribute("customers", customers);
    	List<Package> packages = this.packageService.findAll();
    	model.addAttribute("packages", packages);
    	
    	return "admin.jsp";
    }

	@RequestMapping(value="/packages", method = RequestMethod.POST)
    public String create(
    		@Valid @ModelAttribute("pkg") Package pkg,
    		BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
    	// if result has errors, return the main page
    	if (result.hasErrors()) {
    		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.pkg", result);
            redirectAttributes.addFlashAttribute("pkg", pkg);
    		return "redirect:/packages";
    	}
        // else, save the user in the database, save the user id in session, and redirect them to the appropriate route
    	this.packageService.registerPackage(pkg);
    	return "redirect:/packages";
    }

	@RequestMapping("/packages/{id}")
    public String details(@PathVariable(name = "id") Long packageid, Model model) {
    	Package pkg = this.packageService.findById(packageid);
    	model.addAttribute("pkg", pkg);
    	return "packageEditPage.jsp";
    }

	@RequestMapping(value="/packages/{id}/edit", method = RequestMethod.POST)
    public String edit(
    		@PathVariable(name = "id") Long packageid,
    		@RequestParam(name = "isActive", required = false) Boolean isActive,
    		@RequestParam(name = "cost", required = false) Float cost
    ) {
        // get user from session, save them in the model and return the home page
    	Package pkg = this.packageService.findById(packageid);
    	if (isActive != null)
    		pkg.setIsAvailable(isActive);
    	if (cost != null)
    		pkg.setCost(cost);
    	this.packageService.registerPackage(pkg);
    	return "redirect:/packages";
    }

	@RequestMapping(value="/packages/{id}/delete", method = RequestMethod.POST)
    public String delete(
    		@PathVariable(name = "id") Long packageid
    ) {
        // get user from session, save them in the model and return the home page
    	Package pkg = this.packageService.findById(packageid);
    	this.packageService.remove(pkg);
    	return "redirect:/packages";
    }
	
}
