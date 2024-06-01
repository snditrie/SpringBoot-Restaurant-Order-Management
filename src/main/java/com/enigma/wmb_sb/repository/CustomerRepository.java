package com.enigma.wmb_sb.repository;

import com.enigma.wmb_sb.model.entity.Customer;
import com.enigma.wmb_sb.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {
    Boolean existsByUserAccount (UserAccount userAccount);

//    @Modifying
//    @Query(value = "UPDATE m_customer SET member_status = :isMember WHERE id = :id", nativeQuery = true)
//    void updateStatus(@Param("id") String id, @Param("isMember") Boolean memberStatus);
}
