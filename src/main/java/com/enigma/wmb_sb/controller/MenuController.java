package com.enigma.wmb_sb.controller;

import com.enigma.wmb_sb.constant.APIurl;
import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.request.NewMenuRequest;
import com.enigma.wmb_sb.model.dto.request.SearchMenuRequest;
import com.enigma.wmb_sb.model.dto.request.UpdateMenuRequest;
import com.enigma.wmb_sb.model.dto.response.CommonResponse;
import com.enigma.wmb_sb.model.dto.response.PagingResponse;
import com.enigma.wmb_sb.model.dto.response.MenuResponse;
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
    public ResponseEntity<CommonResponse<MenuResponse>> addNewMenu(@RequestBody NewMenuRequest request){
        MenuResponse newMenu = menuService.create(request);
        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(newMenu)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(path = APIurl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<MenuResponse>> getCustomerById(@PathVariable String id){
        MenuResponse getMenu = menuService.getById(id);
        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(getMenu)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAllMenu(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "priceStart", required = false) Integer priceStart,
            @RequestParam(name = "priceEnd", required = false) Integer priceEnd,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "5") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ){

        SearchMenuRequest request = SearchMenuRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .name(name)
                .priceStart(priceStart)
                .priceEnd(priceEnd)
                .build();
        Page<Menu> allMenus = menuService.getAll(request);

        List<MenuResponse> menuResponses = allMenus.getContent().stream()
                .map(mn -> new MenuResponse(
                        mn.getId(),
                        mn.getName(),
                        mn.getPrice()
                )).toList();

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(allMenus.getTotalPages())
                .totalElements(allMenus.getTotalElements())
                .page(allMenus.getPageable().getPageNumber() + 1)
                .size(allMenus.getPageable().getPageSize())
                .hasNext(allMenus.hasNext())
                .hasPrevious(allMenus.hasPrevious())
                .build();

        CommonResponse<List<MenuResponse>> response = CommonResponse.<List<MenuResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(menuResponses)
                .paging(pagingResponse)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = APIurl.PATH_VAR_ID)
    public ResponseEntity<CommonResponse<MenuResponse>> updateMenu(@RequestBody UpdateMenuRequest request){
        MenuResponse updateMenu = menuService.update(request);
        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(updateMenu)
                .build();
        return ResponseEntity.ok(response);
    }

}
