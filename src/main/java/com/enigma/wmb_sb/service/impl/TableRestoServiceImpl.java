package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.constant.ResponseMessage;
import com.enigma.wmb_sb.model.entity.TableResto;
import com.enigma.wmb_sb.repository.TableRestoRepository;
import com.enigma.wmb_sb.service.TableRestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableRestoServiceImpl implements TableRestoService {
    private final TableRestoRepository tableRestoRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TableResto create(TableResto tableResto) {
        if(tableRestoRepository.existsByName(tableResto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_ALREADY_EXIST);
        }
        return tableRestoRepository.saveAndFlush(tableResto);
    }

    @Transactional(readOnly = true)
    @Override
    public TableResto getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TableResto> getAll() {
        if(tableRestoRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
        }

        return tableRestoRepository.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TableResto update(TableResto tableResto) {
        findByIdOrThrowNotFound(tableResto.getId());
        return tableRestoRepository.saveAndFlush(tableResto);
    }

    @Transactional(readOnly = true)
    public TableResto findByIdOrThrowNotFound(String id){
        return tableRestoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }
}
