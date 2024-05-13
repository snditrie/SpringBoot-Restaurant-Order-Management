package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.entity.BillDetail;

import java.util.List;

public interface BillDetailService {
    List<BillDetail> createBulk (List<BillDetail> billDetails);
}
