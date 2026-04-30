package com.crm.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class PatchCustomerRequest {

    @Size(min = 2, max = 150, message = "Company name must be between 2 and 150 characters")
    public String companyName;

    @Size(max = 255, message = "Legal name must be at most 255 characters")
    public String legalName;

    @Size(max = 50, message = "Tax number must be at most 50 characters")
    public String taxNumber;

    @Size(max = 100, message = "Industry must be at most 100 characters")
    public String industry;

    @Size(max = 255, message = "Website must be at most 255 characters")
    @Pattern(
            regexp = "^(https?://.*)?$",
            message = "Website must be a valid URL starting with http:// or https://"
    )
    public String website;

    public String segment;

    public UUID ownerUserId;

    public List<@Size(max = 50, message = "Each tag must be at most 50 characters") String> tags;

    @Valid
    public CustomerAddressRequest address;
}