package com.enigma.wmb_sb.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchCustomerRequest {
    private String name;
    private String phone;
    private Boolean memberStatus;
}
