package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.model.enm.EnumTransactionType;
import com.enigma.wmb_sb.model.entity.TransactionType;
import com.enigma.wmb_sb.repository.TransactionTypeRepository;
import com.enigma.wmb_sb.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;

    @Override
    public TransactionType create(TransactionType transactionType) {
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

    @Override
    public void deleteById(EnumTransactionType id) {
        TransactionType transTypeToDelete = findByIdOrThrowNotFound(id);
        transactionTypeRepository.delete(transTypeToDelete);
    }

    public TransactionType findByIdOrThrowNotFound(EnumTransactionType id){
        return transactionTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("transaction type not found"));
    }
}
