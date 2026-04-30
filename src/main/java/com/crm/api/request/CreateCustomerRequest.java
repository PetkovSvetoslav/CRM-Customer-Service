package com.crm.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class CreateCustomerRequest {

    @NotBlank(message = "Company name is required")
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

    @NotNull(message = "Status is required")
    public String status;

    @NotNull(message = "Segment is required")
    public String segment;

    public UUID ownerUserId;

    public List<@Size(max = 50, message = "Each tag must be at most 50 characters") String> tags;

    @Valid
    public CustomerAddressRequest address;
}