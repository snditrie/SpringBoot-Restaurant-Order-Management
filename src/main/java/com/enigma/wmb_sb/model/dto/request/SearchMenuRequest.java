package com.enigma.wmb_sb.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchMenuRequest {
    private String id;
    private String name;
    private Integer price;
    private Integer priceStart;
    private Integer priceEnd;

    private Integer page;
    private Integer size;

    private String sortBy;
    private String direction;
}
