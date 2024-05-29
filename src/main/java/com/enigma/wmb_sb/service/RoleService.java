package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.constant.UserRole;
import com.enigma.wmb_sb.model.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
