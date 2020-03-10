package com.example.demo.service;


import com.example.demo.dto.CustomerDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class CustomerServiceimplTest {

    @Mock //Value yang mau di Mock
    private CustomerRepository customerRepository;

    @InjectMocks // Apa yang mau kita inject
    private CustomerServiceimpl customerServiceimpl;

    private String STRING = "STRING";
    private Integer INTEGER = 0;
    private Long LONG = 3L;


    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCustomer_CalledFromCustomerController_GetAllCustomerDataFromDatabase(){

        List<Customer> customers = new ArrayList<>();

        when(this.customerRepository.findAll()).thenReturn(customers);
        List<Customer> result = customerServiceimpl.getCustomer();
        verify(this.customerRepository).findAll();

        assertEquals(result,customers);
    }

    @Test
    public void delete_HavingCustomerIdFromURL_DataSuccessfullyDeletedFromDatabase(){
        Customer customer = new Customer();
        when(this.customerRepository.existsById(LONG)).thenReturn(true);

        ResponseEntity<?> result = customerServiceimpl.delete(LONG);

        verify(this.customerRepository).existsById(LONG);
        verify(this.customerRepository).deleteById(LONG);
        assertEquals(result, ResponseEntity.ok().build());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void delete_HavingCustomerIdFromURL_DataIsNotDeletedBecauseNotFoundInDatabase() {
        Long ID = -1L;
        when(this.customerRepository.existsById(ID)).thenReturn(false);

        try {
            customerServiceimpl.delete(ID);
        }
        catch (ResourceNotFoundException e) {
            verify(this.customerRepository).existsById(ID);
            throw e;
        }
    }

    @Test
    public void update_HavingCustomerIdFromURLandDataFromUser_DataInDatabaseUpdated(){
        Long ID = 3L;
        CustomerDTO customerDTO = CustomerDTO.builder()
                .age(INTEGER)
                .name(STRING)
                .build();
        Customer customer = Customer.builder()
                .age(customerDTO.getAge())
                .name(customerDTO.getName())
                .id(ID)
                .build();
        when(this.customerRepository.findById(ID)).thenReturn(Optional.of(customer));

        customerServiceimpl.update(ID,customerDTO);

        verify(customerRepository).findById(ID);
        verify(customerRepository).save(customer);

    }

    @Test(expected = ResourceNotFoundException.class)
    public void update_HavingCustomerIdFromURLandDataFromUser_DataIsNotUpdatedBecauseNotFoundInDatabase(){
        Long ID = -1L;
        CustomerDTO customerDTO = CustomerDTO.builder()
                .age(INTEGER)
                .name(STRING)
                .build();
        Customer customer = Customer.builder()
                .age(customerDTO.getAge())
                .name(customerDTO.getName())
                .id(ID)
                .build();
        when(this.customerRepository.findById(ID)).thenThrow( new ResourceNotFoundException("Customer not found with id "+ID));

        try {
            customerServiceimpl.update(ID, customerDTO);
        }catch(ResourceNotFoundException e) {
            verify(customerRepository).findById(ID);
            throw e;
        }

    }

    @Test
    public void create_HavingCustomerDTOFromCustomerController_SaveDataToDatabase() {

        CustomerDTO customerDTO = CustomerDTO.builder()
                .name(STRING)
                .age(INTEGER)
                .build();

        Customer customer = Customer.builder()
                .name(customerDTO.getName())
                .age(customerDTO.getAge())
                .build();

        CustomerDTO result = customerServiceimpl.create(customerDTO);
        verify(this.customerRepository).save(customer);

        assertEquals(result, customerDTO);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(this.customerRepository);
    }

}
