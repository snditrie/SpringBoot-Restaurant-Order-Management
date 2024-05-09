package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.model.entity.Menu;
import com.enigma.wmb_sb.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.MENU_API)
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public Menu addNewMenu(@RequestBody Menu menu){
        return menuService.create(menu);
    }

    @GetMapping(path = APIurl.PATH_VAR_ID)
    public Menu getCustomerById(@PathVariable String id){
        return menuService.getById(id);
    }

    @GetMapping
    public List<Menu> getAllMenu(){
        return menuService.getAll();
    }

    @PutMapping
    public Menu updateMenu(@RequestBody Menu menu){
        return menuService.update(menu);
    }

    @DeleteMapping(path = APIurl.PATH_VAR_ID)
    public String deleteMenuById(@PathVariable String id){
        menuService.deleteById(id);
        return "menu with id: " + id + " has been deleted";
    }

    @PutMapping(path = APIurl.PATH_VAR_ID)
    public String updateMenuPriceById(
            @PathVariable String id,
            @RequestParam(name = "newPrice") Integer newPrice
    ){
        menuService.updateMenuPrice(id, newPrice);
        return "menu price with id: " + id + " has been updated";
    }
}
