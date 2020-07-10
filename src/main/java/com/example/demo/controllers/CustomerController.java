package com.example.demo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.Customer;
import com.example.demo.models.Product;
import com.example.demo.services.ICustomerService;
import com.example.demo.services.IProductService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	ICustomerService custService;
	
	@Autowired
	IProductService prodService;
	
	@GetMapping("/showMyProducts/{id}")
	public String getShowMyProductsByCustId(@PathVariable(name = "id") int id, Model model) {
		try {
			model.addAttribute("innerObjectProd", custService.getAllPurchasedProductsByCustId(id));
			model.addAttribute("innerObjectCustName", custService.selectOneCustomerById(id).getName());
			return "show-all-customer-products-page";
		}
		
		catch (Exception e) {
			return "error";
		}
	}
	@GetMapping("/showAllCustomer") 
	public String getShowAllCustomers(Model model) {
		
		model.addAttribute("innerObject", custService.selectAllCustomers());
		return "show-all-customers-page";// show-all-products-page.html
	}
	
	@GetMapping("/insertOneCustomer")
	public String getInsertOneProduct(Customer customer)
	{
		return "insert-one-customer-page";
	}
	
	@PostMapping("/insertOneCustomer")
	public String postInsertOneProduct(@Valid Customer customer, BindingResult result)
	{
		
		
		if(result.hasErrors())
		{
			return "insert-one-customer-page";
		}
				
		custService.register(customer);
		System.out.println(customer);
		return "redirect:/customer/showAllCustomer";
		
	}
	
	@GetMapping("/buy/{id}")
	public String getBuyByCustId(@PathVariable (name = "id") int id, Model model, Customer customer) {
		try {
			model.addAttribute("innerObjectCustName", custService.selectOneCustomerById(id).getName());
			model.addAttribute("allCustomerProducts", prodService.selectAllProducts());
			return "customer-buy";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		}
	@PostMapping("/buy/{id}")
	public String postBuyByCustId(@PathVariable (name = "id") int id, Customer customer) {
		for(Product prod: customer.getAllCustomerProducts())
			System.out.println(prod.getTitle() + " " + prod.getPrice());
		
		try {
			custService.buyProducts(customer.getAllCustomerProducts(), id);
			return "redirect:/customer/showMyProducts/"+id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "error";
		}
	}
	
}
