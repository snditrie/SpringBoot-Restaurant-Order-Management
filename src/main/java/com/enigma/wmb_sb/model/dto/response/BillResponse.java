package com.enigma.wmb_sb.model.dto.response;

import com.enigma.wmb_sb.model.entity.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class BillResponse {
    private String id;
    private String customerId;
    private String tableRestoId;
    private String transTypeId;
    private Date transDate;
    private List<BillDetailResponse> billDetails;
}
