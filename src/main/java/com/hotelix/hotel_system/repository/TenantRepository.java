package com.hotelix.hotel_system.repository;

import com.hotelix.hotel_system.entity.Tenant;
import com.hotelix.hotel_system.enums.TenantStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface TenantRepository extends JpaRepository<Tenant,Long> {

    boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email);

    Optional<Tenant> findByTenantId(String tenantId);

    Page<Tenant> findByTenantStatus(TenantStatus status, Pageable pageable);
}
