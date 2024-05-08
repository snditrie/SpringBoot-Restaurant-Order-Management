package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.entity.Menu;
import com.enigma.wmb_sb.repository.MenuRepository;
import com.enigma.wmb_sb.service.MenuService;
import lombok.RequiredArgsConstructor;
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
        return null;
    }

    @Override
    public List<Menu> getAll() {
        return List.of();
    }

    @Override
    public Menu update(Menu menu) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    public Menu findByIdOrThrowNotFound(String id){
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("menu not found"));
    }
}
