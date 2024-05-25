package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.dto.request.NewCustomerRequest;
import com.enigma.wmb_sb.model.dto.request.SearchCustomerRequest;
import com.enigma.wmb_sb.model.dto.response.CustomerResponse;
import com.enigma.wmb_sb.model.entity.Customer;
import org.springframework.data.domain.Page;

public interface CustomerService {
    CustomerResponse create(NewCustomerRequest request);
    CustomerResponse getById(String id);
    Customer entityById(String id);
    Page<Customer> getAll(SearchCustomerRequest request);
    CustomerResponse update(String id, NewCustomerRequest request);
    void deleteById(String id);
//    void updateStatusById(String id, Boolean status);
}
