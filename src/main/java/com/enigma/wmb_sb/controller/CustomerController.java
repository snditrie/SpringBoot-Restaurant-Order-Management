package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
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
    public List<Customer> getallCustomer(){
        return customerService.getAll();
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

    @PutMapping(path = APIurl.PATH_VAR_ID)
    public String updateMemberStatusById(
            @PathVariable String id,
            @RequestParam(name = "isMember") Boolean memberStatus
    ){
        customerService.updateStatusById(id, memberStatus);
        return "member status with id: " + id + " has been updated";
    }

}
