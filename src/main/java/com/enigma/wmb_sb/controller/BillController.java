package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.model.dto.request.BillRequest;
import com.enigma.wmb_sb.model.dto.response.BillResponse;
import com.enigma.wmb_sb.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.BILL_API)
public class BillController {
    private final BillService billService;

    @PostMapping
    public BillResponse createNewBill(@RequestBody BillRequest request){
        return billService.create(request);
    }

    @GetMapping
    public List<BillResponse> getAllBill(){
        return billService.getAll();
    }
}
