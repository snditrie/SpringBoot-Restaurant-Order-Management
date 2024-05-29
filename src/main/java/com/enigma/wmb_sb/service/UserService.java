package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserAccount getByUserId(String id);
    UserAccount getByContext();
}
