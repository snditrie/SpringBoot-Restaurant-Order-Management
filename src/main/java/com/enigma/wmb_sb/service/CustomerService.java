package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.dto.request.NewCustomerRequest;
import com.enigma.wmb_sb.model.dto.request.SearchCustomerRequest;
import com.enigma.wmb_sb.model.dto.response.SearchCustomerResponse;
import com.enigma.wmb_sb.model.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    SearchCustomerResponse create(NewCustomerRequest request);
    SearchCustomerResponse getById(String id);
    Customer entityById(String id);
    Page<Customer> getAll(SearchCustomerRequest request);
    SearchCustomerResponse update(SearchCustomerRequest customer);
    void deleteById(String id);
    void updateStatusById(String id, Boolean status);
}
