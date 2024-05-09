package com.enigma.wmb_sb.repository;

import com.enigma.wmb_sb.model.enm.EnumTransactionType;
import com.enigma.wmb_sb.model.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TransactionTypeRepository extends JpaRepository<TransactionType, EnumTransactionType> {
    @Modifying
    @Query(value = "UPDATE m_trans_type SET description = :description WHERE id = :id", nativeQuery = true)
    void updateDescription(@Param("id") EnumTransactionType id, @Param("description") String newDescription);
}
