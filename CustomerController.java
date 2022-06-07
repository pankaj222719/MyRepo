package com.synechron.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.entity.Customers;
import com.synechron.entity.CustomersOrders;
import com.synechron.service.CustomerService;
@RestController
@RequestMapping("/customerApi")
public class CustomerController {
	public CustomerController() {
	}
	@Autowired
	private CustomerService customerService;
	@GetMapping("/customers/{custId}")
	public ResponseEntity<Customers> fetchCustomer(@PathVariable("custId") int custId){
		Customers customer=customerService.fetchCustomerService(custId);
		ResponseEntity<Customers> response=null;
		if(customer.getCustId()!=0){
		response=new ResponseEntity<Customers>(customer,HttpStatus.FOUND);
		}else{
		response=new ResponseEntity<Customers>(customer,HttpStatus.NOT_FOUND);
		}
		return response;
	}
	@GetMapping("/orders/{custId}")
	public ResponseEntity<String> fetchOrder(@PathVariable("custId") int custId){
		String orderId=customerService.fetchOrder(custId);
		ResponseEntity<String> response=null;
		if(orderId!=null){
		response=new ResponseEntity<String>(orderId,HttpStatus.FOUND);
		}else{
		response=new ResponseEntity<String>(orderId,HttpStatus.NOT_FOUND);
		}
		return response;
	}
	@PostMapping("/customers")
	public ResponseEntity<String> placeOrder(@RequestBody CustomersOrders customersOrders){
		boolean result=
				customerService.registerOrder(customersOrders.getCustId(), customersOrders.getOrderId());
		ResponseEntity<String> response=null;
		if(result) {
			response=new ResponseEntity<String>("DONE",HttpStatus.CREATED);
		}else {
			response=new ResponseEntity<String>("NOT DONE",HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	@RequestMapping(value = "/")
    public String home() {
      return "home";
    }
	
}
