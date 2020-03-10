package com.example.demo.controller;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(value = "Customer Management System", tags = {"Customer API"})
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation(value = "View a list of available customers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getCustomers() {
        return new ResponseEntity<List<Customer>>(customerService.getCustomer(), HttpStatus.OK);
    }

    @ApiOperation(value = "Insert a new Customer")
    @PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> createCustomer(@ApiParam(value = "Customer object store in database table", required = true)
                                                      @RequestBody CustomerDTO customerRequest) {
        return new ResponseEntity<CustomerDTO>(customerService.create(customerRequest), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a Customer")
    @DeleteMapping(value = "/customers/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId){
        return new ResponseEntity<>(customerService.delete(customerId),HttpStatus.OK);
    }

    @ApiOperation(value = "Update a Customer")
    @PutMapping(value = "customers/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long customerId, @ApiParam(value = "Customer object to be updated in database table", required = true) @RequestBody CustomerDTO customerDTO){
        return new ResponseEntity<CustomerDTO>(customerService.update(customerId,customerDTO),HttpStatus.OK);
    }

}
