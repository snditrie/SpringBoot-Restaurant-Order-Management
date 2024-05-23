package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.request.SearchMenuRequest;
import com.enigma.wmb_sb.model.dto.response.SearchMenuResponse;
import com.enigma.wmb_sb.model.entity.Menu;
import com.enigma.wmb_sb.repository.MenuRepository;
import com.enigma.wmb_sb.service.MenuService;
import com.enigma.wmb_sb.specification.MenuSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public SearchMenuResponse create(SearchMenuRequest request) {
        if(menuRepository.existsByName(request.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_ALREADY_EXIST);
        }

        Menu newMenu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
        menuRepository.saveAndFlush(newMenu);

        return SearchMenuResponse.builder()
                .id(newMenu.getId())
                .name(newMenu.getName())
                .price(newMenu.getPrice())
                .build();
    }

    @Override
    public Menu entityById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public SearchMenuResponse getById(String id) {
        Menu menuFound = menuRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
        return SearchMenuResponse.builder()
                .id(menuFound.getId())
                .name(menuFound.getName())
                .price(menuFound.getPrice())
                .build();
    }

    @Override
    public Page<Menu> getAll(SearchMenuRequest request) {
        if(request.getPage() <= 0) {
            request.setPage(1);
        }
        log.info("Check page: {}", request.getPage());

        String validSortBy;
        if("name".equalsIgnoreCase(request.getSortBy()) || "price".equalsIgnoreCase(request.getSortBy())) {
            validSortBy = request.getSortBy();
        } else {
            validSortBy = "name";
        }
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), validSortBy);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);
        log.info("Check page: {}", pageable.getPageNumber());

        if(     request.getName() == null &&
                request.getPriceStart() == null &&
                request.getPriceEnd() == null) {

            return menuRepository.findAll(pageable);
        } else {
            if(     request.getName() != null &&
                    request.getPriceStart() != null &&
                    request.getPriceEnd() != null) {

                Page<Menu> filteredMenusByPrice = menuRepository.findAllByNameContainingIgnoreCaseAndPriceBetween(
                        request.getName(), request.getPriceStart(), request.getPriceEnd(), pageable);
                if(filteredMenusByPrice.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
                } else {
                    return filteredMenusByPrice;
                }
            } else {
                Specification<Menu> menuSpecification = MenuSpecification.getSpecification(request);
                Page<Menu> filteredMenus = menuRepository.findAll(menuSpecification, pageable);
                if(filteredMenus.isEmpty()) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
                } else {
                    return filteredMenus;
                }
            }
        }
    }

    @Override
    public SearchMenuResponse update(SearchMenuRequest request) {
        Menu updateMenu = entityById(request.getId());

        return SearchMenuResponse.builder()
                .id(updateMenu.getId())
                .name(updateMenu.getName())
                .price(updateMenu.getPrice())
                .build();
    }

    @Override
    public void deleteById(String id) {
        Menu menuToDelete = findByIdOrThrowNotFound(id);
        menuRepository.delete(menuToDelete);
    }

    @Override
    public void updateMenuPrice(String id, Integer newPrice) {
        findByIdOrThrowNotFound(id);
        menuRepository.updatePrice(id, newPrice);
    }

    public Menu findByIdOrThrowNotFound(String id){
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }
}
