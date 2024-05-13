package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.model.dto.request.BillRequest;
import com.enigma.wmb_sb.model.dto.response.BillDetailResponse;
import com.enigma.wmb_sb.model.dto.response.BillResponse;
import com.enigma.wmb_sb.model.entity.Bill;
import com.enigma.wmb_sb.model.entity.BillDetail;
import com.enigma.wmb_sb.model.entity.Customer;
import com.enigma.wmb_sb.model.entity.Menu;
import com.enigma.wmb_sb.repository.BillRepository;
import com.enigma.wmb_sb.service.BillDetailService;
import com.enigma.wmb_sb.service.BillService;
import com.enigma.wmb_sb.service.CustomerService;
import com.enigma.wmb_sb.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillDetailService billDetailService;
    private final CustomerService customerService;
    private final MenuService menuService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BillResponse create(BillRequest request) {
        Customer customer = customerService.getById(request.getCustomerId());
        Bill bill = Bill.builder()
                .customerId(customer)
                .transDate(new Date())
                .build();
        billRepository.saveAndFlush(bill);
        log.info("Check detail dari billDetail: {}", bill.getCustomerId());

        List<BillDetail> billDetails = request.getBillDetails().stream()
                .map(detailRequest -> {
                    Menu menu = menuService.getById(detailRequest.getMenuId());
                    return BillDetail.builder()
                            .menuId(menu)
                            .billId(bill)
                            .qty(detailRequest.getQty())
                            .build();
                }).toList();
        billDetailService.createBulk(billDetails);
        bill.setBillDetails(billDetails);

        List<BillDetailResponse> billDetailResponse = billDetails.stream()
                .map(detail -> {
                    return BillDetailResponse.builder()
                            .id(detail.getId())
                            .menuId(detail.getMenuId().getId())
                            .qty(detail.getQty())
                            .build();
                }).toList();
        return BillResponse.builder()
                .id(bill.getId())
                .customerId(bill.getCustomerId().getId())
                .transDate(bill.getTransDate())
                .billDetails(billDetailResponse)
                .build();
    }

    @Override
    public List<BillResponse> getAll() {
        List<Bill> bills = billRepository.findAll();

        return bills.stream()
                .map(trx -> {
                    List<BillDetailResponse> billDetailResponse = trx.getBillDetails().stream().map(detail -> {
                        return BillDetailResponse.builder()
                                .id(detail.getId())
                                .menuId(detail.getMenuId().getId())
                                .qty(detail.getQty())
                                .build();
                    }).toList();
                    return BillResponse.builder()
                            .id(trx.getId())
                            .customerId(trx.getCustomerId().getId())
                            .transDate(trx.getTransDate())
                            .billDetails(billDetailResponse)
                            .build();
                }).toList();
    }
}
