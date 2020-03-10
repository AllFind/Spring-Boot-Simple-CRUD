package com.example.demo.service;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.utility.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceimpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDTO create(CustomerDTO customerDTO) {
        Customer customer = Customer.builder().age(customerDTO.getAge()).name(customerDTO.getName()).build();
        customerRepository.save(customer);
        return customerDTO;
    }

    @Override
    public List<Customer> getCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public ResponseEntity<?> delete(Long customerid) throws ResourceNotFoundException {
        if(!customerRepository.existsById(customerid)){
            throw new ResourceNotFoundException("Customer not found with id "+customerid);
        }

        customerRepository.deleteById(customerid);
        return ResponseEntity.ok().build();
    }

    @Override
    public CustomerDTO update(Long customerid, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerid).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id "+customerid));
        customer.setName(customerDTO.getName());
        customer.setAge(customerDTO.getAge());
        customerRepository.save(customer);

        return CustomerDTO.builder()
                .name(customer.getName())
                .age(customer.getAge())
                .build();

    }

}
