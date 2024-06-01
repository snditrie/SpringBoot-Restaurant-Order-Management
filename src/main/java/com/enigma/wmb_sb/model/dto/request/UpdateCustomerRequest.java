package com.enigma.wmb_sb.model.dto.request;

import com.enigma.wmb_sb.constant.ResponseMessage;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCustomerRequest {
    private String id;
    private String name;
    @Pattern(regexp = "^08\\d{9,11}$", message = ResponseMessage.ERROR_PHONE_NUMBER)
    private String mobilePhone;
    private Boolean memberStatus;
}
