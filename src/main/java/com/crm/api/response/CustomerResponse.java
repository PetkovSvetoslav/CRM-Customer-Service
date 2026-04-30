package com.crm.api.response;

import java.util.List;

public class CustomerResponse {
    public String id;
    public String customerCode;
    public String companyName;
    public String legalName;
    public String taxNumber;
    public String industry;
    public String website;
    public String status;
    public String segment;
    public String ownerUserId;
    public List<String> tags;
    public CustomerAddressResponse address;
    public String createdAt;
    public String updatedAt;
}