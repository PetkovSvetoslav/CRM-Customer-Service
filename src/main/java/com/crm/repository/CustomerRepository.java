package com.crm.repository;

import com.crm.domain.entity.Customer;
import com.crm.domain.enums.CustomerSegment;
import com.crm.domain.enums.CustomerStatus;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public Optional<Customer> findByIdOptional(UUID id) {
        return find("id", id).firstResultOptional();
    }

    public Optional<Customer> findByCustomerCode(String customerCode) {
        return find("customerCode", customerCode).firstResultOptional();
    }

    public Optional<Customer> findByCompanyName(String companyName) {
        return find("lower(companyName) = ?1", companyName.toLowerCase()).firstResultOptional();
    }

    public boolean existsByCompanyName(String companyName) {
        return count("lower(companyName) = ?1", companyName.toLowerCase()) > 0;
    }

    public boolean existsByCustomerCode(String customerCode) {
        return count("customerCode", customerCode) > 0;
    }

    public PanacheQuery<Customer> search(
            String q,
            CustomerStatus status,
            CustomerSegment segment,
            UUID ownerUserId
    ) {
        StringBuilder jpql = new StringBuilder("1=1");
        Map<String, Object> params = new HashMap<>();

        if (q != null && !q.isBlank()) {
            jpql.append(" and (lower(companyName) like :q or lower(legalName) like :q or lower(taxNumber) like :q)");
            params.put("q", "%" + q.toLowerCase() + "%");
        }

        if (status != null) {
            jpql.append(" and status = :status");
            params.put("status", status);
        }

        if (segment != null) {
            jpql.append(" and segment = :segment");
            params.put("segment", segment);
        }

        if (ownerUserId != null) {
            jpql.append(" and ownerUserId = :ownerUserId");
            params.put("ownerUserId", ownerUserId);
        }

        jpql.append(" order by companyName asc");

        return find(jpql.toString(), params);
    }
}