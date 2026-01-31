package com.hotelix.hotel_system.util;

import com.hotelix.hotel_system.dto.request.TenantRegistrationRequest;
import com.hotelix.hotel_system.dto.response.TenantResponse;
import com.hotelix.hotel_system.entity.Tenant;
import com.hotelix.hotel_system.enums.TenantStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Mapper {
    private final TenantIdGenerator tenantIdGenerator;

    public Tenant toTenant(TenantRegistrationRequest request) {
        return request == null ? null :  Tenant.builder()
                .tenantId(tenantIdGenerator.generate())
                .hotelName(request.getHotelName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .subscriptionPlan(request.getSubscriptionPlan())
                .tenantStatus(TenantStatus.ACTIVE)
                .metadata(request.getMetadata())
                .build();
    }

    public TenantResponse ToResponse(Tenant tenant) {

        return tenant == null ? null : TenantResponse.builder()
                .id(tenant.getId())
                .tenantId(tenant.getTenantId())
                .hotelName(tenant.getHotelName())
                .email(tenant.getEmail())
                .phoneNumber(tenant.getPhoneNumber())
                .address(tenant.getAddress())
                .subscriptionPlan(tenant.getSubscriptionPlan())
                .status(tenant.getTenantStatus())
                .createdAt(tenant.getCreatedAt())
                .updatedAt(tenant.getUpdatedAt())
                .metadata(tenant.getMetadata())
                .build();
    }
}
