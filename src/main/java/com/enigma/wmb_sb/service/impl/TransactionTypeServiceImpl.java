package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.enm.EnumTransactionType;
import com.enigma.wmb_sb.model.entity.TransactionType;
import com.enigma.wmb_sb.repository.TransactionTypeRepository;
import com.enigma.wmb_sb.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;

    @Override
    public TransactionType create(TransactionType transactionType) {
        if(transactionTypeRepository.existsByDescription(transactionType.getDescription())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_ALREADY_EXIST);
        }
        return transactionTypeRepository.saveAndFlush(transactionType);
    }

    @Override
    public TransactionType getById(EnumTransactionType id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<TransactionType> getAll() {
        return transactionTypeRepository.findAll();
    }

    @Override
    public TransactionType update(TransactionType transactionType) {
        findByIdOrThrowNotFound(transactionType.getId());
        return transactionTypeRepository.saveAndFlush(transactionType);

    }

    public TransactionType findByIdOrThrowNotFound(EnumTransactionType id){
        return transactionTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }
}
