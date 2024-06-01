package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.request.UpdateCustomerRequest;
import com.enigma.wmb_sb.model.dto.request.SearchCustomerRequest;
import com.enigma.wmb_sb.model.dto.response.CommonResponse;
import com.enigma.wmb_sb.model.dto.response.PagingResponse;
import com.enigma.wmb_sb.model.dto.response.CustomerResponse;
import com.enigma.wmb_sb.model.entity.Customer;
import com.enigma.wmb_sb.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.CUSTOMER_API)
public class CustomerController {
    private final CustomerService customerService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping(path = APIurl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable String id){
        CustomerResponse getCustomer = customerService.getById(id);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(getCustomer)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<CustomerResponse>>> getallCustomer(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "phoneNumber", required = false) String phone,
            @RequestParam(name = "isMember", required = false) Boolean member,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction

    ){
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .name(name)
                .phone(phone)
                .memberStatus(member)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<Customer> allCustomer = customerService.getAll(request);

        List<CustomerResponse> customerResponses = allCustomer.getContent().stream()
                .map(cs -> new CustomerResponse(
                        cs.getId(),
                        cs.getName(),
                        cs.getPhoneNumber(),
                        cs.getIsMember(),
                        cs.getUserAccount().getUsername()
                )).toList();

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(allCustomer.getTotalPages())
                .totalElements(allCustomer.getTotalElements())
                .page(allCustomer.getPageable().getPageNumber() + 1)
                .size(allCustomer.getPageable().getPageSize())
                .hasNext(allCustomer.hasNext())
                .hasPrevious(allCustomer.hasPrevious())
                .build();

        CommonResponse<List<CustomerResponse>> response = CommonResponse.<List<CustomerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customerResponses)
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@RequestBody UpdateCustomerRequest request){
        CustomerResponse updateCustomer = customerService.update(request);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(updateCustomer)
                .build();
        return ResponseEntity.ok(response);
    }

}
