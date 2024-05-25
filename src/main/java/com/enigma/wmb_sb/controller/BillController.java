package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.request.BillRequest;
import com.enigma.wmb_sb.model.dto.request.SearchBillRequest;
import com.enigma.wmb_sb.model.dto.response.BillDetailResponse;
import com.enigma.wmb_sb.model.dto.response.BillResponse;
import com.enigma.wmb_sb.model.dto.response.CommonResponse;
import com.enigma.wmb_sb.model.dto.response.PagingResponse;
import com.enigma.wmb_sb.model.entity.Bill;
import com.enigma.wmb_sb.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.BILL_API)
public class BillController {
    private final BillService billService;

    @PostMapping
    public ResponseEntity<CommonResponse<BillResponse>> addNewBill(@RequestBody BillRequest request){
        BillResponse newBill = billService.create(request);
        CommonResponse<BillResponse> response = CommonResponse.<BillResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(newBill)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<BillResponse>>> getAllBill(
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ){
        SearchBillRequest request = SearchBillRequest.builder()
                .date(date)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<Bill> allBills = billService.getAll(request);
        List<BillResponse> billResponses = allBills.getContent().stream()
                .map(bl -> BillResponse.builder()
                        .id(bl.getId())
                        .customerName(bl.getCustomerId().getName())
                        .tableRestoName(bl.getTableRestoId().getName())
                        .transTypeId(bl.getTransactionTypeId().getId().toString())
                        .transDate(bl.getTransDate())
                        .billDetails(bl.getBillDetails().stream()
                                .map(detail -> BillDetailResponse.builder()
                                        .id(detail.getId())
                                        .menuName(detail.getMenuId().getName())
                                        .qty(detail.getQty())
                                        .price(detail.getMenuId().getPrice())
                                        .totalAmount(detail.getMenuId().getPrice() * detail.getQty())
                                        .build()).toList())
                        .totalAmount(bl.getBillDetails().stream()
                                .mapToInt(dt -> dt.getMenuId().getPrice() * dt.getQty()).sum())
                        .build()).toList();

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(allBills.getTotalPages())
                .totalElements(allBills.getTotalElements())
                .page(allBills.getPageable().getPageNumber() + 1)
                .size(allBills.getPageable().getPageSize())
                .hasNext(allBills.hasNext())
                .hasPrevious(allBills.hasPrevious())
                .build();

        CommonResponse<List<BillResponse>> response = CommonResponse.<List<BillResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(billResponses)
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = APIurl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<BillResponse>> getBillById(@PathVariable String id) {
        BillResponse getBill = billService.getById(id);
        CommonResponse<BillResponse> response = CommonResponse.<BillResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(getBill)
                .build();
        return ResponseEntity.ok(response);
    }
}
