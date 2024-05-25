package com.enigma.wmb_sb.repository;

import com.enigma.wmb_sb.model.enm.EnumTransactionType;
import com.enigma.wmb_sb.model.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, EnumTransactionType> {
    Boolean existsByDescription(String description);
}
