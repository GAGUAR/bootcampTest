package com.example.demo.repos;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.models.Customer;
import com.example.demo.models.Product;
import com.example.demo.models.enums.ProductType;

public interface IProductRepo extends CrudRepository<Product, Integer> {

	boolean existsByTitleAndPriceAndType(String title, float price, ProductType type);

	ArrayList<Product> findByPriceLessThan(float priceThreshold);
	
	ArrayList<Product> findByPrice(float price);
	
	ArrayList<Product> findByCustomer(Customer customer);

	Product findByTitleAndPrice(String title, float price);
}
