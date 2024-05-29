package com.enigma.wmb_sb.service;

import com.enigma.wmb_sb.model.dto.request.AuthRequest;
import com.enigma.wmb_sb.model.dto.response.LoginResponse;
import com.enigma.wmb_sb.model.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
    RegisterResponse registerAdmin(AuthRequest request);
    LoginResponse login(AuthRequest request);

}
