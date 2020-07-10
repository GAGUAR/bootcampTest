package com.example.demo.services.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Customer;
import com.example.demo.models.Product;
import com.example.demo.models.enums.ProductType;
import com.example.demo.repos.ICustomerRepo;
import com.example.demo.repos.IProductRepo;
import com.example.demo.services.IProductService;

@Service
public class ProductServiceImpl implements IProductService {
@Autowired
IProductRepo prodRepo;
@Autowired
ICustomerRepo custRepo;
	
	@Override
	public Product selectOneProductById(int id) throws Exception {
		if(id > 0)
		{
			/*for(int i = 0; i < allProducts.size();i++)
			{
				if(allProducts.get(i).getId() == id)
					return allProducts.get(i);
			}*/
			if(prodRepo.existsById(id)) {
			return prodRepo.findById(id).get();	
			}
		}
		
		throw new Exception("Id is not correct and there is not product with that id in System");
	}

	@Override
	public ArrayList<Product> selectAllProducts() {

		return (ArrayList<Product>) prodRepo.findAll();
	}

	@Override
	public boolean insertNewProduct(String title, float price, ProductType type) {
		/*for(int i = 0; i < allProducts.size();i++)
		{
			if(allProducts.get(i).getTitle().equals(title) && allProducts.get(i).getPrice() == price)
				return false;
		}
		
		Product prod = new Product(title, price, type);
		allProducts.add(prod);
		return true;*/
		
		if(prodRepo.existsByTitleAndPriceAndType(title, price, type)) {
			return false;
		}
		Product prod = new Product(title, price, type);
		prodRepo.save(prod);
		return true;
	}

	@Override
	public boolean insertNewProductByObject(Product product) {
		/*for(Product prod:allProducts)
		{
			if(prod.getTitle().equals(product.getTitle()) && prod.getPrice() ==  product.getPrice())
				return false;
		}
		
		Product prodNew = new Product(product.getTitle(), product.getPrice(), product.getType());
		allProducts.add(prodNew);
		return true;*/
		if(prodRepo.existsByTitleAndPriceAndType(product.getTitle(), product.getPrice(), product.getType())) {
			return false;
		}
		Product prod = new Product(product.getTitle(), product.getPrice(), product.getType());
		prodRepo.save(prod);
		return true;
		
	}

	@Override
	public boolean updateProductById(int id, String title, float price) {
		/*if(id > 0)
		{
			for(int i = 0; i < allProducts.size();i++)
			{
				if(allProducts.get(i).getId() == id)
				{
					allProducts.get(i).setTitle(title);
					allProducts.get(i).setPrice(price);
					return true;
				}
			}
		}
		
		return false;*/
		
		if(id>0) {
			if(prodRepo.existsById(id)) {
				Product productToUpdate = prodRepo.findById(id).get();
				productToUpdate.setTitle(title);
				productToUpdate.setPrice(price);
				//TYPE!!!!!!!!!!!!!!!!!!!!
				prodRepo.save(productToUpdate);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean updateProductObjectById(int id, Product product) {

		if(id>0) {
			if(prodRepo.existsById(id)) {
				Product productToUpdate = prodRepo.findById(id).get();
				productToUpdate.setTitle(product.getTitle());
				productToUpdate.setPrice(product.getPrice());
				//TYPE!!!!!!!!!!!!!!!!!!!!
				prodRepo.save(productToUpdate);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean deleteProductById(int id) {
		/*if(id > 0)
		{
			for(int i = 0; i < allProducts.size();i++)
			{
				if(allProducts.get(i).getId() == id)
				{
					allProducts.remove(i);
					return true;
				}
			}
		}
		return false;*/
		if(id>0) {
			if(prodRepo.existsById(id)) {
				prodRepo.deleteById(id);
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<Product> selectProductsWherePriceLessThan(float price) {
		ArrayList<Product> productsWithPriceThanTreshold = prodRepo.findByPriceLessThan(price);
		if(productsWithPriceThanTreshold!=null)
			return productsWithPriceThanTreshold;
		
		return new ArrayList<>();
		
		
	}

	@Override
	public void saveTestingData() {
		
		Customer cust1=new Customer("Janis", 32);
		Customer cust2=new Customer("Baiba", 54);
		custRepo.save(cust1);
		custRepo.save(cust2);
		
		prodRepo.save(new Product("bread", 1.99f, ProductType.BakeryType, cust1));
		prodRepo.save(new Product("window", 56.67f, ProductType.OtherType));
		prodRepo.save(new Product("apple", 0.56f, ProductType.OtherType, cust2));

		
	}



}
