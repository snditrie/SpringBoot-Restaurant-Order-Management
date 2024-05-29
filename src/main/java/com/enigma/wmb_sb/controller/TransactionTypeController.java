package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.response.CommonResponse;
import com.enigma.wmb_sb.constant.EnumTransactionType;
import com.enigma.wmb_sb.model.entity.TransactionType;
import com.enigma.wmb_sb.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.TRANS_TYPE_API)
public class TransactionTypeController {
    private final TransactionTypeService transactionTypeService;

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionType>> addNewDescription(@RequestBody TransactionType transactionType){
        TransactionType newDescription = transactionTypeService.create(transactionType);
        CommonResponse<TransactionType> response = CommonResponse.<TransactionType>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(newDescription)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = APIurl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<TransactionType>> getTransTypeById(@PathVariable EnumTransactionType id){
        TransactionType getTransType = transactionTypeService.getById(id);
        CommonResponse<TransactionType> response = CommonResponse.<TransactionType>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(getTransType)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionType>>> getAllTransType(){
        List<TransactionType> allTransType = transactionTypeService.getAll();
        CommonResponse<List<TransactionType>> response = CommonResponse.<List<TransactionType>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(allTransType)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<TransactionType>> updateTransTypeDesc(@RequestBody TransactionType transactionType){
        TransactionType updateDescription = transactionTypeService.update(transactionType);
        CommonResponse<TransactionType> response = CommonResponse.<TransactionType>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(updateDescription)
                .build();
        return ResponseEntity.ok(response);
    }

}
