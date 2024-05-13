package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.dto.request.SearchMenuRequest;
import com.enigma.wmb_sb.model.entity.Menu;

import java.util.List;

public interface MenuService {
    Menu create(Menu menu);
    Menu getById(String id);
    List<Menu> getAll(SearchMenuRequest menu);
    Menu update(Menu menu);
    void deleteById(String id);
    void updateMenuPrice(String id, Integer newPrice);
}
