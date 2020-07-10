package com.example.demo.models;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.demo.models.enums.ProductType;
@Entity
@Table(name = "ProductTable")
public class Product {
	//1.variables
	@Column(name="Title") 
	@Size(min = 3, max = 20)
	@Pattern(regexp="[a-zA-Z\\s]+$", message = "Only letters and spaces allowed")
	private String title;
	
	
	@Column(name="Price") 
	@Min(0)
	@Max(1000)
	private float price;
	
	@Column(name="ProdType")
	private ProductType type;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name="Cid")
	private Customer customer;
	
	
	
	//2.constructors
	//no-argument constructor
	public Product()
	{
		
	}
	public Product(String title, float price, ProductType type) {
		this.title = title;
		this.price = price;
		this.type  = type;
		
		
	}
	
	
	//3. getters and setters
	
	public Product(String title, float price, ProductType type, Customer customer) {
		super();
		this.title = title;
		this.price = price;
		this.type = type;
		this.customer = customer;
	}
	public String getTitle() {
		return title;
	}
	public int getId() {
		return id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
	
	public ProductType getType() {
		return type;
	}
	public void setType(ProductType type) {
		this.type = type;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	//4.toString
	public String toString()
	{
		return id +"." +title + " " + price + " eur ," + type;
		
	}
	
}
