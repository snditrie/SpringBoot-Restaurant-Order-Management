package com.enigma.wmb_sb.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchCustomerResponse {
    private String id;
    private String name;
    private String mobilePhone;
    private Boolean isMember;
}
