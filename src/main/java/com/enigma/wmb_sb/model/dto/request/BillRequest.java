package com.enigma.wmb_sb.model.dto.request;

import com.enigma.wmb_sb.constant.ResponseMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillRequest {
    @NotBlank(message = "customer ID" + ResponseMessage.ERROR_DATA_REQUIRED)
    private String customerId;

    @NotBlank(message = "transaction type" + ResponseMessage.ERROR_DATA_REQUIRED)
    private String transTypeId;

    private String tableRestoId;

    @NotNull(message = "bill detail" + ResponseMessage.ERROR_DATA_REQUIRED)
    @Size(min = 1, message = "bill must have at least one detail")
    private List<BillDetailRequest> billDetails;
}
