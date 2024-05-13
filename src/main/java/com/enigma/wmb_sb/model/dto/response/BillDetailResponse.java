package com.enigma.wmb_sb.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillDetailResponse {
    private String id;
    private String menuId;
    private Integer qty;
}
