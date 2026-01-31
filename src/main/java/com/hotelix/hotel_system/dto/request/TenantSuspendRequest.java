package com.hotelix.hotel_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TenantSuspendRequest {

    @NotBlank(message = "Suspend reason is required")
    private String reason;
}
