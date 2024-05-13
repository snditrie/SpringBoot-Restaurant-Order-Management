package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.model.dto.request.SearchCustomerRequest;
import com.enigma.wmb_sb.model.entity.Customer;
import com.enigma.wmb_sb.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.CUSTOMER_API)
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public Customer addNewMenu(@RequestBody Customer customer){
        return customerService.create(customer);
    }

    @GetMapping(path = APIurl.PATH_VAR_ID)
    public Customer getCustomerById(@PathVariable String id){
        return customerService.getById(id);
    }

    @GetMapping
    public List<Customer> getallCustomer(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "member", required = false) Boolean member
    ){
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .name(name)
                .phone(phone)
                .memberStatus(member)
                .build();
        return customerService.getAll(request);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer){
        return customerService.update(customer);
    }

    @DeleteMapping(path = APIurl.PATH_VAR_ID)
    public String deleteCustomerById(@PathVariable String id){
        customerService.deleteById(id);
        return "customer with id: " + id + " has been deleted";
    }



}
