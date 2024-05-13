package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.model.dto.request.SearchCustomerRequest;
import com.enigma.wmb_sb.model.entity.Customer;
import com.enigma.wmb_sb.repository.CustomerRepository;
import com.enigma.wmb_sb.service.CustomerService;
import com.enigma.wmb_sb.specification.CustomerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Customer> getAll(SearchCustomerRequest request) {
        Specification<Customer> customerSpecification = CustomerSpecification.getSpecification(request);
        if(request.getName() == null && request.getPhone() == null && request.getMemberStatus() == null){
            return customerRepository.findAll();
        }
        return customerRepository.findAll(customerSpecification);
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

    @Override
    public void updateStatusById(String id, Boolean status) {
        findByIdOrThrowNotFound(id);
        customerRepository.updateStatus(id, status);
    }

    public Customer findByIdOrThrowNotFound(String id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("customer not found"));
    }
}
