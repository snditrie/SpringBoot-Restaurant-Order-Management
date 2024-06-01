package com.enigma.wmb_sb.model.dto.request;

import com.enigma.wmb_sb.constant.ResponseMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank(message = "username" + ResponseMessage.ERROR_DATA_REQUIRED)
    @Size(min = 5, max = 25, message = "username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "password" + ResponseMessage.ERROR_DATA_REQUIRED)
    @Size(min = 8, message = "password must be at least 6 characters")
    private String password;
}
