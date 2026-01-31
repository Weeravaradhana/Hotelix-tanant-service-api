package com.hotelix.hotel_system.api;

import com.hotelix.hotel_system.dto.request.SubscriptionPlanUpdateRequest;
import com.hotelix.hotel_system.dto.request.TenantRegistrationRequest;
import com.hotelix.hotel_system.dto.request.TenantSuspendRequest;
import com.hotelix.hotel_system.dto.request.TenantUpdateRequest;
import com.hotelix.hotel_system.dto.response.TenantResponse;
import com.hotelix.hotel_system.enums.TenantStatus;
import com.hotelix.hotel_system.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/tenants")
@RestController
@RequiredArgsConstructor
@Slf4j
public class TenantController {

    private final TenantService tenantService;


    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantResponse> registerTenant(
            @Valid @RequestBody TenantRegistrationRequest request) {

        log.info("Registering new tenant");
        TenantResponse response = tenantService.registerTenant(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{tenantId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TENANT_ADMIN')")
    public ResponseEntity<TenantResponse> getTenantById(
            @PathVariable String tenantId) {

        TenantResponse response = tenantService.getTenantById(tenantId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Page<TenantResponse>> getAllTenants(
            @PageableDefault(size = 20) Pageable pageable) {

        Page<TenantResponse> response = tenantService.getAllTenant(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Page<TenantResponse>> getTenantsByStatus(
            @PathVariable TenantStatus status,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<TenantResponse> response =
                tenantService.getTenantByStatus(status, pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{tenantId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TENANT_ADMIN')")
    public ResponseEntity<TenantResponse> updateTenant(
            @PathVariable String tenantId,
            @Valid @RequestBody TenantUpdateRequest request) {

        TenantResponse response =
                tenantService.updateTenant(tenantId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{tenantId}/subscription")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantResponse> updateSubscriptionPlan(
            @PathVariable String tenantId,
            @Valid @RequestBody SubscriptionPlanUpdateRequest request) {

        TenantResponse response =
                tenantService.updateSubscribePlan(tenantId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{tenantId}/suspend")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> suspendTenant(
            @PathVariable String tenantId,
            @Valid @RequestBody TenantSuspendRequest request) {

        tenantService.suspendTenant(tenantId, request.getReason());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{tenantId}/activate")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> activateTenant(
            @PathVariable String tenantId) {

        tenantService.activateTenant(tenantId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{tenantId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteTenant(
            @PathVariable String tenantId) {

        tenantService.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/internal/validate/{tenantId}")
    public ResponseEntity<Void> validateTenant(@PathVariable String tenantId) {
        try {
            tenantService.getTenantById(tenantId);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
