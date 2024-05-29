package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.dto.request.BillRequest;
import com.enigma.wmb_sb.model.dto.request.SearchBillRequest;
import com.enigma.wmb_sb.model.dto.response.BillDetailResponse;
import com.enigma.wmb_sb.model.dto.response.BillResponse;
import com.enigma.wmb_sb.constant.EnumTransactionType;
import com.enigma.wmb_sb.model.entity.*;
import com.enigma.wmb_sb.repository.BillRepository;
import com.enigma.wmb_sb.service.*;
import com.enigma.wmb_sb.specification.BillSpecification;
import com.enigma.wmb_sb.utils.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillDetailService billDetailService;
    private final CustomerService customerService;
    private final MenuService menuService;
    private final TransactionTypeService transactionTypeService;
    private final TableRestoService tableRestoService;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BillResponse create(BillRequest request) {
        validationUtil.validate(request);
        validationUtil.validateTableRestoId(request);
        Customer customer = customerService.entityById(request.getCustomerId());
        TransactionType transactionType = transactionTypeService.getById(EnumTransactionType.valueOf(request.getTransTypeId()));

        TableResto tableResto = null;
        if (request.getTableRestoId() != null) {
            tableResto = tableRestoService.getById(request.getTableRestoId());
        }

        Bill bill = Bill.builder()
                .customerId(customer)
                .transactionTypeId(transactionType)
                .tableRestoId(tableResto)
                .transDate(new Date())
                .build();
        billRepository.saveAndFlush(bill);

        List<BillDetail> billDetails = request.getBillDetails().stream()
                .map(detailRequest -> {
                    Menu menu = menuService.entityById(detailRequest.getMenuId());

                    return BillDetail.builder()
                            .billId(bill)
                            .menuId(menu)
                            .qty(detailRequest.getQty())
                            .build();
                }).toList();
        billDetailService.createBulk(billDetails);
        bill.setBillDetails(billDetails);

        List<BillDetailResponse> billDetailResponse = billDetails.stream()
                .map(detail -> BillDetailResponse.builder()
                        .id(detail.getId())
                        .menuName(detail.getMenuId().getName())
                        .qty(detail.getQty())
                        .price(detail.getMenuId().getPrice())
                        .subTotal(detail.getMenuId().getPrice() * detail.getQty())
                        .build()).toList();
        return BillResponse.builder()
                .id(bill.getId())
                .customerName(bill.getCustomerId().getName())
                .transTypeId(bill.getTransactionTypeId().getId().toString())
                .tableRestoName(bill.getTableRestoId() != null ? bill.getTableRestoId().getName() : null)
                .transDate(bill.getTransDate())
                .billDetails(billDetailResponse)
                .totalAmount(billDetails.stream().mapToInt(detail -> detail.getMenuId().getPrice() * detail.getQty()).sum())
                .build();
    }

    @Override
    public Page<Bill> getAll(SearchBillRequest request) {
        if(request.getPage() <= 0) {
            request.setPage(1);
        }
        String validSortBy;
        if("transDate".equalsIgnoreCase(request.getSortBy()) || "totalAmount".equalsIgnoreCase(request.getSortBy())) {
            validSortBy = request.getSortBy();
        } else {
            validSortBy = "transDate";
        }

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), validSortBy);
        Pageable pageable = PageRequest.of(request.getPage() -1, request.getSize(), sort);

        if(request.getDate() == null) {
            return billRepository.findAll(pageable);
        } else {
            Specification<Bill> billSpecification = BillSpecification.getSpecification(request);
            Page<Bill> filteredBills = billRepository.findAll(billSpecification, pageable);
            if (filteredBills.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            } else {
                return filteredBills;
            }
        }

    }

    @Override
    public BillResponse getById(String id) {
        Bill billFound = billRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));

        List<BillDetailResponse> billDetailResponse = billFound.getBillDetails().stream()
                .map(detail -> BillDetailResponse.builder()
                        .id(detail.getId())
                        .menuName(detail.getMenuId().getName())
                        .qty(detail.getQty())
                        .price(detail.getMenuId().getPrice())
                        .subTotal(detail.getMenuId().getPrice() * detail.getQty())
                        .build()).toList();

        int totalAmount = billFound.getBillDetails().stream()
                .mapToInt(detail -> detail.getMenuId().getPrice() * detail.getQty())
                .sum();

        return BillResponse.builder()
                .id(id)
                .customerName(billFound.getCustomerId().getName())
                .tableRestoName(billFound.getTableRestoId() != null ? billFound.getTableRestoId().getName() : null)
                .transTypeId(billFound.getTransactionTypeId().getId().toString())
                .transDate(billFound.getTransDate())
                .billDetails(billDetailResponse)
                .totalAmount(totalAmount)
                .build();
    }
}
