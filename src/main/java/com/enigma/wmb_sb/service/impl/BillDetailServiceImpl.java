package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.entity.BillDetail;
import com.enigma.wmb_sb.repository.BillDetailRepository;
import com.enigma.wmb_sb.service.BillDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillDetailServiceImpl implements BillDetailService {
    private final BillDetailRepository billDetailRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<BillDetail> createBulk(List<BillDetail> billDetails) {
        return billDetailRepository.saveAllAndFlush(billDetails);
    }

    @Transactional(readOnly = true)
    @Override
    public BillDetail getById(String id) {
        return billDetailRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }
}
