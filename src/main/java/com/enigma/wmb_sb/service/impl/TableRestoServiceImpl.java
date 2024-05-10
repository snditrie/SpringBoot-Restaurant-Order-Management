package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.model.entity.TableResto;
import com.enigma.wmb_sb.repository.TableRestoRepository;
import com.enigma.wmb_sb.service.TableRestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableRestoServiceImpl implements TableRestoService {
    private final TableRestoRepository tableRestoRepository;

    @Override
    public TableResto create(TableResto tableResto) {
        return tableRestoRepository.saveAndFlush(tableResto);
    }

    @Override
    public TableResto getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public List<TableResto> getAll() {
        return tableRestoRepository.findAll();
    }

    @Override
    public TableResto update(TableResto tableResto) {
        findByIdOrThrowNotFound(tableResto.getId());
        return tableRestoRepository.saveAndFlush(tableResto);
    }

    @Override
    public void deleteById(String id) {
        TableResto tableToDelete = findByIdOrThrowNotFound(id);
        tableRestoRepository.delete(tableToDelete);
    }

    public TableResto findByIdOrThrowNotFound(String id){
        return tableRestoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("table not found"));
    }
}
