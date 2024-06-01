package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.response.CommonResponse;
import com.enigma.wmb_sb.model.entity.TableResto;
import com.enigma.wmb_sb.service.TableRestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.TABLE_API)
public class TableRestoController {
    private final TableRestoService tableRestoService;

    @PostMapping
    public ResponseEntity<CommonResponse<TableResto>> addNewTable(@RequestBody TableResto tableResto){
        TableResto newTableResto = tableRestoService.create(tableResto);
        CommonResponse<TableResto> response = CommonResponse.<TableResto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(newTableResto)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = APIurl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<TableResto>> getTableById(@PathVariable String id){
        TableResto getTableResto = tableRestoService.getById(id);
        CommonResponse<TableResto> response = CommonResponse.<TableResto>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(getTableResto)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TableResto>>> getAllTable(){
        List<TableResto> allTableResto = tableRestoService.getAll();
        CommonResponse<List<TableResto>> response = CommonResponse.<List<TableResto>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(allTableResto)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<TableResto>> updateTable(@RequestBody TableResto tableResto){
        TableResto updateTableResto = tableRestoService.update(tableResto);
        CommonResponse<TableResto> response = CommonResponse.<TableResto>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(updateTableResto)
                .build();
        return ResponseEntity.ok(response);
    }

}
