package com.enigma.wmb_sb.service.impl;

import com.enigma.wmb_sb.constant.UserRole;
import com.enigma.wmb_sb.model.entity.Role;
import com.enigma.wmb_sb.repository.RoleRepository;
import com.enigma.wmb_sb.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role getOrSave(UserRole role) {
        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.saveAndFlush(
                        Role.builder()
                                .role(role)
                                .build()));
    }
}
