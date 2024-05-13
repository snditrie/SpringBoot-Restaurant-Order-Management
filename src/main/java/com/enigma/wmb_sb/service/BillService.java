package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.dto.request.BillRequest;
import com.enigma.wmb_sb.model.dto.response.BillResponse;
import com.enigma.wmb_sb.model.entity.Bill;
import com.enigma.wmb_sb.model.entity.TableResto;

import java.util.List;

public interface BillService {
    BillResponse create(BillRequest request);
//    Bill getById(String id);
    List<BillResponse> getAll();
//    Bill update(Bill bill);
//    void deleteById(String id);
}
