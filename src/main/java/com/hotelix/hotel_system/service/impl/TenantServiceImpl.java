package com.hotelix.hotel_system.service.impl;

import com.hotelix.hotel_system.dto.request.SubscriptionPlanUpdateRequest;
import com.hotelix.hotel_system.dto.request.TenantRegistrationRequest;
import com.hotelix.hotel_system.dto.request.TenantUpdateRequest;
import com.hotelix.hotel_system.dto.response.TenantResponse;
import com.hotelix.hotel_system.entity.Tenant;
import com.hotelix.hotel_system.enums.TenantStatus;
import com.hotelix.hotel_system.exception.TenantAlreadyExistsException;
import com.hotelix.hotel_system.exception.TenantNotFoundException;
import com.hotelix.hotel_system.repository.TenantRepository;
import com.hotelix.hotel_system.service.TenantService;
import com.hotelix.hotel_system.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final Mapper mapper;

    @Override
    public TenantResponse registerTenant(TenantRegistrationRequest request) {

     if(tenantRepository.existsByEmail(request.getEmail())) {
         throw new TenantAlreadyExistsException(
                 "Tenant already exists with email: " + request.getEmail()
         );
     }
        Tenant savedTenant = tenantRepository.save(mapper.toTenant(request));
        return mapper.ToResponse(savedTenant);
    }

    @Override
    public TenantResponse getTenantById(String tenantId) {
        Tenant selectedTenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with id: " + tenantId));
       return mapper.ToResponse(selectedTenant);
    }

    @Override
    public Page<TenantResponse> getAllTenant(Pageable pageable) {
        return tenantRepository.findAll(pageable)
                .map(mapper::ToResponse);
    }

    @Override
    public Page<TenantResponse> getTenantByStatus(TenantStatus status, Pageable pageable) {
        return tenantRepository
                .findByTenantStatus(status,pageable)
                .map( mapper::ToResponse);
    }

    @Override
    public TenantResponse updateTenant(String tenantId, TenantUpdateRequest request) {
        Tenant selectedTenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with id: " + tenantId));

        if(request.getHotelName() != null)selectedTenant.setHotelName(request.getHotelName());
        if(request.getPhoneNumber() != null)selectedTenant.setPhoneNumber(request.getPhoneNumber());
        if(request.getAddress() != null) selectedTenant.setAddress(request.getAddress());
        if(request.getMetadata() != null) selectedTenant.setMetadata(request.getMetadata());

        Tenant updatedTenant = tenantRepository.save(selectedTenant);

        return mapper.ToResponse(updatedTenant);
    }

    @Override
    public TenantResponse updateSubscribePlan(String tenantId, SubscriptionPlanUpdateRequest request) {
        Tenant selectedTenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with id: " + tenantId));

        if(request.getSubscriptionPlan() != null)selectedTenant.setSubscriptionPlan(request.getSubscriptionPlan());

        return mapper.ToResponse(selectedTenant);
    }

    @Override
    public void suspendTenant(String tenantId, String suspensionReason) {
        Tenant selectedTenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with id: " + tenantId));

        if(selectedTenant.getTenantStatus().equals(TenantStatus.SUSPENDED)) return;

        selectedTenant.setTenantStatus(TenantStatus.SUSPENDED);
        tenantRepository.save(selectedTenant);
    }

    @Override
    public void activateTenant(String tenantId) {
        Tenant selectedTenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with id: " + tenantId));

        if(selectedTenant.getTenantStatus().equals(TenantStatus.ACTIVE)) return;

        selectedTenant.setTenantStatus(TenantStatus.ACTIVE);
        tenantRepository.save(selectedTenant);
    }

    @Override
    public void deleteTenant(String tenantId) {
        Tenant selectedTenant = tenantRepository.findByTenantId(tenantId)
                .orElseThrow(() -> new TenantNotFoundException("Tenant not found with id: " + tenantId));

        if (selectedTenant.getTenantStatus().equals(TenantStatus.DELETED)) return;

        selectedTenant.setTenantStatus(TenantStatus.DELETED);
        tenantRepository.delete(selectedTenant);
    }
}
