package com.enigma.wmb_sb.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BillDetailResponse {
    private String id;
    private String menuName;
    private Integer qty;
    private Integer price;
    private Integer totalAmount;
}
