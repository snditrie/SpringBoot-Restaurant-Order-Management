package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.dto.response.JwtClaims;
import com.enigma.wmb_sb.model.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);

}
