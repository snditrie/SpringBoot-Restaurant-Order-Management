package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.request.NewCustomerRequest;
import com.enigma.wmb_sb.model.dto.request.SearchCustomerRequest;
import com.enigma.wmb_sb.model.dto.response.CustomerResponse;
import com.enigma.wmb_sb.model.entity.Customer;
import com.enigma.wmb_sb.repository.CustomerRepository;
import com.enigma.wmb_sb.service.CustomerService;
import com.enigma.wmb_sb.specification.CustomerSpecification;
import com.enigma.wmb_sb.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    @Override
    public CustomerResponse create(NewCustomerRequest request) {
        validationUtil.validate(request);

        if(customerRepository.existsByPhoneNumber(request.getPhone())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_ALREADY_EXIST);
        }

        Customer newCustomer = Customer.builder()
                .name(request.getName())
                .phoneNumber(request.getPhone())
                .isMember(request.getMemberStatus())
                .build();
        return CustomerResponse.builder()
                .id(newCustomer.getId())
                .name(newCustomer.getName())
                .mobilePhone(newCustomer.getPhoneNumber())
                .isMember(newCustomer.getIsMember())
                .build();
    }

    @Override
    public Customer entityById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public CustomerResponse getById(String id) {
        Customer customerFound = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
        return CustomerResponse.builder()
                .id(customerFound.getId())
                .name(customerFound.getName())
                .mobilePhone(customerFound.getPhoneNumber())
                .isMember(customerFound.getIsMember())
                .build();
    }

    @Override
    public Page<Customer> getAll(SearchCustomerRequest request) {
        if(request.getPage() <= 0) {
            request.setPage(1);
        }

        String validSortBy;
        if("name".equalsIgnoreCase(request.getSortBy()) || "isMember".equalsIgnoreCase(request.getSortBy())) {
            validSortBy = request.getSortBy();
        } else {
            validSortBy = "name";
        }
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), validSortBy);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

        if (request.getName() == null &&
            request.getPhone() == null &&
            request.getMemberStatus() == null) {

            return customerRepository.findAll(pageable);
        } else {
            Specification<Customer> customerSpecification = CustomerSpecification.getSpecification(request);
            Page<Customer> customersFound = customerRepository.findAll(customerSpecification, pageable);
            if (customersFound.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            }
            return customersFound;
        }

    }

    @Override
    public CustomerResponse update(String id, NewCustomerRequest request) {
        validationUtil.validate(request);
        Customer updateCustomer = entityById(id);

        if(request.getName() != null) {
            updateCustomer.setName(request.getName());
        }

        if(request.getPhone() != null) {
            updateCustomer.setPhoneNumber(request.getPhone());
        }

        if(request.getMemberStatus() != null) {
            updateCustomer.setIsMember(request.getMemberStatus());
        }

        customerRepository.saveAndFlush(updateCustomer);
        return CustomerResponse.builder()
                .id(updateCustomer.getId())
                .name(updateCustomer.getName())
                .mobilePhone(updateCustomer.getPhoneNumber())
                .isMember(updateCustomer.getIsMember())
                .build();
    }

    @Override
    public void deleteById(String id) {
        Customer customerToDelete = findByIdOrThrowNotFound(id);
        customerRepository.delete(customerToDelete);
    }

    public Customer findByIdOrThrowNotFound(String id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }
}
