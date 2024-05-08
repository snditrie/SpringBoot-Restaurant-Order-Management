package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.entity.Customer;
import com.enigma.wmb_sb.repository.CustomerRepository;
import com.enigma.wmb_sb.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public Customer getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer update(Customer customer) {
        findByIdOrThrowNotFound(customer.getId());
        return customerRepository.saveAndFlush(customer);
    }

    @Override
    public void deleteById(String id) {
        Customer customerToDelete = findByIdOrThrowNotFound(id);
        customerRepository.delete(customerToDelete);
    }

//    @Override
//    public void updateStatusById(String id, Boolean memberStatus) {
//        findByIdOrThrowNotFound(id);
//        customerRepository.up
//    }

    public Customer findByIdOrThrowNotFound(String id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("customer not found"));
    }
}
