package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.dto.request.SearchMenuRequest;
import com.enigma.wmb_sb.model.dto.response.SearchMenuResponse;
import com.enigma.wmb_sb.model.entity.Menu;
import org.springframework.data.domain.Page;

public interface MenuService {
    SearchMenuResponse create(SearchMenuRequest request);
    SearchMenuResponse getById(String id);
    Menu entityById(String id);
    Page<Menu> getAll(SearchMenuRequest request);
    SearchMenuResponse update(SearchMenuRequest request);
    void deleteById(String id);
    void updateMenuPrice(String id, Integer newPrice);
}
