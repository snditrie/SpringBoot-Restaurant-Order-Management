package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.dto.request.SearchMenuRequest;
import com.enigma.wmb_sb.model.dto.response.MenuResponse;
import com.enigma.wmb_sb.model.entity.Menu;
import org.springframework.data.domain.Page;

public interface MenuService {
    MenuResponse create(SearchMenuRequest request);
    MenuResponse getById(String id);
    Menu entityById(String id);
    Page<Menu> getAll(SearchMenuRequest request);
    MenuResponse update(SearchMenuRequest request);
    void deleteById(String id);
//    void updateMenuPrice(String id, Integer newPrice);
}
