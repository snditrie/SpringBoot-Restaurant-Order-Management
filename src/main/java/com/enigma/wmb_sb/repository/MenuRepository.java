package com.enigma.wmb_sb.repository;

import com.enigma.wmb_sb.model.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface MenuRepository extends JpaRepository<Menu, String>, JpaSpecificationExecutor<Menu> {
    Page<Menu> findAllByNameContainingIgnoreCaseAndPriceBetween(String name, Integer priceStart, Integer priceEnd, Pageable pageable);
    Boolean existsByName(String name);

    @Modifying
    @Query(value = "UPDATE m_menu SET menu_price = :price WHERE id = :id", nativeQuery = true)
    void updatePrice(@Param("id") String id, @Param("price") Integer newPrice);
}
