package com.enigma.wmb_sb.model.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtClaims {
    private String userAccountId;
    private List<String> roles;
}
