package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.request.SearchMenuRequest;
import com.enigma.wmb_sb.model.dto.response.CommonResponse;
import com.enigma.wmb_sb.model.dto.response.PagingResponse;
import com.enigma.wmb_sb.model.dto.response.SearchMenuResponse;
import com.enigma.wmb_sb.model.entity.Menu;
import com.enigma.wmb_sb.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIurl.MENU_API)
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<CommonResponse<SearchMenuResponse>> addNewMenu(@RequestBody Menu menu){
        SearchMenuResponse newMenu = menuService.create(menu);
        CommonResponse<SearchMenuResponse> response = CommonResponse.<SearchMenuResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(newMenu)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = APIurl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<SearchMenuResponse>> getCustomerById(@PathVariable String id){
        SearchMenuResponse getMenu = menuService.getById(id);
        CommonResponse<SearchMenuResponse> response = CommonResponse.<SearchMenuResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(getMenu)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<SearchMenuResponse>>> getAllMenu(
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
        Page<Menu> allMenus = menuService.getAll(request);

        List<SearchMenuResponse> menuResponses = allMenus.getContent().stream()
                .map(mn -> new SearchMenuResponse(
                        mn.getId(),
                        mn.getName(),
                        mn.getPrice().longValue()
                )).toList();

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(allMenus.getTotalPages())
                .totalElements(allMenus.getTotalElements())
                .page(allMenus.getPageable().getPageNumber())
                .size(allMenus.getPageable().getPageSize())
                .hasNext(allMenus.hasNext())
                .hasPrevious(allMenus.hasPrevious())
                .build();

        CommonResponse<List<SearchMenuResponse>> response = CommonResponse.<List<SearchMenuResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(menuResponses)
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<SearchMenuResponse>> updateMenu(@RequestBody Menu menu){
        SearchMenuResponse updateMenu = menuService.update(menu);
        CommonResponse<SearchMenuResponse> response = CommonResponse.<SearchMenuResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(updateMenu)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = APIurl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> deleteMenuById(@PathVariable String id){
        menuService.deleteById(id);
        CommonResponse<Menu> response = CommonResponse.<Menu>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = APIurl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<?>> updateMenuPriceById(
            @PathVariable String id,
            @RequestParam(name = "newPrice") Integer newPrice
    ){
        menuService.updateMenuPrice(id, newPrice);
        CommonResponse<Menu> response = CommonResponse.<Menu>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build();
        return ResponseEntity.ok(response);
    }
}
