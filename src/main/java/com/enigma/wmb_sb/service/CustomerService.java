package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);
    Customer getById(String id);
    List<Customer> getAll();
    Customer update(Customer customer);
    void deleteById(String id);
    void updateStatusById(String id, Boolean memberStatus);
}
