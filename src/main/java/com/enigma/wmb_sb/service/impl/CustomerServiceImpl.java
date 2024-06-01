package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.request.UpdateCustomerRequest;
import com.enigma.wmb_sb.model.dto.request.SearchCustomerRequest;
import com.enigma.wmb_sb.model.dto.response.CustomerResponse;
import com.enigma.wmb_sb.model.entity.Customer;
import com.enigma.wmb_sb.model.entity.UserAccount;
import com.enigma.wmb_sb.repository.CustomerRepository;
import com.enigma.wmb_sb.service.CustomerService;
import com.enigma.wmb_sb.service.UserService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;
    private final UserService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse create(Customer customer) {
        validationUtil.validate(customer);

        if(customerRepository.existsByUserAccount(customer.getUserAccount())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_ALREADY_EXIST);
        }

        Customer savedCustomer = customerRepository.saveAndFlush(customer);
        return CustomerResponse.builder()
                .id(savedCustomer.getId())
                .name(savedCustomer.getName())
                .mobilePhone(savedCustomer.getPhoneNumber())
                .memberStatus(savedCustomer.getIsMember())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Customer entityById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public CustomerResponse getById(String id) {
        Customer customerFound = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
        return parseCustomerToCustomerResponse(customerFound);
    }

    @Transactional(readOnly = true)
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerResponse update(UpdateCustomerRequest request) {
        validationUtil.validate(request);

        Customer customerToUpdate = entityById(request.getId());
        UserAccount userAccount = userService.getByContext();

        if(!userAccount.getId().equals(customerToUpdate.getUserAccount().getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
        }

        customerToUpdate.setName(request.getName());
        customerToUpdate.setPhoneNumber(request.getMobilePhone());
        customerToUpdate.setIsMember(request.getMemberStatus());
        customerRepository.saveAndFlush(customerToUpdate);
        return parseCustomerToCustomerResponse(customerToUpdate);
    }

    @Transactional(readOnly = true)
    public Customer findByIdOrThrowNotFound(String id){
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    private CustomerResponse parseCustomerToCustomerResponse(Customer customer){
        String userId;
        if(customer.getUserAccount() == null){
            userId = null;
        } else {
            userId = customer.getUserAccount().getId();
        }
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .mobilePhone(customer.getPhoneNumber())
                .memberStatus(customer.getIsMember())
                .userAccountId(userId)
                .build();
    }

}
