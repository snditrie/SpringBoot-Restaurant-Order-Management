package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.model.dto.request.SearchMenuRequest;
import com.enigma.wmb_sb.model.entity.Menu;
import com.enigma.wmb_sb.repository.MenuRepository;
import com.enigma.wmb_sb.service.MenuService;
import com.enigma.wmb_sb.specification.MenuSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public Menu create(Menu menu) {
        return menuRepository.saveAndFlush(menu);
    }

    @Override
    public Menu getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<Menu> getAll(SearchMenuRequest request) {

        if(     request == null ||
                request.getName() == null &&
                request.getPriceStart() == null &&
                request.getPriceEnd() == null) {

            return menuRepository.findAll();
        } else {
            if(     request.getName() != null &&
                    request.getPriceStart() != null &&
                    request.getPriceEnd() != null) {

                return menuRepository.findAllByNameContainingIgnoreCaseAndPriceBetween(request.getName(), request.getPriceStart(), request.getPriceEnd());
            } else {
                Specification<Menu> menuSpecification = MenuSpecification.getSpecification(request);
                List<Menu> filteredMenus = menuRepository.findAll(menuSpecification);

                return filteredMenus.isEmpty() ? menuRepository.findAll() : filteredMenus;
            }
        }
    }

    @Override
    public Menu update(Menu menu) {
        findByIdOrThrowNotFound(menu.getId());
        return menuRepository.saveAndFlush(menu);
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
