package com.enigma.wmb_sb.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillRequest {
    private String customerId;
    private String transTypeId;
    private String tableRestoId;
    private List<BillDetailRequest> billDetails;
}
