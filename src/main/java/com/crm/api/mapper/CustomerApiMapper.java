package com.crm.api.mapper;

import com.crm.api.request.CustomerAddressRequest;
import com.crm.api.response.CustomerAddressResponse;
import com.crm.api.response.CustomerResponse;
import com.crm.domain.entity.Customer;
import com.crm.domain.entity.CustomerAddress;

import java.util.ArrayList;

public final class CustomerApiMapper {

    private CustomerApiMapper() {
    }

    public static CustomerResponse toCustomerResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.id = customer.id != null ? customer.id.toString() : null;
        response.customerCode = customer.customerCode;
        response.companyName = customer.companyName;
        response.legalName = customer.legalName;
        response.taxNumber = customer.taxNumber;
        response.industry = customer.industry;
        response.website = customer.website;
        response.status = customer.status != null ? customer.status.name() : null;
        response.segment = customer.segment != null ? customer.segment.name() : null;
        response.ownerUserId = customer.ownerUserId != null ? customer.ownerUserId.toString() : null;
        response.tags = customer.tags != null ? new ArrayList<>(customer.tags) : new ArrayList<>();
        response.address = toCustomerAddressResponse(customer.address);
        response.createdAt = customer.createdAt != null ? customer.createdAt.toString() : null;
        response.updatedAt = customer.updatedAt != null ? customer.updatedAt.toString() : null;
        return response;
    }

    public static CustomerAddressResponse toCustomerAddressResponse(CustomerAddress address) {
        if (address == null) {
            return null;
        }

        CustomerAddressResponse response = new CustomerAddressResponse();
        response.country = address.country;
        response.city = address.city;
        response.postalCode = address.postalCode;
        response.line1 = address.line1;
        response.line2 = address.line2;
        return response;
    }

    public static CustomerAddress toCustomerAddress(CustomerAddressRequest request) {
        if (request == null) {
            return null;
        }

        CustomerAddress address = new CustomerAddress();
        address.country = request.country;
        address.city = request.city;
        address.postalCode = request.postalCode;
        address.line1 = request.line1;
        address.line2 = request.line2;
        return address;
    }
}