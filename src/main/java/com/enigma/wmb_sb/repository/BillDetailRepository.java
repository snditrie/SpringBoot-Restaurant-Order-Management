package com.enigma.wmb_sb.repository;

import com.enigma.wmb_sb.model.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, String> {
}
