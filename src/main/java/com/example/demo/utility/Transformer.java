package com.example.demo.utility;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.model.Customer;
import org.springframework.beans.BeanUtils;

public class Transformer {
    public static CustomerDTO constructDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }
}
