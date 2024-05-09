package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.model.enm.EnumTransactionType;
import com.enigma.wmb_sb.model.entity.TransactionType;
import com.enigma.wmb_sb.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.TRANS_TYPE_API)
public class TransactionTypeController {
    private final TransactionTypeService transactionTypeService;

    @PostMapping
    public TransactionType addNewDescription(@RequestBody TransactionType transactionType){
        return transactionTypeService.create(transactionType);
    }

    @GetMapping(path = APIurl.PATH_VAR_ID)
    public TransactionType getTransTypeById(@PathVariable EnumTransactionType id){
        return transactionTypeService.getById(id);
    }

    @GetMapping
    public List<TransactionType> getAllTransType(){
        return transactionTypeService.getAll();
    }

    @PutMapping(path = APIurl.PATH_VAR_ID)
    public String updateTransTypeDesc(
            @PathVariable EnumTransactionType id,
            @RequestParam(name = "newDescription") String newDescription
    ){
        transactionTypeService.updateDescriptionById(id, newDescription);
        return "description transaction type " + id + " has been updated";
    }

    @DeleteMapping(path = APIurl.PATH_VAR_ID)
    public String deleteTransTypeById(@PathVariable EnumTransactionType id){
        transactionTypeService.deleteById(id);
        return "transaction type " + id + " has been deleted";
    }


}
