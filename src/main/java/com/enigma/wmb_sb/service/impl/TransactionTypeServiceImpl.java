package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.constant.EnumTransactionType;
import com.enigma.wmb_sb.model.entity.TransactionType;
import com.enigma.wmb_sb.repository.TransactionTypeRepository;
import com.enigma.wmb_sb.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionType create(TransactionType transactionType) {
        if(transactionTypeRepository.existsByDescription(transactionType.getDescription())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_ALREADY_EXIST);
        }
        return transactionTypeRepository.saveAndFlush(transactionType);
    }

    @Transactional(readOnly = true)
    @Override
    public TransactionType getById(EnumTransactionType id) {
        return findByIdOrThrowNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransactionType> getAll() {
        return transactionTypeRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionType update(TransactionType transactionType) {
        findByIdOrThrowNotFound(transactionType.getId());
        return transactionTypeRepository.saveAndFlush(transactionType);
    }

    @Transactional(readOnly = true)
    public TransactionType findByIdOrThrowNotFound(EnumTransactionType id){
        return transactionTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }
}
