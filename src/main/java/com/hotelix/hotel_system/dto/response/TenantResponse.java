package com.hotelix.hotel_system.dto.response;

import com.hotelix.hotel_system.enums.SubscriptionPlan;
import com.hotelix.hotel_system.enums.TenantStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantResponse {
    private Long id;
    private String tenantId;
    private String hotelName;
    private String email;
    private String phoneNumber;
    private String address;
    private SubscriptionPlan subscriptionPlan;
    private TenantStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String metadata;
}