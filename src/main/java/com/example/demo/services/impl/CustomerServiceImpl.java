package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Customer;
import com.example.demo.models.Product;
import com.example.demo.repos.ICustomerRepo;
import com.example.demo.repos.IProductRepo;
import com.example.demo.services.ICustomerService;

@Service
public class CustomerServiceImpl implements ICustomerService {

	@Autowired
	ICustomerRepo custRepo;
	@Autowired
	IProductRepo prodRepo;
	
	@Override
	public boolean register(Customer customer) {
		if(custRepo.existsByName(customer.getName())) {
			return false;
		}
		custRepo.save(new Customer(customer.getName(),customer.getAge()));
		return true;
	}

	@Override
	public ArrayList<Product> getAllPurchasedProductsByCustId(int id) throws Exception {
		if(id > 0)
		{
			if(custRepo.existsById(id)) {
			Customer cust = custRepo.findById(id).get();
			ArrayList<Product>purchasedProduct = prodRepo.findByCustomer(cust);
			return purchasedProduct;
			}
		}
		throw new Exception("Id is not correct and there is not customer with that id in System");
	}
	

	@Override
	public boolean buyProducts(Collection<Product> purchasedProducts, int id) throws Exception {
		if(id > 0)
		{
			if(custRepo.existsById(id)) {
			Customer cust = custRepo.findById(id).get();
			for (int i = 0; i < purchasedProducts.size(); i++) {
				ArrayList<Product> prodTemp = (ArrayList<Product>) purchasedProducts;
				Product prod = prodRepo.findByTitleAndPrice(prodTemp.get(i).getTitle(), prodTemp.get(i).getPrice());
				prod.setCustomer(cust);
				prodRepo.save(prod);
				return true;
			}
			}
		}
		throw new Exception("Id is not correct and there is not customer with that id in System");
	}

	@Override
	public Customer selectOneCustomerById(int id) throws Exception {
		if(id > 0)
		{
			if(custRepo.existsById(id)) {
			return custRepo.findById(id).get();	
			}
		}
		throw new Exception("Id is not correct and there is not customer with that id in System");
	}

	@Override
	public ArrayList<Customer> selectAllCustomers() {
		return (ArrayList<Customer>) custRepo.findAll();
	}

}
