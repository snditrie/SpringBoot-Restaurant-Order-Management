package com.enigma.wmb_sb.model.dto.request;

import com.enigma.wmb_sb.constant.ResponseMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailRequest {
    @NotBlank(message = "menu ID" + ResponseMessage.ERROR_DATA_REQUIRED)
    private String menuId;

    @NotNull(message = "quantity" + ResponseMessage.ERROR_DATA_REQUIRED)
    @Min(value = 1, message = "quantity must be at least 1")
    private Integer qty;
}
