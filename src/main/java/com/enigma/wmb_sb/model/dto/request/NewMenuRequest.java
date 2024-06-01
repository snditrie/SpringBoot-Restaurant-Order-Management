package com.enigma.wmb_sb.model.dto.request;

import com.enigma.wmb_sb.constant.ResponseMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewMenuRequest {
    @NotBlank(message = "name" + ResponseMessage.ERROR_DATA_REQUIRED)
    private String name;

    @NotNull(message = "price" + ResponseMessage.ERROR_DATA_REQUIRED)
    @Min(value = 0, message = "price must be greater than or equal 0")
    private Integer price;
}
