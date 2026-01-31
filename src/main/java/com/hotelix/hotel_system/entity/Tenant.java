package com.hotelix.hotel_system.entity;

import com.hotelix.hotel_system.enums.SubscriptionPlan;
import com.hotelix.hotel_system.enums.TenantStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;


@Entity
@Table(name = "tenants",indexes = {
        @Index(name = "idx_tenant_id",columnList = "tenantId"),
        @Index(name = "idx_email",columnList = "email")
})
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false,length = 50)
    private String tenantId;
    @Column(nullable = false, length = 100)
    private String hotelName;
    @Column(unique = true, nullable = false, length = 100)
    private String email;
    @Column(length = 20)
    private String phoneNumber;
    @Column(length = 250)
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SubscriptionPlan subscriptionPlan = SubscriptionPlan.FREE;
    @Enumerated
    @Column(nullable = false, length = 20)
    private TenantStatus tenantStatus = TenantStatus.ACTIVE;
    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedBy
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Column(length = 500)
    private String metadata;
}
