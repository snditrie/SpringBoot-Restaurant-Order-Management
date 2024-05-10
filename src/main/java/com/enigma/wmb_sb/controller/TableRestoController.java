package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.model.entity.TableResto;
import com.enigma.wmb_sb.service.TableRestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.TABLE_API)
public class TableRestoController {
    private final TableRestoService tableRestoService;

    @PostMapping
    public TableResto addNewTable(@RequestBody TableResto tableResto){
        return tableRestoService.create(tableResto);
    }

    @GetMapping(path = APIurl.PATH_VAR_ID)
    public TableResto getTableById(@PathVariable String id){
        return tableRestoService.getById(id);
    }

    @GetMapping
    public List<TableResto> getAllTable(){
        return tableRestoService.getAll();
    }

    @PutMapping
    public TableResto updateTable(@RequestBody TableResto tableResto){
        return tableRestoService.update(tableResto);
    }

    @DeleteMapping(path = APIurl.PATH_VAR_ID)
    public String deleteTable(@PathVariable String id){
        tableRestoService.deleteById(id);
        return "table " + id + " has been deleted";
    }
}
