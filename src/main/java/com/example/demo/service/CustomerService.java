package com.example.demo.service;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    CustomerDTO create(CustomerDTO customerDTO);

    List<Customer> getCustomer();

    ResponseEntity<?> delete(Long customerid) throws ResourceNotFoundException;

    CustomerDTO update(Long customerid, CustomerDTO customerDTO);
}
