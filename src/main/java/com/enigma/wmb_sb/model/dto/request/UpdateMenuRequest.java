package com.enigma.wmb_sb.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMenuRequest {
    private String id;

    private String name;

    @Min(value = 0, message = "price must be greater than or equal 0")
    private Integer price;
}
