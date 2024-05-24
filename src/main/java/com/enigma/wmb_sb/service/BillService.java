package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.dto.request.BillRequest;
import com.enigma.wmb_sb.model.dto.request.SearchBillRequest;
import com.enigma.wmb_sb.model.dto.response.BillResponse;
import com.enigma.wmb_sb.model.entity.Bill;
import com.enigma.wmb_sb.model.entity.TableResto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillService {
    BillResponse create(BillRequest request);
    Page<Bill> getAll(SearchBillRequest request);
    BillResponse getById(String id);
}
