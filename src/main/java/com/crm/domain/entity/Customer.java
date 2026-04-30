package com.crm.domain.entity;

import com.crm.domain.enums.CustomerSegment;
import com.crm.domain.enums.CustomerStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer extends PanacheEntityBase {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    public UUID id;

    @Column(name = "customer_code", nullable = false, unique = true, length = 30)
    public String customerCode;

    @Column(name = "company_name", nullable = false, length = 150)
    public String companyName;

    @Column(name = "legal_name", length = 255)
    public String legalName;

    @Column(name = "tax_number", length = 50)
    public String taxNumber;

    @Column(name = "industry", length = 100)
    public String industry;

    @Column(name = "website", length = 255)
    public String website;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    public CustomerStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "segment", nullable = false, length = 30)
    public CustomerSegment segment;

    @Column(name = "owner_user_id")
    public UUID ownerUserId;

    @ElementCollection
    @CollectionTable(name = "customer_tags", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "tag", length = 50, nullable = false)
    public List<String> tags = new ArrayList<>();

    @Embedded
    public CustomerAddress address;

    @Column(name = "created_at", nullable = false)
    public OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    public OffsetDateTime updatedAt;

    @PrePersist
    void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        if (id == null) {
            id = UUID.randomUUID();
        }
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}