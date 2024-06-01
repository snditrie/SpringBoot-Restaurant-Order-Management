package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.dto.request.UpdateCustomerRequest;
import com.enigma.wmb_sb.model.dto.request.SearchCustomerRequest;
import com.enigma.wmb_sb.model.dto.response.CustomerResponse;
import com.enigma.wmb_sb.model.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponse create(Customer customer);
    CustomerResponse getById(String id);
    Customer entityById(String id);
    Page<Customer> getAll(SearchCustomerRequest request);
    CustomerResponse update(UpdateCustomerRequest request);
    void deleteById(String id);
}
