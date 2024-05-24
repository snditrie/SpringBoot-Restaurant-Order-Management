package com.enigma.wmb_sb.repository;

import com.enigma.wmb_sb.model.entity.TableResto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRestoRepository extends JpaRepository<TableResto, String> {
    Boolean existsByName (String name);
}
