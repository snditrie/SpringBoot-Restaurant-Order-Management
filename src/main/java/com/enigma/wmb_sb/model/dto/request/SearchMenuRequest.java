package com.enigma.wmb_sb.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchMenuRequest {
    private String name;
    private Long priceStart;
    private Long priceEnd;
}
