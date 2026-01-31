package com.hotelix.hotel_system.dto.request;

import com.hotelix.hotel_system.enums.SubscriptionPlan;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPlanUpdateRequest {
    @NotNull(message = "Subscription plan is required")
    private SubscriptionPlan subscriptionPlan;
}