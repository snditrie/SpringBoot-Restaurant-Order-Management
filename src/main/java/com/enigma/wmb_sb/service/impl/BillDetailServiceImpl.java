package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.model.entity.BillDetail;
import com.enigma.wmb_sb.repository.BillDetailRepository;
import com.enigma.wmb_sb.service.BillDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillDetailServiceImpl implements BillDetailService {
    private final BillDetailRepository billDetailRepository;

    @Override
    public List<BillDetail> createBulk(List<BillDetail> billDetails) {
        return billDetailRepository.saveAllAndFlush(billDetails);
    }
}
