package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.model.dto.request.SearchMenuRequest;
import com.enigma.wmb_sb.model.dto.response.PagingResponse;
import com.enigma.wmb_sb.model.dto.response.SearchMenuResponse;
import com.enigma.wmb_sb.model.entity.Menu;
import com.enigma.wmb_sb.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.MENU_API)
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public SearchMenuResponse addNewMenu(@RequestBody Menu menu){
        return menuService.create(menu);
    }

    @GetMapping(path = APIurl.PATH_VAR_ID)
    public SearchMenuResponse getCustomerById(@PathVariable String id){
        return menuService.getById(id);
    }

    @GetMapping
    public Page<Menu> getAllMenu(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "priceStart", required = false) Long priceStart,
            @RequestParam(name = "priceEnd", required = false) Long priceEnd,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ){
        SearchMenuRequest request = SearchMenuRequest.builder()
                .name(name)
                .priceStart(priceStart)
                .priceEnd(priceEnd)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        return menuService.getAll(request);
    }

    @PutMapping
    public SearchMenuResponse updateMenu(@RequestBody Menu menu){
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
