package com.example.Customers.Controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Customers.Exception.*;
import com.example.Customers.Model.Customer;
import com.example.Customers.Service.CustomerService;

@RestController
public class CustomersController {

	@Autowired
	private CustomerService customerService;
	
	//Get all Customers
	@GetMapping("/Customers")
	public List<Customer> getAllCustomers(){
		List<Customer> Customers=customerService.getAllCustomers();
		if(Customers.isEmpty()){
			throw new ResourceNotFoundException("No Customer Found.");
		}
		return Customers;
	}
	
	//Get Customers by Id
	@GetMapping("/Customers/{id}")
	public Customer getCustomerById(@PathVariable("id") Long id) {
		return customerService.getCustomerById(id).orElseThrow(()->new ResourceNotFoundException("No Customer found for Id: "+id));
	}
	
	//Create a new Customer
	@PostMapping("/Customers")
	public Customer createCustomer(@Valid @RequestBody Customer customerRequest) {
		return customerService.createNewCustomer(customerRequest);
	 
	}
	//update a new customer by id 
	@PutMapping("/Customers/{id}")
	public Customer updateCustomerById(@PathVariable("id") Long id,@Valid @RequestBody Customer customerRequest)
	{
		return customerService.updateCustomerById(id, customerRequest)
				.orElseThrow(()->new ResourceNotFoundException("Customer Not found with id:"+id));
	}
	
	//delete a customer by id
	@DeleteMapping("/Customers/{id}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("id") Long id){
		try {
		 customerService.deleteCustomerById(id);
		}
		catch(Exception ex) {
			throw new ResourceNotFoundException("Customer Not found with id:"+id);
			}
	
		return ResponseEntity.ok().build();

	}
}
