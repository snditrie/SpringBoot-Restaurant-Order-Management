package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.entity.TableResto;

import java.util.List;

public interface TableRestoService {
    TableResto create(TableResto tableResto);
    TableResto getById(String id);
    List<TableResto> getAll();
    TableResto update(TableResto tableResto);
    void deleteById(String id);
}
