package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.enm.EnumTransactionType;
import com.enigma.wmb_sb.model.entity.Menu;
import com.enigma.wmb_sb.model.entity.TransactionType;

import java.util.List;

public interface TransactionTypeService {
    TransactionType create(TransactionType transactionType);
    TransactionType getById(EnumTransactionType id);
    List<TransactionType> getAll();
    void updateDescriptionById(EnumTransactionType id, String newDescription);
    void deleteById(EnumTransactionType id);
}
