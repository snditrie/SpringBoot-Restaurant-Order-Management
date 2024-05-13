package com.enigma.wmb_sb.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailRequest {
    private String menuId;
    private Integer qty;
}
