package com.enigma.wmb_sb.model.dto.request;

import com.enigma.wmb_sb.constant.ResponseMessage;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCustomerRequest {
    private String name;
    @Pattern(regexp = "^08\\d{9,11}$", message = ResponseMessage.ERROR_PHONE_NUMBER)
    private String phone;
    private Boolean memberStatus;
}
