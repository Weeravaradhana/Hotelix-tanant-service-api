package com.hotelix.hotel_system.service;

import com.hotelix.hotel_system.dto.request.SubscriptionPlanUpdateRequest;
import com.hotelix.hotel_system.dto.request.TenantRegistrationRequest;
import com.hotelix.hotel_system.dto.request.TenantUpdateRequest;
import com.hotelix.hotel_system.dto.response.TenantResponse;
import com.hotelix.hotel_system.enums.TenantStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantService {
    TenantResponse registerTenant(TenantRegistrationRequest request);
    TenantResponse getTenantById(String tenantId);
    Page<TenantResponse> getAllTenant(Pageable pageable);
    Page<TenantResponse> getTenantByStatus(TenantStatus status, Pageable pageable);
    TenantResponse updateTenant(String tenantId, TenantUpdateRequest request);
    TenantResponse updateSubscribePlan(String tenantId, SubscriptionPlanUpdateRequest request);
    void suspendTenant(String tenantId,String suspensionReason);
    void activateTenant(String tenantId);
    void deleteTenant(String tenantId);
}
