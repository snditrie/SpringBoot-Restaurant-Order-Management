package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.model.dto.request.SearchMenuRequest;
import com.enigma.wmb_sb.model.dto.response.SearchMenuResponse;
import com.enigma.wmb_sb.model.entity.Menu;
import com.enigma.wmb_sb.repository.MenuRepository;
import com.enigma.wmb_sb.service.MenuService;
import com.enigma.wmb_sb.specification.MenuSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public SearchMenuResponse create(Menu menu) {
        Menu newMenu = Menu.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
        menuRepository.saveAndFlush(newMenu);
        return SearchMenuResponse.builder()
                .id(newMenu.getId())
                .name(newMenu.getName())
                .price(newMenu.getPrice().longValue())
                .build();
    }

    @Override
    public Menu entityById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public SearchMenuResponse getById(String id) {
        Menu menuFound = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("menu not found"));
        return SearchMenuResponse.builder()
                .id(menuFound.getId())
                .name(menuFound.getName())
                .price(menuFound.getPrice().longValue())
                .build();
    }

    @Override
    public Page<Menu> getAll(SearchMenuRequest request) {
        if(request.getPage() <= 0) {
            request.setPage(1);
        }

        String validSortBy;
        if("name".equalsIgnoreCase(request.getSortBy())) {
            validSortBy = request.getSortBy();
        } else {
            validSortBy = "name";
        }
        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), validSortBy);
        Pageable pageable = PageRequest.of((request.getPage()-1), request.getSize(), sort);

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
                return filteredMenusByPrice.isEmpty() ? menuRepository.findAll(pageable) : filteredMenusByPrice;
            } else {
                Specification<Menu> menuSpecification = MenuSpecification.getSpecification(request);
                Page<Menu> filteredMenus = menuRepository.findAll(menuSpecification, pageable);

                return filteredMenus.isEmpty() ? menuRepository.findAll(pageable) : filteredMenus;
            }
        }
    }

    @Override
    public SearchMenuResponse update(Menu menu) {
        Menu updateMenu = entityById(menu.getId());

        return SearchMenuResponse.builder()
                .id(updateMenu.getId())
                .name(updateMenu.getName())
                .price(updateMenu.getPrice().longValue())
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
                .orElseThrow(() -> new RuntimeException("menu not found"));
    }
}
