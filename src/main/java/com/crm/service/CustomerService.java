package com.crm.service;

import com.crm.api.mapper.CustomerApiMapper;
import com.crm.api.request.CreateCustomerRequest;
import com.crm.api.request.PatchCustomerRequest;
import com.crm.api.request.PatchCustomerStatusRequest;
import com.crm.api.response.CustomerResponse;
import com.crm.domain.entity.Customer;
import com.crm.domain.enums.CustomerSegment;
import com.crm.domain.enums.CustomerStatus;
import com.crm.exception.DuplicateResourceException;
import com.crm.exception.InvalidEnumValueException;
import com.crm.exception.NotFoundException;
import com.crm.repository.CustomerRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        String normalizedCompanyName = request.companyName.trim();

        if (customerRepository.existsByCompanyName(normalizedCompanyName)) {
            throw new DuplicateResourceException("Customer with this company name already exists");
        }

        Customer customer = new Customer();
        customer.id = UUID.randomUUID();
        customer.customerCode = generateUniqueCustomerCode();
        customer.companyName = normalizedCompanyName;
        customer.legalName = request.legalName;
        customer.taxNumber = request.taxNumber;
        customer.industry = request.industry;
        customer.website = request.website;
        customer.status = parseStatus(request.status);
        customer.segment = parseSegment(request.segment);
        customer.ownerUserId = request.ownerUserId;
        customer.tags = request.tags != null ? new ArrayList<>(request.tags) : new ArrayList<>();
        customer.address = CustomerApiMapper.toCustomerAddress(request.address);

        customerRepository.persist(customer);

        return CustomerApiMapper.toCustomerResponse(customer);
    }

    public List<CustomerResponse> list(
            String q,
            String status,
            String segment,
            UUID ownerUserId,
            int page,
            int size
    ) {
        CustomerStatus parsedStatus = status != null ? parseStatus(status) : null;
        CustomerSegment parsedSegment = segment != null ? parseSegment(segment) : null;

        PanacheQuery<Customer> query = customerRepository.search(q, parsedStatus, parsedSegment, ownerUserId)
                .page(page, size);

        return query.list()
                .stream()
                .map(CustomerApiMapper::toCustomerResponse)
                .toList();
    }

    public long count(
            String q,
            String status,
            String segment,
            UUID ownerUserId
    ) {
        CustomerStatus parsedStatus = status != null ? parseStatus(status) : null;
        CustomerSegment parsedSegment = segment != null ? parseSegment(segment) : null;

        return customerRepository.search(q, parsedStatus, parsedSegment, ownerUserId).count();
    }

    public CustomerResponse getById(UUID id) {
        Customer customer = customerRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        return CustomerApiMapper.toCustomerResponse(customer);
    }

    @Transactional
    public CustomerResponse patch(UUID id, PatchCustomerRequest request) {
        Customer customer = customerRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        if (request.companyName != null) {
            String normalizedCompanyName = request.companyName.trim();

            if (!normalizedCompanyName.equalsIgnoreCase(customer.companyName)
                    && customerRepository.existsByCompanyName(normalizedCompanyName)) {
                throw new DuplicateResourceException("Customer with this company name already exists");
            }

            customer.companyName = normalizedCompanyName;
        }

        if (request.legalName != null) {
            customer.legalName = request.legalName;
        }

        if (request.taxNumber != null) {
            customer.taxNumber = request.taxNumber;
        }

        if (request.industry != null) {
            customer.industry = request.industry;
        }

        if (request.website != null) {
            customer.website = request.website;
        }

        if (request.segment != null) {
            customer.segment = parseSegment(request.segment);
        }

        if (request.ownerUserId != null) {
            customer.ownerUserId = request.ownerUserId;
        }

        if (request.tags != null) {
            customer.tags = new ArrayList<>(request.tags);
        }

        if (request.address != null) {
            customer.address = CustomerApiMapper.toCustomerAddress(request.address);
        }

        return CustomerApiMapper.toCustomerResponse(customer);
    }

    @Transactional
    public CustomerResponse patchStatus(UUID id, PatchCustomerStatusRequest request) {
        Customer customer = customerRepository.findByIdOptional(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        customer.status = parseStatus(request.status);

        return CustomerApiMapper.toCustomerResponse(customer);
    }

    private CustomerStatus parseStatus(String value) {
        try {
            return CustomerStatus.valueOf(value.trim().toUpperCase());
        } catch (Exception ex) {
            throw new InvalidEnumValueException("Invalid customer status: " + value);
        }
    }

    private CustomerSegment parseSegment(String value) {
        try {
            return CustomerSegment.valueOf(value.trim().toUpperCase());
        } catch (Exception ex) {
            throw new InvalidEnumValueException("Invalid customer segment: " + value);
        }
    }

    private String generateUniqueCustomerCode() {
        String code;
        do {
            code = "CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (customerRepository.existsByCustomerCode(code));
        return code;
    }
}