package com.enigma.wmb_sb.repository;

import com.enigma.wmb_sb.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MenuRepository extends JpaRepository<Menu, String> {
    @Modifying
    @Query(value = "UPDATE m_menu SET menu_price = :price WHERE id = :id", nativeQuery = true)
    void updatePrice(@Param("id") String id, @Param("price") Integer newPrice);
}
