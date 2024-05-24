package com.enigma.wmb_sb.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchBillRequest {
    private String date;

    private String sortBy;
    private String direction;

    private Integer page;
    private Integer size;
}
