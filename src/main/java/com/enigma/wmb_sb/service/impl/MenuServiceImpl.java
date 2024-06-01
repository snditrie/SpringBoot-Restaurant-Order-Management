package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.request.NewMenuRequest;
import com.enigma.wmb_sb.model.dto.request.SearchMenuRequest;
import com.enigma.wmb_sb.model.dto.request.UpdateMenuRequest;
import com.enigma.wmb_sb.model.dto.response.MenuResponse;
import com.enigma.wmb_sb.model.entity.Menu;
import com.enigma.wmb_sb.repository.MenuRepository;
import com.enigma.wmb_sb.service.MenuService;
import com.enigma.wmb_sb.specification.MenuSpecification;
import com.enigma.wmb_sb.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse create(NewMenuRequest request) {
        validationUtil.validate(request);

        if(menuRepository.existsByName(request.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_ALREADY_EXIST);
        }

        Menu newMenu = Menu.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
        menuRepository.saveAndFlush(newMenu);

        return parseMenuToMenuResponse(newMenu);
    }

    @Transactional(readOnly = true)
    @Override
    public Menu entityById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public MenuResponse getById(String id) {
        Menu menuFound = menuRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
        return parseMenuToMenuResponse(menuFound);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Menu> getAll(SearchMenuRequest request) {
        if(request.getPage() <= 0) {
            request.setPage(1);
        }

        String validSortBy;
        if("name".equalsIgnoreCase(request.getSortBy()) || "price".equalsIgnoreCase(request.getSortBy())) {
            validSortBy = request.getSortBy();
        } else {
            validSortBy = "name";
        }
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), validSortBy);
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize(), sort);

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MenuResponse update(UpdateMenuRequest request) {
        validationUtil.validate(request);

        Menu updateMenu = entityById(request.getId());

        if(request.getName() != null) {
            updateMenu.setName(request.getName());
        }

        if(request.getPrice() != null) {
            updateMenu.setPrice(request.getPrice());
        }

        menuRepository.saveAndFlush(updateMenu);
        return parseMenuToMenuResponse(updateMenu);
    }

    @Transactional(readOnly = true)
    public Menu findByIdOrThrowNotFound(String id){
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }

    private MenuResponse parseMenuToMenuResponse(Menu menu){
        return MenuResponse.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }
}
